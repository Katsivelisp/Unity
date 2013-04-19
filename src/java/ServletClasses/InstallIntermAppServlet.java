package ServletClasses;

import JavaConnector.Activity;
import JavaConnector.ActivityVerb;
import JavaConnector.Application;
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
public class InstallIntermAppServlet extends HttpServlet {

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
            out.println("<title>Servlet InstallIntermAppServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InstallIntermAppServlet at " + request.getContextPath () + "</h1>");
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
        Long viewer =  Long.parseLong( request.getParameter("viewer") );
        Long interm =  Long.parseLong( request.getParameter("interm") );
        String app = request.getParameter("app");
        String interm_app = app +"-Interm";
        String type = "intermediate";
        Person p = new Person();
        Utils util = new Utils();
        Application a = new Application();
        Activity activity = new Activity();
        p.addIntermediate(viewer, interm, type, a.getApplicationObjectId(app));
        a.installIntermApp(a.getApplicationObjectId(app), interm, viewer);
        activity.createNewActivity(util.getPersonName(viewer), interm_app, 
                ActivityVerb.INTERMEDIATE.toString(), util.getPersonName(interm), "private");
        activity.createNewActivity(util.getPersonName(viewer), interm_app, 
                ActivityVerb.INTERMEDIATE.toString(), util.getPersonName(interm), "public");
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
