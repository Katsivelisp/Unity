/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletClasses;

import JavaConnector.Group;
import JavaConnector.Post;
import JavaConnector.Utils;
import SocialObjects.PostObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author valentino
 */
public class GetWallGroupPostsServlet extends HttpServlet {

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
            out.println("<title>Servlet GetWallGroupPostsServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetWallGroupPostsServlet at " + request.getContextPath () + "</h1>");
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
        Long owner = Long.parseLong( request.getParameter("owner") );
        Long goid = Long.parseLong( request.getParameter("goid") );
        
        ArrayList<PostObject> posts =  utilPost.getWallPosts( goid, "group" );
                                   
            if( posts == null ) {
                out.println("No posts yet. Be the first to post!");
            }
            else {
                ListIterator itr1 = posts.listIterator(posts.size());
                int i = 0;
                while( itr1.hasPrevious() && i < 7 ) {
                    
                    PostObject post = ( PostObject ) itr1.previous();
                    
                    boolean isPostOwner = ( owner == post.getPerson_id() ) ? true : false;
                    boolean isGroupOwner = ( post.getPerson_id() == utilGroup.getGroup(goid).getPerson_id() ) ? true : false;
                    
                    String person_id = util.getPersonName(post.getPerson_id());
                    String person_image = "<div class=\"postimg\"><img src=\""+util.getPersonProfilePicture(person_id)+"\"/></div>";
                    String person_link;
                    if ( isGroupOwner ) {
                        person_link =  "<a href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+person_id+"\" >"+util.getPersonDisplayName(person_id) +" (<span style=\"color:maroon;\">admin</span>)</a>";           
                    }
                    else {
                        person_link =  "<a href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+person_id+"\" >"+util.getPersonDisplayName(person_id) + " </a>";           
                    }
                    
                    String message = "<div class=\"posttext\">" + person_link + "<br />" + post.getMessage();                    
                    
                    Long oid = post.getOid();
                    Long posted_to = post.getPosted_to();
                    String posted_target  = post.getPosted_target();
                    
                    Timestamp posted_time = post.getPosted_time();
                    Timestamp now = new Timestamp(new java.util.Date().getTime());
                    ArrayList<String> diff = util.getTimePassed(now.getTime(), posted_time.getTime());
                    
                    String x = "<a href=\"#\" ><img src=\"../photos/img_close.png\" width=\"10px\" height=\"10px\" class=\"post_x\" onclick=\"deletePost("+post.getOid()+");\" /></a>";
                    String time = "<div class=\"posttime\">" + diff.get(0) + " " + diff.get(1) + " ago</div></div>";
                    String time_plus_x = "<div class=\"posttime\">" + diff.get(0) + " " + diff.get(1) + " ago " + x + "</div></div>";
                    
                    String printIt = "<div class=\"post\" >" + person_image + message;
                    boolean postPrinted = false;
                    
                    out.println(printIt);
                    postPrinted = true;
                    
                    if ( isPostOwner && postPrinted == true ) { 
                        out.println(time_plus_x + "</div>");
                    } 
                    else if( postPrinted == true ){ 
                        out.println(time + "</div>");             
                    }
                    
                    i++;
                } /* while */
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
