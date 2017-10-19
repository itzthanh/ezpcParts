/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author Thanh
 */
@WebServlet(urlPatterns = {"/checkout"})
public class checkout extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "1803");
//            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");      
//            Connection conn = DriverManager.getConnection("jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124-db-053", "inf124-db-053", "fyy1arKN!Z<k");
//            Statement stmt = conn.createStatement();
//            String pid = request.getParameter("pid");
//            String sql = "SELECT * FROM Product";
//            ResultSet rs = stmt.executeQuery(sql);
//            HttpSession session = request.getSession();
//            ArrayList<Integer> cart = (ArrayList) session.getAttribute("cart");
//            ArrayList<Integer> cartQuantity = (ArrayList) session.getAttribute("cartQuantity");
//            double totalPrice, tax, shipping, grandTotal;
//            totalPrice = tax = shipping = grandTotal = 0.0;
//            for(int i = 0; i < cart.size(); i++){
//                int cartPid = (int) cart.get(i);
//                rs.absolute(cartPid);
//                Double price = rs.getDouble("price");
//                int quantity = (int) cartQuantity.get(i);
//                totalPrice += (quantity * price);
//            }
//            tax = (0.08 * totalPrice);
//            grandTotal = totalPrice + tax + shipping;
//            session.setAttribute("totalPrice", totalPrice);
//            session.setAttribute("tax", tax);
//            session.setAttribute("shipping", shipping);
//            session.setAttribute("grandTotal", grandTotal);
//            rs.close();
//            stmt.close();
//            conn.close(); 
////            request.getRequestDispatcher("checkout.jsp").include(request, response);
//            
//            /* TODO output your page here. You may use following sample code. */
//        } catch (SQLException ex) {
//            Logger.getLogger(checkout.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(checkout.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(checkout.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(checkout.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        try{
            PrintWriter out = response.getWriter();
            String pid = request.getParameter("pid");
            ClientConfig config = new ClientConfig();
            Client client = ClientBuilder.newClient(config);

            WebTarget target = client.target(getBaseURI());
            HttpSession session = request.getSession();
            ArrayList<Integer> cart = (ArrayList) session.getAttribute("cart");
            ArrayList<Integer> cartQuantity = (ArrayList) session.getAttribute("cartQuantity");
            double totalPrice, tax, shipping, grandTotal;
            totalPrice = tax = shipping = grandTotal = 0.0;
            
            int quantity = 0;
            for (int i=0; i<cartQuantity.size(); i++){
                quantity += cartQuantity.get(i);
            }
            
            for(int i = 0; i < cart.size(); i++){
                String jsonResponse =
                target.path("api").path("products").path(Integer.toString(cart.get(i))).
                request(). //send a request
                accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                get(String.class); // use the get method and return the response as a string

//                System.out.println(jsonResponse);

                ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

                Product product = objectMapper.readValue(jsonResponse, new TypeReference<Product>(){});
                
                int cartPid = (int) cart.get(i);
                float price = product.getPrice();
                int q = (int) cartQuantity.get(i);
                totalPrice += (q * price);
            }
            tax = (0.08 * totalPrice);
            grandTotal = totalPrice + tax + shipping;
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("tax", tax);
            session.setAttribute("shipping", shipping);
            session.setAttribute("grandTotal", grandTotal);
            request.setAttribute("target", target);
            request.setAttribute("quantity", quantity);
            request.setAttribute("grandTotal", grandTotal);
            request.getRequestDispatcher("checkout.jsp").include(request, response);
            
        
    } catch (Exception e){
        PrintWriter out = response.getWriter();
        out.println(e.getMessage());
        
    }
}

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    private static URI getBaseURI() {
    //Change the URL here to make the client point to your service.
        return UriBuilder.fromUri("http://localhost:8080/PA4/").build();
    }
    
}
