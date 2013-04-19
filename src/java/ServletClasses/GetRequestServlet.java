/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletClasses;

import JavaConnector.Person;
import JavaConnector.Utils;
import SocialObjects.PersonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author it20714
 */
public class GetRequestServlet extends HttpServlet {

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
            out.println("<title>Servlet GetRequestServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetRequestServlet at " + request.getContextPath () + "</h1>");
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
        
        Person utilPerson = new Person();
        Utils util  = new Utils();
        Long owner = Long.parseLong( request.getParameter("owner") );
        
        ArrayList<PersonObject> candidates = utilPerson.getFriendRequest(owner);
        int i = 0;

        if ( candidates == null || candidates.size() == 0 ) {
            out.println("<p style=\"color : red;\" >No news good news!</p>");
        }
        else {
            while ( i < candidates.size() ) {
                
                String name = candidates.get(i).getDisplayName();
                String role = util.convertAcademicRole( candidates.get(i).getAcademicRole() );
                String photoUrl = candidates.get(i).getProfile_picture();
                String person_id = candidates.get(i).getPersonId();
                Long oid = candidates.get(i).getOid();

                out.println("<p id=\"request"+i+"\" ");
                out.println("<img src=\"" + photoUrl + "\"/>");
                out.println("<a href=\"profile?owner=" + person_id + "\" >" + name + "</a>" );
                out.println(role + "<br />");
                out.println( "<input type=\"button\" id=\"accept_request\" value=\"confirm fellowship\" onclick=\"acceptRequest("+oid+", request"+i+")\" />" );
                out.println( "<input type=\"button\" id=\"deny_request\" value=\"deny fellowship\" onclick=\"denyRequest("+oid+", request"+i+")\" />" );
                out.println("</p>");  
                
                i++;
            }
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
