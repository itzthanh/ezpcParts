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
import java.util.Vector;
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

@WebServlet(urlPatterns = {"/productDescription"})
public class productDescription extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            request.getRequestDispatcher("header.jsp").include(request, response);
            out.println("<link rel=\"stylesheet\" href=\"css/product.css\" />");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "1803");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            Connection conn = DriverManager.getConnection("jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124-db-053", "inf124-db-053", "fyy1arKN!Z<k");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Product WHERE pid=" + request.getParameter("pid");            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                int pid = rs.getInt("pid");
                out.println("<b><h1 id='title'>" + rs.getString("name") + "</h1></b>");
            
                out.println("<hr><br><div id=\"product\"><div id=\"product-img\">");
                if(pid == 1)
                {
                    out.println("<img src='img/gtx1080.jpg'/>");
                }
                else if(pid == 2)
                {
                    out.println("<img src='img/gtx1070.jpg'/>");
                }
                else if(pid == 3)
                {
                    out.println("<img src='img/4690k.jpg'/>");
                }
                else if(pid == 4)
                {
                    out.println("<img src='img/4790k.jpg'/>");
                }
                else if(pid == 5)
                {
                    out.println("<img src='img/sabre.png'/>");
                }
                else if(pid == 6)
                {
                    out.println("<img src='img/g502.jpg'/>");
                }
                else if(pid == 7)
                {
                    out.println("<img src='img/blackwidow.png'/>");
                }
                else if(pid == 8)
                {
                    out.println("<img src='img/strafe.png'/>");
                }
                else if(pid == 9)
                {
                    out.println("<img src='img/h440.png'/>");
                }
                else
                {
                    out.println("<img src='img/view27.jpg'/>");
                }
                out.println("</div><div id=\"product-description\">");
                out.println("<p>" + rs.getString("description") + "</p><br>");
                out.println("<h2>PID: " + rs.getString("pid") + "<br>");
                out.println("Price: " + rs.getString("price") + "</h2>");
                out.println("<form action=\"./addedToCart\" method=\"get\"><p>Quantity <input id=\"quantity\" name=\"quantity\" type=\"number\" min=\"1\" value=\"1\"></p>");
                out.println("<input type=\"hidden\" name=\"pid\" id=\"pid\" value=\"" + pid + "\"");
                out.println("<div id=\"checkout-button\"><input type=\"submit\" value=\"Add To Cart\"></div></div></div><hr><p id=\"footer\">&copy; EZPC Parts 2017</p><br>");
                out.println("</body></html>");
                
                //Save product into browse history servlet session
                HttpSession session = request.getSession();
                if(session.getAttribute("productHistory") == null)
                {
                    ArrayList<Integer> newProductHistory = new ArrayList<Integer>();
                    newProductHistory.add(pid);
                    session.setAttribute("productHistory", newProductHistory);
                }
                else
                {
                    ArrayList productsViewed = (ArrayList) session.getAttribute("productHistory");
                    if(productsViewed.size() == 5)
                    {
                        productsViewed.remove(0);
                    } 
                    else if (productsViewed.contains(pid)){
                        productsViewed.remove(productsViewed.indexOf(pid));
                    }
                    productsViewed.add(pid);
                    session.setAttribute("productHistory", productsViewed);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (InstantiationException ex) {
            Logger.getLogger(productDescription.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(productDescription.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(productDescription.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(productDescription.class.getName()).log(Level.SEVERE, null, ex);
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
        
        request.getRequestDispatcher("header.jsp").include(request, response);
        out.println("<link rel=\"stylesheet\" href=\"css/product.css\" />");
        
        String jsonResponse =
                target.path("api").path("products").path(request.getParameter("pid")).
                        request(). //send a request
                        accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                        get(String.class); // use the get method and return the response as a string

//        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

        Product product = objectMapper.readValue(jsonResponse, new TypeReference<Product>(){});

//        PrintWriter out = response.getWriter();


        int pid = product.getPID();
        
        HttpSession session = request.getSession();
        if(session.getAttribute("productHistory") == null)
        {
            ArrayList<Integer> newProductHistory = new ArrayList<Integer>();
            newProductHistory.add(pid);
            session.setAttribute("productHistory", newProductHistory);
        }
        else
        {
            ArrayList productsViewed = (ArrayList) session.getAttribute("productHistory");
            if(productsViewed.size() == 5)
            {
                productsViewed.remove(0);
            } 
            else if (productsViewed.contains(pid)){
                productsViewed.remove(productsViewed.indexOf(pid));
            }
            productsViewed.add(pid);
            session.setAttribute("productHistory", productsViewed);
        }
        out.println("<b><h1 id='title'>" + product.getName() + "</h1></b>");

        out.println("<hr><br><div id=\"product\"><div id=\"product-img\">");
        if(pid == 1)
        {
            out.println("<img src='img/gtx1080.jpg'/>");
        }
        else if(pid == 2)
        {
            out.println("<img src='img/gtx1070.jpg'/>");
        }
        else if(pid == 3)
        {
            out.println("<img src='img/4690k.jpg'/>");
        }
        else if(pid == 4)
        {
            out.println("<img src='img/4790k.jpg'/>");
        }
        else if(pid == 5)
        {
            out.println("<img src='img/sabre.png'/>");
        }
        else if(pid == 6)
        {
            out.println("<img src='img/g502.jpg'/>");
        }
        else if(pid == 7)
        {
            out.println("<img src='img/blackwidow.png'/>");
        }
        else if(pid == 8)
        {
            out.println("<img src='img/strafe.png'/>");
        }
        else if(pid == 9)
        {
            out.println("<img src='img/h440.png'/>");
        }
        else
        {
            out.println("<img src='img/view27.jpg'/>");
        }
        out.println("</div><div id=\"product-description\">");
        out.println("<p>" + product.getDescription() + "</p><br>");
        out.println("<h2>PID: " +  pid + "<br>");
        out.println("Price: " + product.getPrice() + "</h2>");
        out.println("<form action=\"./addedToCart\" method=\"get\"><p>Quantity <input id=\"quantity\" name=\"quantity\" type=\"number\" min=\"1\" value=\"1\"></p>");
        out.println("<input type=\"hidden\" name=\"pid\" id=\"pid\" value=\"" + pid + "\"");
        out.println("<div id=\"checkout-button\"><input type=\"submit\" value=\"Add To Cart\"></div></div></div><hr><p id=\"footer\">&copy; EZPC Parts 2017</p><br>");
        out.println("</body></html>");
        
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
