<%-- 
    Document   : group
    Created on : Aug 4, 2011, 5:31:18 PM
    Author     : UNITY Developers
--%>

<%@ page import="JavaConnector.*" %>
<%@ page import="SocialObjects.*" %>
<%@ page import="ServletClasses.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Timestamp"%>

<jsp:useBean id="user" class="Beans.PersonBean" scope="session"/>

<%
    Utils util = new Utils();
    Post utilPost = new Post();
    Group utilGroup = new Group();
    Person utilPerson = new Person();
    
    Long goid = Long.parseLong( request.getParameter("goid") );
    PersonObject person;
    
    if( user.getStudent() != null ) {
        person = user.getStudent();
    }
    else if (user.getTeacher() != null ) {
        person = user.getTeacher();
    }
    else {
        person = user.getStaff();
    }
    GroupObject group = utilGroup.getGroup(goid);

    boolean isGroupOwner = ( person.getOid() == group.getPerson_id() ) ? true : false;
    boolean isUserMember = util.isMemberOfGroup(goid, person.getOid());
    
    String creator_name;
    String creator_id;  
    if ( ! isGroupOwner ) {
        creator_name = util.getPersonDisplayName(group.getPerson_id());
        creator_id = util.getPersonName(group.getPerson_id());
    }   
    if( ( user.getViewer() == null ) || person.getOid() == 0 || session == null ) {
        response.sendRedirect("../index.jsp");
    }
    String post_type = "group";    
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        
        <title>Group Page</title>
        <link rel="StyleSheet" href="../css/global.css" type="text/css" media="screen" />
        <link rel=StyleSheet href="../css/group_style.css" type="text/css" media="screen" />
        <link rel=StyleSheet href="../css/nav.css" type="text/css" media="screen" />
        
        <script type="text/javascript" src="../js/requiered/jquery.js"></script>
        <script type="text/javascript" src="../js/jquery_effects.js"></script>
        <script type="text/javascript" src="../js/functions.js" ></script>
        <script src="../js/colorbox/jquery.colorbox.js"></script>      
        <script type="text/javascript" >
            function checkRequest() {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState==4 && xmlhttp.status==200 ) {
                         var a = xmlhttp.responseText;
                         if (  a == 1 ) {
                             var image = document.getElementById("requestsImg");
                             image.src = "../photos/request1.png";
                         } else {
                             var image = document.getElementById("requestsImg");
                             image.src = "../photos/requests.png";
                         }
                    }
                }
                
                var param = "owner=<%= person.getOid()%>";
                xmlhttp.open("POST","../servlet/CheckRequestFellowServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             } // end function
             
            function getGroupMembers() {
                var xmlhttp;              
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        document.getElementById("members").innerHTML = xmlhttp.responseText;
                    }
                }                
                var param = "isGroupOwner=<%= isGroupOwner%>&owner=<%= person.getOid()%>&goid=<%= goid%>";
                xmlhttp.open("GET", "../servlet/GetGroupMembersServlet?"+param, true);
                xmlhttp.send();
             } // end function       
            
            function getWallGroupPosts() {
                var xmlhttp;                
                document.getElementById("show_posts").innerHTML = "";
                
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        document.getElementById("show_posts").innerHTML = xmlhttp.responseText;
                    }
                }

                var param = "owner=<%= person.getOid()%>&goid=<%= goid%>";
                xmlhttp.open("GET", "../servlet/GetWallGroupPostsServlet?"+param, true);
                xmlhttp.send();
             } // end function
            
            function createPost() {             
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        getWallGroupPosts();
                    }
                }
                
                var param = "owner=<%= person.getOid()%>&goid=<%= goid%>&post=" 
                                        + document.getElementById("status").value;
                xmlhttp.open("GET","../servlet/NewPostGroupServlet?"+param, true);
                xmlhttp.send();            
             } // end function
             
             function deletePost(post_id) {              
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange = function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        getWallGroupPosts();
                    }
                }
                var param = "post_id=" + post_id;
                xmlhttp.open("GET","../servlet/DeleteGroupPostServlet?" + param, true);
                xmlhttp.send();
             } // end function
             
             function removeMember(member_id) {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        getGroupMembers();
                        setTimeout("location.reload(true);", 2000);
                    }
                }
                
                var param = "owner=<%= person.getOid()%>&group=<%= goid%>&member=" + member_id;
                xmlhttp.open("GET","../servlet/RemoveMemberServlet?"+param, true);
                xmlhttp.send();
             } // end function
             
             function joinGroup() {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        setTimeout("location.reload(true);", 2000);
                    }
                }

                var param = "owner=<%=person.getOid()%>&goid=<%= goid%>";
                xmlhttp.open("GET","../servlet/JoinGroupServlet?"+param, true);
                xmlhttp.send();
             } // end function

             function deleteGroup() {             
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange = function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        window.location.href = '../pages/groups.jsp?owner=<%=person.getOid()%>'
                    }
                }

                var param = "owner=<%=person.getOid()%>&goid=<%= goid%>";
                xmlhttp.open("POST","../servlet/DeleteGroupServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);        
            } // end function         
        </script>
    </head>
    <body onload="checkRequest(); getGroupMembers(); getWallGroupPosts(); MM_preloadImages
        ('../photos/images/hover/menu-bar_01_01.gif',
         '../photos/images/hover/menu-bar_01_02.gif',
         '../photos/images/hover/menu-bar_01_03.gif',
         '../photos/images/hover/menu-bar_01_04.gif',
         '../photos/images/hover/menu-bar_01_05.gif')">

        <%@include file="nav.jsp" %>
        <div id="div_main">
            <div id="left"><br />
                <div id="group_pic">
                    <img id ="groupImg" src="<%= group.getPicture()%>" />
                    <%
                        if ( isUserMember && !isGroupOwner) { //member but not owner
                            out.println( "<div id=\"registeredMember\" ><h4>Member</h4>"
                              + "<a href=\"#\"><img src=\"../photos/user_delete.png\" "
                              + "onclick=\"removeMember("+person.getOid()+");\" "
                              + "title=\"Leave Group\" /></a></div>");
                        }
                        else if (isGroupOwner){
                            out.println( "<div id=\"registeredMember\" ><h4>Owner</h4>"
                              + "<a href=\"#\"><img src=\"../photos/user_delete.png\" "
                              + "onclick=\"deleteGroup();\" "
                              + "title=\"Delete Group\" /></a></div>");
                        }
                    %>
                    <br />
                    <div id="personal_info_academic">
                        <span class="info_title"><b>Information</b></span>
                        <hr class="info_hr" />
                        <div class="information">
                            <b>Creator</b> <br/>
                            <a href="profile.jsp?owner=<%= util.getPersonName(group.getPerson_id())%>">
                                <%= util.getPersonDisplayName(group.getPerson_id()) %></a><br/></br>
                            <b>Group Type</b> <br/>
                            <% 
                                if ( group.getType().equalsIgnoreCase("fun") ) 
                                    out.println("Fun"); 
                                else 
                                    out.println("Class");  
                            %>
                            <br/></br>
                            <b>Short Description</b> <br/><%= group.getDescription() %><br/>
                        </div>
                    </div>
                 </div>        
            </div>
            <div id="content"><br />            
                <h1 style="display: inline;"><%= group.getId()%></h1>
                <%
                    if ( isGroupOwner ) {
                %>
                <a href="gedit.jsp?goid=<%=group.getOid()%>" >
                    <img src="../photos/edit.png" width="20" height="20" title="Edit group"/></a>
                <%
                    }
                    if ( ( ! isUserMember ) && ( ! isGroupOwner ) ){ // not a member or owner
                        out.println("<button id=\"joinG\" onclick=\"joinGroup();\" "
                                             + "class=\"green\">Join Group</button>");
                    }
                %>
                <img id="Ok" src="../photos/tick.png" />
                <div id="posts" ><br/>
                    <h2>Posts</h2>         
                    <% 
                        if (  isUserMember || isGroupOwner ) { 
                    %>
                        <p class="create_post">   
                            <input name="post" id="status" placeholder="Post something" type="text"/>
                            <input class="myButton"  type="submit" name="button" value="share" 
                                                                    onclick="createPost();" />
                        </p> <br /> <br />                
                    <% } %>            
                    <div id="show_posts" ></div>   
                </div>
            </div>                
            <div id="right" >
                <h1>Members</h1>
                <p id="members"></p>
            </div>
        </div>                             
    </body>
</html>
