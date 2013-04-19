package ServletClasses;

import JavaConnector.Activity;
import JavaConnector.ActivityVerb;
import JavaConnector.Post;
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
public class NewPostServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
                                                throws ServletException, IOException {       
        /* to doPost xrisimopoiw .....*/        
        String postMessage = request.getParameter("post");
        String postTarget = request.getParameter("post_for");
        Long viewer =  Long.parseLong( request.getParameter("viewer") );
        Long owner =  Long.parseLong( request.getParameter("owner") );
   
        PrintWriter out = response.getWriter();
        
        out.println("\n********* POST : " + postMessage);
        out.println("\n********* POST Target : " + postTarget);
        out.println("\n********* VIEWER : " + viewer);
        out.println("\n********* OWNER : " + owner);
        
        response.sendRedirect("profile.jsp");        
        try {
            /* TODO output your page here */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewPostServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewPostServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
                                                throws ServletException, IOException {
        String post = request.getParameter("post");
        String post_for = request.getParameter("post_for");
        Long viewer =  Long.parseLong( request.getParameter("viewer") );
        Long owner =  Long.parseLong( request.getParameter("owner") );
        String post_type = "fellow";    // fellow's wall
        if( viewer == owner )
            post_type = "wall";         // own wall      
        Utils util = new Utils();     
        String owner_id = util.getPersonName(owner);      
        post(viewer, post, owner, post_type, post_for, util);
        
        /* TODO vres apo requst to onoma pou 8a pas pisw.. */
        response.sendRedirect("../pages/profile.jsp?owner=" + owner_id);    
    }
      
    public String post(Long viewer, String message, Long owner, String post_type, 
                                                    String post_for, Utils util) {
        
        Post post = new Post();
        Activity activity = new Activity();
        
        String result = post.checkPost(viewer, message, post_type, owner, post_for);
        if ( ! result.equalsIgnoreCase("ok") ) {
            System.out.println ( result );
        }
        else {
            /* create post */
            result = post.createPost(viewer, message, post_type, owner, post_for);  
            /* create activity */
            activity.createActivity(util.getPersonName(viewer), ActivityVerb.POST.toString(), 
                                                            util.getPersonName(owner), post_for);    
        }
        return "ok";
    }   
}
