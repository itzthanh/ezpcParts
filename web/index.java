import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

public class index extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, InstantiationException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           request.getRequestDispatcher("header.jsp").include(request, response);
           out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\" />");
		out.println("<div id=\"jumbotron\">");
			out.println("<img src=\"img/products-banner.jpg\"/>");
		out.println("</div>");
		out.println("<hr>");
		out.println("<br>");

		out.println("<div id=\"text-block-wrapper\">");
			out.println("<div class=\"text-block float-left\">");
				out.println("<h3>Simplified Browsing</h3>");
				out.println("<p>Browse what you need in an instant on our products page. Know what you need for your computer with a single click by viewing further details of the product.");
				out.println("</p>");
			out.println("</div>");

			out.println("<div class=\"text-block float-left\">");
				out.println("<h3>Simplified Purchasing</h3>");
				out.println("<p>Purchase the components you need in an instant. Fill out one form and you’re set for your product to arrive in pristine condition.");
				out.println("</p>");
			out.println("</div>");

			out.println("<div class=\"text-block float-left last-text-block\">");
				out.println("<h3>EZPC</h3>");
				out.println("<p>Easy peasy! With a simple website layout, get what you want in a flash. No more having to figure out where to find a specific PC part, and buy instantly.");
				out.println("</p>");
			out.println("</div>");
		out.println("</div>");
		out.println("<hr>");
		out.println("<br>");

		out.println("<div id=\"what-we-sell\">");
			out.println("<h3>What We Sell</h3>");
			out.println("<p>EZPC Parts sells some of the fundamental components required for a computer. This includes mouses, keyboards, GPUs, CPUs, and computer cases. We are continuously expanding and selling more products, so come back later and check us out!");
			out.println("</p>");
		out.println("</div>");
                out.println("<br><br>");
                out.println("<hr>");
                
                HttpSession session = request.getSession();
                if(session.getAttribute("productHistory") != null){
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "1803");
                    //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Product";
                    ResultSet rs = stmt.executeQuery(sql);
                    ArrayList productsViewed = (ArrayList) session.getAttribute("productHistory");          
                    out.println("<h2 id=\"recentlyViewedTitle\">Recently Viewed Products</h2>");
                    response.setContentType("image/jpg");
                    out.println("<div id='recentlyViewedContainer'>");
                    out.println("<div style='margin:0 auto'>");
                    for(int i = productsViewed.size() - 1; i >= 0; i--){  
                        rs.absolute((int)productsViewed.get(i));
                        out.print("<img src='" + rs.getString("image") + "'/>");
                        
                    }   
                    out.println("</div>");
                    out.println("</div>");
                    response.setContentType("text/html;charset=UTF-8");
                } 
                
		out.println("<div id=\"button\">");
			out.println("<a id=\"start\" href=\"./products\"><b><h2>GET STARTED!</h2></b></a>");
		out.println("</div>");

                out.println("<p id=\"footer\">© EZPC Parts 2017</p><br>");

	out.println("</body>");
out.println("</html>");
        } catch (IllegalAccessException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
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
        try {      
            processRequest(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
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

}
