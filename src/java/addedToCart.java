import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

@WebServlet(urlPatterns = {"/addedToCart"})
public class addedToCart extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            request.getRequestDispatcher("header.jsp").include(request, response);
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/addToCart.css\" />");
            out.println("<h2 id='addToCartHeader'>The following product was added to your cart: </h2>");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "1803");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");      
            Connection conn = DriverManager.getConnection("jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124-db-053", "inf124-db-053", "fyy1arKN!Z<k");    
            Statement stmt = conn.createStatement();
            String pid = request.getParameter("pid");
            String sql = "SELECT * FROM Product WHERE pid=" + pid;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            response.setContentType("image/jpg"); 
            out.print("<img id='addToCartProduct' src='" + rs.getString("image") + "'/>");   
            response.setContentType("text/html;charset=UTF-8");
            out.println("<p id='addToCartName'>Item: <span style='font-weight:bold'>" + rs.getString("name") + "</span></p>");
            out.println("<p id='addToCartQuantity'>Quantity: <span style='font-weight:bold'>" + request.getParameter("quantity") + "</span></p>");
                //Save product to cart
                HttpSession session = request.getSession();
                if(session.getAttribute("cart") == null)
                {
                    ArrayList<Integer> newCart = new ArrayList<Integer>();
                    ArrayList<Integer> cartQuantity = new ArrayList<Integer>();
                    newCart.add(Integer.parseInt(request.getParameter("pid")));
                    cartQuantity.add(Integer.parseInt(request.getParameter("quantity")));
                    session.setAttribute("cart", newCart);
                    session.setAttribute("cartQuantity", cartQuantity);
                }
                else
                {
                    ArrayList<Integer> cart = (ArrayList) session.getAttribute("cart");
                    ArrayList<Integer> cartQuantity = (ArrayList) session.getAttribute("cartQuantity");
                    if (!(cart.contains(Integer.parseInt(request.getParameter("pid"))))){
                        cart.add(Integer.parseInt(request.getParameter("pid")));
                        cartQuantity.add(Integer.parseInt(request.getParameter("quantity")));
                    } else {
                        int index = cart.indexOf(Integer.parseInt(request.getParameter("pid")));
                        int oldQuantity = cartQuantity.get(index);
                        cartQuantity.set(index, oldQuantity + Integer.parseInt(request.getParameter("quantity")));
                    }
                        out.println("<hr>");
                        out.println("<h2>Your Shopping Cart</h2><br>");
                        out.println("<div class='cartContainer'>");
                        sql = "SELECT * FROM Product";
                        rs = stmt.executeQuery(sql);
                        for(int i = 0; i < cart.size(); i++)
                        {
                            out.println("<div class='cartItem'>");
                            out.println("<div class='cartProductText'>");
                            int cartPid = (int) cart.get(i);
                            rs.absolute(cartPid);
                            out.println("<p>Item: <span style='font-weight:bold'>" + rs.getString("name") + "</span></p>");
                            out.println("<p>Quantity: <span style='font-weight:bold'>" + cartQuantity.get(i) + "</span></p>");
                            out.println("</div>");
                            out.println("<div class='cartProductImage'>");
                            response.setContentType("image/jpg"); 
                            out.println("<img class='cartImage' src='" + rs.getString("image") + "'/>");
                            out.println("</div>");
                            response.setContentType("text/html;charset=UTF-8");
                            out.println("</div>");
                        }
                        out.println("</div>");
                        session.setAttribute("cart", cart);
                        session.setAttribute("cartQuantity", cartQuantity);
                }
                out.println("<a class='cartButton' href='./products'>Continue Shopping</a><br><a class='cartButton' href='./checkout'>Checkout</a>");
                out.println("</body>");
                out.println("</html>");
                rs.close();
                stmt.close();
                conn.close(); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addedToCart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(addedToCart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(addedToCart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(addedToCart.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        request.getRequestDispatcher("header.jsp").include(request, response);
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/addToCart.css\" />");
        out.println("<h2 id='addToCartHeader'>The following product was added to your cart: </h2>");
        
        response.setContentType("image/jpg"); 
        int newlyAddedProduct = Integer.parseInt(request.getParameter("pid"));
        if(newlyAddedProduct == 1)
        {
            out.println("<img id='addToCartProduct' src='img/gtx1080.jpg'/>");
        }
        else if(newlyAddedProduct == 2)
        {
            out.println("<img id='addToCartProduct' src='img/gtx1070.jpg'/>");
        }
        else if(newlyAddedProduct == 3)
        {
            out.println("<img id='addToCartProduct' src='img/4690k.jpg'/>");
        }
        else if(newlyAddedProduct == 4)
        {
            out.println("<img id='addToCartProduct'src='img/4790k.jpg'/>");
        }
        else if(newlyAddedProduct == 5)
        {
            out.println("<img id='addToCartProduct' src='img/sabre.png'/>");
        }
        else if(newlyAddedProduct == 6)
        {
            out.println("<img id='addToCartProduct' src='img/g502.jpg'/>");
        }
        else if(newlyAddedProduct == 7)
        {
            out.println("<img id='addToCartProduct'src='img/blackwidow.png'/>");
        }
        else if(newlyAddedProduct == 8)
        {
            out.println("<img id='addToCartProduct'src='img/strafe.png'/>");
        }
        else if(newlyAddedProduct == 9)
        {
            out.println("<img id='addToCartProduct' src='img/h440.png'/>");
        }
        else
        {
            out.println("<img id='addToCartProduct' src='img/view27.jpg'/>");
        }  
        response.setContentType("text/html;charset=UTF-8");
        String jsonResponse1 =
                    target.path("api").path("products").path(Integer.toString(newlyAddedProduct)).
                            request(). //send a request
                            accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                            get(String.class); // use the get method and return the response as a string

//        System.out.println(jsonResponse1);
        ObjectMapper objectMapper1 = new ObjectMapper(); // This object is from the jackson library
        Product product1 = objectMapper1.readValue(jsonResponse1, new TypeReference<Product>(){});
        out.println("<p id='addToCartName'>Item: <span style='font-weight:bold'>" + product1.getName() + "</span></p>");
        out.println("<p id='addToCartQuantity'>Quantity: <span style='font-weight:bold'>" + request.getParameter("quantity") + "</span></p>");
        
        if(session.getAttribute("cart") == null)
        {
            ArrayList<Integer> newCart = new ArrayList<Integer>();
            ArrayList<Integer> cartQuantity = new ArrayList<Integer>();
            newCart.add(Integer.parseInt(request.getParameter("pid")));
            cartQuantity.add(Integer.parseInt(request.getParameter("quantity")));
            session.setAttribute("cart", newCart);
            session.setAttribute("cartQuantity", cartQuantity);
        }
        else
        {
            ArrayList<Integer> cart = (ArrayList) session.getAttribute("cart");
            ArrayList<Integer> cartQuantity = (ArrayList) session.getAttribute("cartQuantity");
            if (!(cart.contains(Integer.parseInt(request.getParameter("pid"))))){
                cart.add(Integer.parseInt(request.getParameter("pid")));
                cartQuantity.add(Integer.parseInt(request.getParameter("quantity")));
            } else {
                int index = cart.indexOf(Integer.parseInt(request.getParameter("pid")));
                int oldQuantity = cartQuantity.get(index);
                cartQuantity.set(index, oldQuantity + Integer.parseInt(request.getParameter("quantity")));
            }
                    out.println("<hr>");
                    out.println("<h2>Your Shopping Cart</h2><br>");
                    out.println("<div class='cartContainer'>");
                    for (int i=0; i<cart.size(); i++){
            int pid = cart.get(i);
            String jsonResponse =
                    target.path("api").path("products").path(Integer.toString(pid)).
                            request(). //send a request
                            accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                            get(String.class); // use the get method and return the response as a string

//            System.out.println(jsonResponse);

            ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

            Product product = objectMapper.readValue(jsonResponse, new TypeReference<Product>(){});
            

                        out.println("<div class='cartItem'>");
                        out.println("<div class='cartProductText'>");
                        out.println("<p>Item: <span style='font-weight:bold'>" + product.getName() + "</span></p>");
                        out.println("<p>Quantity: <span style='font-weight:bold'>" + cartQuantity.get(i) + "</span></p>");
                        out.println("</div>");
                        out.println("<div class='cartProductImage'>");
                        response.setContentType("image/jpg");                             
                        if(pid == 1)
                        {
                            out.println("<img class='cartImage' src='img/gtx1080.jpg'/>");
                        }
                        else if(pid == 2)
                        {
                            out.println("<img class='cartImage' src='img/gtx1070.jpg'/>");
                        }
                        else if(pid == 3)
                        {
                            out.println("<img class='cartImage' src='img/4690k.jpg'/>");
                        }
                        else if(pid == 4)
                        {
                            out.println("<img class='cartImage' src='img/4790k.jpg'/>");
                        }
                        else if(pid == 5)
                        {
                            out.println("<img class='cartImage' src='img/sabre.png'/>");
                        }
                        else if(pid == 6)
                        {
                            out.println("<img class='cartImage' src='img/g502.jpg'/>");
                        }
                        else if(pid == 7)
                        {
                            out.println("<img class='cartImage' src='img/blackwidow.png'/>");
                        }
                        else if(pid == 8)
                        {
                            out.println("<img class='cartImage' src='img/strafe.png'/>");
                        }
                        else if(pid == 9)
                        {
                            out.println("<img class='cartImage' src='img/h440.png'/>");
                        }
                        else
                        {
                            out.println("<img class='cartImage' src='img/view27.jpg'/>");
                        }                          
                        out.println("</div>");
                        response.setContentType("text/html;charset=UTF-8");
                        out.println("</div>");
        }
        

                        }
                        out.println("</div>");
                        out.println("<a class='cartButton' href='./products'>Continue Shopping</a><br><a class='cartButton' href='./checkout'>Checkout</a>");
                        out.println("</body>");
                        out.println("</html>");
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
        processRequest(request, response);
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
