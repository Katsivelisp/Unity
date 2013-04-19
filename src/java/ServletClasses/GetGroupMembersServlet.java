package ServletClasses;

import JavaConnector.Group;
import JavaConnector.Post;
import JavaConnector.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author UNITY Developers
 */
public class GetGroupMembersServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetGroupMembersServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetGroupMembersServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {            
            out.close();
        }
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
        PrintWriter out = response.getWriter();
        
        Utils util  = new Utils();
        Post utilPost = new Post();
        Group utilGroup = new Group();
        
        Boolean isGroupOwner = Boolean.parseBoolean( request.getParameter("isGroupOwner") );
        Long owner = Long.parseLong( request.getParameter("owner") );
        Long goid = Long.parseLong( request.getParameter("goid") );
        
        System.out.println("LALAALALALALALALALLALALAALALALALAL");
        
        ArrayList<Long> members =utilGroup.getMembers(goid);
        
        Iterator<Long> itr = members.iterator();
        
        while ( itr.hasNext() ) {
            Long memberOid = itr.next();
          
            String memberPhoto = util.getPersonProfilePicture(memberOid);
            String memberName = util.getPersonDisplayName(memberOid);
            String memberPerson_Id = util.getPersonName(memberOid);

            
            out.println( "<div class=\"member\">" );
            out.println( "<div class=\"memberimg\"><img src=\""+memberPhoto+"\"/></div>" );
            out.println( "<div class=\"membername\"><a href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+memberPerson_Id+"\" >"+memberName+"<a/>" );
            if ( isGroupOwner )
                out.println( "<div class=\"deleteimg\"><a href=\"#\"><img src=\"../photos/user_delete.png\" onclick=\"removeMember("+memberOid+");\" title=\"Remove Member\" /></a></div></div>" );
            
            out.println( "</div>" );
        }
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
