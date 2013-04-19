package ServletClasses;

import JavaConnector.Post;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author UNITY Developers
 */
public class DeletePostServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        

        Long post_oid =  Long.parseLong( request.getParameter("post_oid") );
        
        Post post = new Post();
        
        String result = post.deletePost(post_oid);
        
        if ( result.equalsIgnoreCase("Done!")) {
          
        }
        
        response.sendRedirect("../pages/profile.jsp?owner=" + request.getParameter("owner") );
        
    }
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
       /* System.out.print( " ^^^^^^^^^^^^^^^^^^^^^^ I am in doGet DELETEPOST" );
        Long post_oid =  Long.parseLong( request.getParameter("postOid").toString() );
        
        Post post = new Post();
        
        String result = post.deletePost(post_oid);
        
        if ( result.equalsIgnoreCase("Done!")) {
            System.out.print( " ^^^^^^^^^^^^^^^^^^^^^^ PATH IS : " + request.getContextPath() );
            System.out.print( " ^^^^^^^^^^^^^^^^^^^^^^ Path info  IS : " + request.getPathInfo() );
            System.out.print( " ^^^^^^^^^^^^^^^^^^^^^^ REQUEST URL IS : " + request.getRequestURL().toString() );
        }*/
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
