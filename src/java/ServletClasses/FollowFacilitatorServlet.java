package ServletClasses;

import JavaConnector.Activity;
import JavaConnector.ActivityVerb;
import JavaConnector.Person;
import JavaConnector.Utils;
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
public class FollowFacilitatorServlet extends HttpServlet {

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
            out.println("<title>Servlet FollowFacilitatorServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FollowFacilitatorServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        
        Long viewer =  Long.parseLong( request.getParameter("viewer") );
        Long owner =  Long.parseLong( request.getParameter("owner") );
        
        Activity activity = new Activity();
        Person utilPerson = new Person();
        Utils util = new Utils();
        String result = utilPerson.addFriend(viewer, owner, "facilitator");
        
        if ( result.equalsIgnoreCase("Done!")) {
            /* create activity */
            activity.createActivity( util.getPersonName(viewer), ActivityVerb.FOLLOW.toString(), 
                                            util.getPersonName(owner), "public");
            out.println("ok");
        }
        else {
            out.println("not ok");
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
