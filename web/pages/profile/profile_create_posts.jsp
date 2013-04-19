<%-- 
    Document   : profile_create_posts
    Created on : Aug 1, 2011, 10:17:31 PM
    Author     : UNITY Developers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% 
    if ( util.isFellow(person.getOid(), ownerDbId) || isViewerOwner ){ 
%>
    <div class="create_post" >   
        <form action="../servlet/NewPostServlet?viewer=<%= person.getOid()%>&owner=<%= ownerDbId%>" method="POST">
            <input name="post" id="status" placeholder="Post something" type="text" AUTOCOMPLETE="OFF"/>
            <input class="myButton" type="submit" name="button" value="share" />
            <a id="privacy" href="#">
                <img src="../photos/lock.png" alt="Privacy Settings" title="Privacy Settings"/>
            </a>
            <%
                if( person.getAcademicRole() < 4 ) {
            %>
            <select id="post_for" name="post_for">
                <option value="public" selected="selected">public</option>
                <option value="fellows">fellows</option>
                <option value="self">self</option>                                
            </select>
            <% 
                }
                else if( (person.getAcademicRole() >= 4) && (person.getAcademicRole() < 6) ) {
            %>    
            <select id="post_for" name="post_for">
                <option value="public" selected="selected">public</option>
                <option value="fellows">fellows</option>
                <option value="students">students</option>
                <option value="self">self</option>
            </select> 
            <%
                }
                else {
            %>    
            <select id="post_for" name="post_for">
                <option value="public" selected="selected">public</option>
                <option value="fellows">fellows</option>
                <option value="students">students</option>
                <option value="self">self</option>
            </select> 
            <%
                }
            %>
        </form>
    </div><br /> <br />                
<% 
    }
%>


