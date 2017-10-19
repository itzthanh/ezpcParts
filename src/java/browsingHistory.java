import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/browsingHistory"})
public class browsingHistory extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            HttpSession session = request.getSession();
            if(session.getAttribute("productHistory") != null){
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "1803");
                //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
                Connection conn = DriverManager.getConnection("jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124-db-053", "inf124-db-053", "fyy1arKN!Z<k");
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(browsingHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(browsingHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(browsingHistory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(browsingHistory.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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

}
