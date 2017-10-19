import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.client.ClientConfig;

public class products extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            request.getRequestDispatcher("products.jsp").include(request, response);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "1803");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124-db-053", "inf124-db-053", "fyy1arKN!Z<k");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM product";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                int pid = rs.getInt("pid");
                out.println("<tr><td>" + pid + "</td><td><a href='./productDescription?pid=");
                if(pid == 1)
                {
                    out.println("1'><img src='img/gtx1080.jpg'/><p>");
                }
                else if(pid == 2)
                {
                    out.println("2'><img src='img/gtx1070.jpg'/><p>");
                }
                else if(pid == 3)
                {
                    out.println("3'><img src='img/4690k.jpg'/><p>");
                }
                else if(pid == 4)
                {
                    out.println("4'><img src='img/4790k.jpg'/><p>");
                }
                else if(pid == 5)
                {
                    out.println("5'><img src='img/sabre.png'/><p>");
                }
                else if(pid == 6)
                {
                    out.println("6'><img src='img/g502.jpg'/><p>");
                }
                else if(pid == 7)
                {
                    out.println("7'><img src='img/blackwidow.png'/><p>");
                }
                else if(pid == 8)
                {
                    out.println("8'><img src='img/strafe.png'/><p>");
                }
                else if(pid == 9)
                {
                    out.println("9'><img src='img/h440.png'/><p>");
                }
                else
                {
                    out.println("10'><img src='img/view27.jpg'/><p>");
                }
                out.println(rs.getString("name") + "</p></a></td><td>" + rs.getString("category") + "</td><td>" + rs.getString("brand") + "</td><td>" + rs.getString("price") + "</td></tr>");
            }
            out.println("</table></div></div><hr><p id=\"footer\">© EZPC Parts 2017</p><br>");
            out.println("</body>");
            out.println("</html>");
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
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
        
        String jsonResponse =
                target.path("api").path("products").
                        request(). //send a request
                        accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                        get(String.class); // use the get method and return the response as a string

//        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

        List<Product> productList = objectMapper.readValue(jsonResponse, new TypeReference<List<Product>>(){});

//        PrintWriter out = response.getWriter();
        
        request.getRequestDispatcher("products.jsp").include(request, response);
        
        for (Product product : productList){
                int pid = product.getPID();
                out.println("<tr><td>" + pid + "</td><td><a href='./productDescription?pid=");
                if(pid == 1)
                {
                    out.println("1'><img src='img/gtx1080.jpg'/><p>");
                }
                else if(pid == 2)
                {
                    out.println("2'><img src='img/gtx1070.jpg'/><p>");
                }
                else if(pid == 3)
                {
                    out.println("3'><img src='img/4690k.jpg'/><p>");
                }
                else if(pid == 4)
                {
                    out.println("4'><img src='img/4790k.jpg'/><p>");
                }
                else if(pid == 5)
                {
                    out.println("5'><img src='img/sabre.png'/><p>");
                }
                else if(pid == 6)
                {
                    out.println("6'><img src='img/g502.jpg'/><p>");
                }
                else if(pid == 7)
                {
                    out.println("7'><img src='img/blackwidow.png'/><p>");
                }
                else if(pid == 8)
                {
                    out.println("8'><img src='img/strafe.png'/><p>");
                }
                else if(pid == 9)
                {
                    out.println("9'><img src='img/h440.png'/><p>");
                }
                else
                {
                    out.println("10'><img src='img/view27.jpg'/><p>");
                }
                out.println(product.getName()+ "</p></a></td><td>" + product.getCategory() + "</td><td>" + product.getBrand() + "</td><td>" + product.getPrice() + "</td></tr>");
            }
            out.println("</table></div></div><hr><p id=\"footer\">&copy; EZPC Parts 2017</p><br>");
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
