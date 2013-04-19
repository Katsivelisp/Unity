<%-- 
    Document   : profile_posts
    Created on : Aug 1, 2011, 10:03:40 PM
    Author     : UNITY Developers
--%>

<%@page import="JavaConnector.*"%>
<%@page import="SocialObjects.*"%>
<%@page import="ServletClasses.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="show_posts" > 
    <%
        ArrayList<PostObject> posts =  post.getWallPosts( ownerDbId, "wall" );
        if( posts == null ) {
            out.println("No wall or no posts for this wall");
        }
        else {
            int i = posts.size() - 1;
            int count = 0;
            while( i >= 0 ) {
                String person_id = util.getPersonName(posts.get(i).getPerson_id());
                String person_image = "<div class=\"postimg\"><img src=\"" 
                                    + util.getPersonProfilePicture(person_id)+"\"/></div>";
                String person_link =  "<a href=\"http://localhost:8084/Web/pages/profile.jsp?owner=" 
                                   + person_id+"\" >"+util.getPersonDisplayName(person_id) +"</a>";           
                String message = "<div class=\"posttext\">" + person_link + "<br />" 
                               + posts.get(i).getMessage();
                String posted_type = posts.get(i).getPosted_type();
                Timestamp posted_time = posts.get(i).getPosted_time();

                Long oid = posts.get(i).getOid();
                Long posted_to = posts.get(i).getPosted_to();
                String posted_target  = posts.get(i).getPosted_target();

                Timestamp now = new Timestamp(new java.util.Date().getTime());
                ArrayList<String> diff = util.getTimePassed(now.getTime(), posted_time.getTime());
                String x = "<a href=\"../servlet/DeletePostServlet?post_oid=" 
                         +oid+"&owner="+owner+"\" ><img src=\"../photos/img_close.png\" "
                         + "width=\"10px\" height=\"10px\" class=\"post_x\" /></a>";
                String time = "<div class=\"posttime\">" + diff.get(0) + " " + diff.get(1) + " ago</div></div>";
                String time_plus_x = "<div class=\"posttime\">" + diff.get(0) + " " 
                                   + diff.get(1) + " ago " + x + "</div></div>";

                String printIt = "<div class=\"post\" >" + person_image /*+ person_link*/ + message;

                boolean postPrinted = false;
                if ( posted_type.equalsIgnoreCase("wall")) {
                    if ( posted_target.equalsIgnoreCase("public")) {
                        out.println( printIt );
                        postPrinted = true;
                    }
                    else if ( posted_target.equalsIgnoreCase("self")) {
                        if ( isViewerOwner ) {
                            out.println(printIt);
                            postPrinted = true;
                         }
                    }
                    else if ( posted_target.equalsIgnoreCase("fellows")) {
                        if ( util.isFellow(person.getOid(), ownerDbId) || isViewerOwner){
                            out.println(printIt);
                            postPrinted = true;
                         }
                    }
                    else if ( posted_target.equalsIgnoreCase("students")) {
                        if ( util.isTutor(person.getOid(), ownerDbId ) || isViewerOwner ){
                            out.println(printIt);
                            postPrinted = true;
                         }    
                    }
                 }
                 else if ( posted_type.equalsIgnoreCase("group") ) {
                    out.println(printIt);
                    postPrinted = true;
                 }
                 else if ( posted_type.equalsIgnoreCase("fellow") ) {
                    String wall_person =  "<a href=\"http://localhost:8084/Web/pages/profile.jsp?owner="
                                       +util.getPersonName(posted_to)+"\" >" 
                                       + util.getPersonDisplayName(posted_to) +"</a>";
                    message = "<div class=\"posttext\">" + person_link + " to " 
                            + wall_person + "<br />" + posts.get(i).getMessage();

                    printIt = "<div class=\"post\" >" + person_image + message;
                    out.println(printIt);
                    postPrinted = true;
                 } 

                 if ( isViewerOwner && postPrinted == true ) { 
                    out.println(time_plus_x + "</div>");
                 } 
                 else if( postPrinted == true ){ out.println(time + "</div>"); 

                 }
                 i--;
            }
        }
    %>
</div>