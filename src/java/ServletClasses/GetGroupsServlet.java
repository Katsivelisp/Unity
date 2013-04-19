package ServletClasses;

import JavaConnector.Group;
import JavaConnector.Utils;
import SocialObjects.GroupObject;
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
public class GetGroupsServlet extends HttpServlet {

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
            out.println("<title>Servlet GetGroupsServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetGroupsServlet at " + request.getContextPath () + "</h1>");
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
        
        PrintWriter out = response.getWriter();
        Group utilGroup = new Group();
        Utils util = new Utils();
        
        String owner = request.getParameter("owner") ;
        Long ownerDbId = util.getPersonObjectId(owner);
        String type = request.getParameter("type") ;
        
        ArrayList<GroupObject> groups = utilGroup.getGroups(type);
        Iterator itr = groups.iterator();
        while( itr.hasNext() ) {      
            GroupObject group = (GroupObject) itr.next();
            
            String group_image = "<p class=\"groupimg\"><img src=\""+group.getPicture()+"\"/></p>";
            String group_name  = "<p class=\"groupname\"><a href=\"group.jsp?goid=" 
                               + group.getOid()+"\" >"+group.getId() +"</a><br/>";
            String group_owner = "by <a href=\"profile.jsp?owner=" 
                               + util.getPersonName( group.getPerson_id() ) + "\" >" 
                               + util.getPersonDisplayName(group.getPerson_id()) +"</a></p>";
            String output = "<div class=\"group\">"+ group_image + group_name + group_owner + "</div>";           
            out.println(output);
        }       
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
