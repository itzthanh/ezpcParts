import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import order.Order;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.client.ClientConfig;

@WebServlet(urlPatterns = {"/orderConfirmation"})
public class orderConfirmation extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            request.getRequestDispatcher("header.jsp").include(request, response);
//            out.println("<link rel=\"stylesheet\" href=\"css/order-confirmation.css\" /> ");
//            out.println("<br><br>");
//            out.println("<img id=\"checkmark\" src=\"img/checkmark.png\" />");
//            out.println("<div id=\"order-header\">");
//            out.println("<h1 class=\"text-center\">Thank you for your order!</h1><br>");
//            out.println("<p id=\"intro\">Dear " +  request.getParameter("first-name") + ",<br><br>Thank you for shopping at EZPC Parts! Your purchase helps contribute to the expansion of products we offer and online service we provide! We're processing your order now. Here are your order details and shipping information.</p>");
//            out.println("<br></div><hr>");
//            out.println("<div id=\"contact-info\" class=\"section\">");
//            out.println("<h2 class=\"section-title\">Contact Information</h2>");
//            out.println("<p class=\"section-body\">");
//            out.println("<b>Name:</b> " + request.getParameter("first-name") + " " + request.getParameter("last-name") + "<br>");
//            out.println("<b>Email:</b> " + request.getParameter("email") + "<br>");
//            out.println("<b>Phone:</b> " + request.getParameter("telephone") + "<br>");
//            out.println("</p></div><br><hr><br>");
//            out.println("<div id=\"order-details\" class=\"section\">");
//            out.println("<h2 class=\"section-title\">Order Details</h2>");
//            out.println("<p class=\"section-body\">");
//            out.println("<b>Payment Method: </b>Credit Card "+ request.getParameter("card-number") + "<br>");
//            out.println("<b>Billing Address: </b>" + request.getParameter("address") + " " + request.getParameter("city") + ", " + request.getParameter("state") + ", " + request.getParameter("postal-code"));
//            out.println("</p></div><br><hr><br>");
//            out.println("<div id=\"shipment-details\" class=\"section\">");
//            out.println("<h2 class=\"section-title\">Shipment Details</h2>");
//            out.println("<p class=\"section-body\">");
//            String shippingOption = "";
//            if (request.getParameter("shipping-option").equals("10")){
//                shippingOption = "Overnight";
//            } else if (request.getParameter("shipping-option").equals("5")){
//                shippingOption = "2-days Expedited";
//            } else{
//                shippingOption = "Free Standard";
//            }
//            out.println("<b>Delivery Option: </b>"+ shippingOption + "<br>");
//            out.println("<b>Shipping Address: </b>" + request.getParameter("shipping-address") + " " + request.getParameter("shipping-city") + ", " + request.getParameter("shipping-state") + ", " + request.getParameter("shipping-postal-code"));
//            out.println("</p></div><br><hr><br>");
//            out.println("<div id=\"item\" class=\"section\">");
//            out.println("<h2 class=\"section-title\">Your Products</h2>");
//            //PRINT OUT CART
//            HttpSession session = request.getSession();
//            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");      
//            Connection conn = DriverManager.getConnection("jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124-db-053", "inf124-db-053", "fyy1arKN!Z<k");
//            Statement stmt = conn.createStatement();
//            ArrayList<Integer> cart = (ArrayList) session.getAttribute("cart");
//            ArrayList<Integer> cartQuantity = (ArrayList) session.getAttribute("cartQuantity"); 
//            String sql = "SELECT * FROM Product";
//            ResultSet rs = stmt.executeQuery(sql);
//            for(int i = 0; i < cart.size(); i++)
//            {
//                out.println("<div class='cartItem'>");
//                out.println("<div class='cartProductText'>");
//                int cartPid = (int) cart.get(i);
//                rs.absolute(cartPid);
//                out.println("<p>Item: <span style='font-weight:bold;background-color:transparent;color:black'>" + rs.getString("name") + "</span></p>");
//                out.println("<p>Quantity: <span style='font-weight:bold;background-color:transparent;color:black'>" + cartQuantity.get(i) + "</span></p>");
//                out.println("</div>");
//                out.println("<div class='cartProductImage'>");
//                response.setContentType("image/jpg"); 
//                out.println("<img class='cartImage' src='" + rs.getString("image") + "'/>");
//                out.println("</div>");
//                response.setContentType("text/html;charset=UTF-8");
//                out.println("</div>");
//                out.println("</div>");
//            }
//            out.println("</div><br><br>");
//            out.println("<a id=\"done\" href=\"./index\"><button type=\"button\">Done</button></a>");
//            out.println("<a href=\"./products\"><button type=\"button\">Continue Shopping</button></a>");
//            out.println("<p id=\"footer\">© EZPC Parts 2017</p><br>");
//            out.println("</body>");
//            out.println("</html>");
//            
//            
//            
//            
//            stmt = null;
//            PreparedStatement pstmt = null;
//            pstmt = conn.prepareStatement("INSERT INTO Orders (OrderDate, firstName, lastName, email, phone, "
//                    + "address, city, state, zip, country, cardNumber, cardExpirationMonth, cardExpirationYear, "
//                    + "ccv, shippingCost, shippingAddress, shippingCity, shippingState, shippingZip, shippingCountry, "
//                    + "quantity, totalPrice)  VALUES ("
//                    + "?, ?, ?, ?, ?, "
//                    + "?, ?, ?, ?, ?, ?, ?, ?, "
//                    + "?, ?, ?, ?, ?, ?, ?, "
//                    + "?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
//            //pstmt.setString(1, ZonedDateTime.now());
//            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//            pstmt.setDate(1, date);
//            pstmt.setString(2, request.getParameter("first-name"));
//            pstmt.setString(3, request.getParameter("last-name"));
//            pstmt.setString(4, request.getParameter("email"));
//            pstmt.setString(5, request.getParameter("telephone"));
//            pstmt.setString(6, request.getParameter("address"));
//            pstmt.setString(7, request.getParameter("city"));
//            pstmt.setString(8, request.getParameter("state"));
//            pstmt.setInt(9, Integer.parseInt(request.getParameter("postal-code")));
//            pstmt.setString(10, request.getParameter("country"));
//            pstmt.setString(11, request.getParameter("card-number"));
//            pstmt.setInt(12, Integer.parseInt(request.getParameter("expiration-month")));
//            pstmt.setInt(13, Integer.parseInt(request.getParameter("expiration-year")));
//            pstmt.setInt(14, Integer.parseInt(request.getParameter("CCV")));
//            pstmt.setInt(15, Integer.parseInt(request.getParameter("shipping-option")));
//            pstmt.setString(16, request.getParameter("shipping-address"));
//            pstmt.setString(17, request.getParameter("shipping-city"));
//            pstmt.setString(18, request.getParameter("shipping-state"));
//            pstmt.setInt(19, Integer.parseInt(request.getParameter("shipping-postal-code")));
//            pstmt.setString(20, request.getParameter("shipping-country"));
//            int quantity = 0;
//            for (int i=0; i<cartQuantity.size(); i++){
//                quantity += cartQuantity.get(i);
//            }
//            pstmt.setInt(21, quantity);
//            pstmt.setDouble(22, Double.parseDouble(String.format("%.2f", session.getAttribute("grandTotal"))));
//            int result = pstmt.executeUpdate();
//            System.out.println(result);
//            //rs = stmt.getGeneratedKeys();
//            
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(orderConfirmation.class.getName()).log(Level.SEVERE, null, ex);
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
        try{
        request.getRequestDispatcher("header.jsp").include(request, response);
        PrintWriter out = response.getWriter();
        out.println("<link rel=\"stylesheet\" href=\"css/order-confirmation.css\" /> ");
        out.println("<br><br>");
        out.println("<img id=\"checkmark\" src=\"img/checkmark.png\" />");
        out.println("<div id=\"order-header\">");
        out.println("<h1 class=\"text-center\">Thank you for your order!</h1><br>");
        out.println("<p id=\"intro\">Dear " +  request.getParameter("first-name") + ",<br><br>Thank you for shopping at EZPC Parts! Your purchase helps contribute to the expansion of products we offer and online service we provide! We're processing your order now. Here are your order details and shipping information.</p>");
        out.println("<br></div><hr>");
        out.println("<div id=\"contact-info\" class=\"section\">");
        out.println("<h2 class=\"section-title\">Contact Information</h2>");
        out.println("<p class=\"section-body\">");
        out.println("<b>Name:</b> " + request.getParameter("first-name") + " " + request.getParameter("last-name") + "<br>");
        out.println("<b>Email:</b> " + request.getParameter("email") + "<br>");
        out.println("<b>Phone:</b> " + request.getParameter("telephone") + "<br>");
        out.println("</p></div><br><hr><br>");
        out.println("<div id=\"order-details\" class=\"section\">");
        out.println("<h2 class=\"section-title\">Order Details</h2>");
        out.println("<p class=\"section-body\">");
        out.println("<b>Payment Method: </b>Credit Card "+ request.getParameter("card-number") + "<br>");
        out.println("<b>Billing Address: </b>" + request.getParameter("address") + " " + request.getParameter("city") + ", " + request.getParameter("state") + ", " + request.getParameter("postal-code"));
        out.println("</p></div><br><hr><br>");
        out.println("<div id=\"shipment-details\" class=\"section\">");
        out.println("<h2 class=\"section-title\">Shipment Details</h2>");
        out.println("<p class=\"section-body\">");
        String shippingOption = "";
        if (request.getParameter("shipping-option").equals("10")){
            shippingOption = "Overnight";
        } else if (request.getParameter("shipping-option").equals("5")){
            shippingOption = "2-days Expedited";
        } else{
            shippingOption = "Free Standard";
        }
        out.println("<b>Delivery Option: </b>"+ shippingOption + "<br>");
        out.println("<b>Shipping Address: </b>" + request.getParameter("shipping-address") + " " + request.getParameter("shipping-city") + ", " + request.getParameter("shipping-state") + ", " + request.getParameter("shipping-postal-code"));
        out.println("</p></div><br><hr><br>");
        out.println("<div id=\"item\" class=\"section\">");
        out.println("<h2 class=\"section-title\">Your Products</h2>");
        //PRINT OUT CART
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        HttpSession session = request.getSession();
        ArrayList<Integer> cart = (ArrayList) session.getAttribute("cart");
        ArrayList<Integer> cartQuantity = (ArrayList) session.getAttribute("cartQuantity"); 
        
        Form form = new Form().param("first-name", request.getParameter("first-name"))
                .param("last-name", request.getParameter("last-name")).param("email", request.getParameter("email"))
                .param("telephone", request.getParameter("telephone")).param("address", request.getParameter("address"))
                .param("city", request.getParameter("city")).param("state", request.getParameter("state"))
                .param("postal-code", request.getParameter("postal-code")).param("country", request.getParameter("country"))
                .param("card-number", request.getParameter("card-number")).param("expiration-month", request.getParameter("expiration-month"))
                .param("expiration-year", request.getParameter("expiration-year")).param("CCV", request.getParameter("CCV"))
                .param("shipping-option", request.getParameter("shipping-option")).param("shipping-address", request.getParameter("shipping-address"))
                .param("shipping-city", request.getParameter("shipping-city")).param("shipping-state", request.getParameter("shipping-state"))
                .param("shipping-postal-code", request.getParameter("shipping-postal-code")).param("shipping-country", request.getParameter("shipping-country"))
                .param("quantity", request.getParameter("quantity")).param("grandTotal", request.getParameter("grandTotal"));

        Entity<Form> entity = Entity.form(form);
        Response jsonResponsePost =
                target.path("api").path("products").path("order").
                request().accept(MediaType.APPLICATION_JSON).post(entity);//send a request
                 //specify the media type of the response
//                get(String.class); // use the get method and return the response as a string
        
        System.out.println(target);
        
        for(int i = 0; i < cart.size(); i++)
        {
            int pid = cart.get(i);
            out.println("<div class='cartItem'>");
            out.println("<div class='cartProductText'>");

            String jsonResponse =
                target.path("api").path("products").path(Integer.toString(pid)).
                request(). //send a request
                accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                get(String.class); // use the get method and return the response as a string

//            System.out.println(jsonResponse);

            ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

            Product product = objectMapper.readValue(jsonResponse, new TypeReference<Product>(){});

            out.println("<p>Item: <span style='font-weight:bold;background-color:transparent;color:black'>" + product.getName() + "</span></p>");
            out.println("<p>Quantity: <span style='font-weight:bold;background-color:transparent;color:black'>" + cartQuantity.get(i) + "</span></p>");
            out.println("</div>");
            out.println("<div class='cartProductImage'>");
            response.setContentType("image/jpg"); 
            if(pid == 1)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/gtx1080.jpg'/>");
            }
            else if(pid == 2)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/gtx1070.jpg'/>");
            }
            else if(pid == 3)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/4690k.jpg'/>");
            }
            else if(pid == 4)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/4790k.jpg'/>");
            }
            else if(pid == 5)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/sabre.png'/>");
            }
            else if(pid == 6)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/g502.jpg'/>");
            }
            else if(pid == 7)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/blackwidow.png'/>");
            }
            else if(pid == 8)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/strafe.png'/>");
            }
            else if(pid == 9)
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/h440.png'/>");
            }
            else
            {
                out.println("<img class='cartImage' style='width:150px;height:auto' src='img/view27.jpg'/>");
            }                          
            out.println("</div>");
            response.setContentType("text/html;charset=UTF-8");
            out.println("</div>");
        }
            out.println("</div>");                            

            out.println("</div><br><br>");
            out.println("<a href='./orderCancelled' id='done' style='margin-left:35%;margin-right:10%'><button type='button'>Cancel Order</button></a>");
            out.println("<a id=\"done\" style='margin-left:0' href=\"./index\"><button type=\"button\">Done</button></a>");
            out.println("<a href=\"./products\"><button type=\"button\">Continue Shopping</button></a>");
            out.println("<p id=\"footer\">© EZPC Parts 2017</p><br>");
            out.println("</body>");
            out.println("</html>");
            session.invalidate();
        } catch (Exception e){
            PrintWriter out = response.getWriter();
            out.println(e.getMessage());
        }
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
