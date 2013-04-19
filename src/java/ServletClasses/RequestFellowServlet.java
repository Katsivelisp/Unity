/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletClasses;

import JavaConnector.Person;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author it20739
 */
public class RequestFellowServlet extends HttpServlet {

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
            out.println("<title>Servlet RequestFellowServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestFellowServlet at " + request.getContextPath () + "</h1>");
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
        
        Long person_id = Long.parseLong( request.getParameter( "viewer" ) );
        Long fellow_id = Long.parseLong( request.getParameter( "owner" ) );

        Person utilPerson = new Person();
        
        String result = utilPerson.setFriendRequest(person_id, fellow_id);
               
        if ( result.equalsIgnoreCase("Done!")) {
            out.println("<b>request sent</b>");
        }
        else {
            out.println("<b>unable to send request</b>");
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
 
        Long person_id = Long.parseLong( request.getParameter( "viewer" ) );
        Long fellow_id = Long.parseLong( request.getParameter( "owner" ) );

        Person utilPerson = new Person();
        
        String result = utilPerson.setFriendRequest(person_id, fellow_id);
        
        if ( result.equalsIgnoreCase("Done!")) {
            out.println("<b>request sent</b>");
        }
        else {
            out.println("<b>unable to send request</b>");
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
