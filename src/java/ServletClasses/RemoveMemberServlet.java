/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletClasses;

import JavaConnector.Activity;
import JavaConnector.ActivityVerb;
import JavaConnector.Group;
import JavaConnector.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author valentino
 */
public class RemoveMemberServlet extends HttpServlet {

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
            out.println("<title>Servlet RemoveMemberServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RemoveMemberServlet at " + request.getContextPath () + "</h1>");
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
        Group utilGroup = new Group();
        Activity utilActivity = new Activity();
        
        Long owner = Long.parseLong(  request.getParameter("owner"));
        Long group_id = Long.parseLong( request.getParameter("group") );
        Long member = Long.parseLong( request.getParameter("member") );
        String member_id = util.getPersonName(member);
        
        
        
        if ( owner == utilGroup.getGroup(group_id).getPerson_id() ) { /* Owner deletes member */
            String result = utilGroup.removeMemberfromGroup(group_id, member);
            if ( result.equalsIgnoreCase("Done!")) {
                result = utilActivity.createActivity(member_id, ActivityVerb.REMOVE.toString(), group_id.toString(), "self");
                if ( result.equalsIgnoreCase("Done!")) {
                    out.println("Done!");
                }
                else {
                    out.println("NOT Done!");
                }
            }
            else {
                // do nothing. try again
                out.println("NOT Done!");
            }
        }
        else if ( owner == member ){ /* member leaves the group */
            String result = utilGroup.removeMemberfromGroup(group_id, member);
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
        PrintWriter out = response.getWriter();

        Utils util  = new Utils();
        Group utilGroup = new Group();
        Activity utilActivity = new Activity();
        
        String owner = request.getParameter("owner");
        Long group_id = Long.parseLong( request.getParameter("group") );
        Long member = Long.parseLong( request.getParameter("member") );
        String member_id = util.getPersonName(member);
        
        String result = utilGroup.removeMemberfromGroup(group_id, member);
        
        if ( result.equalsIgnoreCase("Done!")) {
            utilActivity.createActivity(member_id, ActivityVerb.REMOVE.toString(), group_id.toString(), "self");
        }
        else{
            // do nothing. try again
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
