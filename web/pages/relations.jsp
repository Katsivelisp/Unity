<%-- 
    Document   : relations
    Created on : Jul 20, 2011, 6:50:59 PM
    Author     : UNITY Developers
--%>

<%@ page import="JavaConnector.Utils"%>
<%@ page import="JavaConnector.*" %>
<%@ page import="SocialObjects.*" %>

<jsp:useBean id="user" class="Beans.PersonBean" scope="session"/>

<%
    Utils util = new Utils();
    String owner = request.getParameter("owner");
    Long ownerDbId = util.getPersonObjectId( owner );   
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
    
    boolean isViewerOwner =  ( person.getOid() ==  ownerDbId ) ? true : false;
    
    request.setAttribute("viewerId", person.getOid());
    request.setAttribute("ownerId", ownerDbId);
    
    if( ( user.getViewer() == null ) || ownerDbId == 0 || session == null ) {
        response.sendRedirect("../index.jsp");
    }
    
    String post_type = "fellow";    // fellow's wall
    if( isViewerOwner )
        post_type = "wall";         // own wall
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relations Page</title>
        <LINK rel="StyleSheet" href="../css/main_style.css" type="text/css" media="screen">
        <script type="text/javascript" src="../js/navBarFX.js"></script>
    </head>
    <body onload="MM_preloadImages
        ('../photos/images/hover/menu-bar_01_01.jpg',
         '../photos/images/hover/menu-bar_01_02.jpg',
         '../photos/images/hover/menu-bar_01_03.jpg',
         '../photos/images/hover/menu-bar_01_04.jpg',
         '../photos/images/hover/menu-bar_01_05.jpg')">

        <div id="menu" >
            <div id="navbar">
                <div id="navmenu">
                    <a href="main.jsp?owner=<%= owner%>" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Home','','../photos/images/hover/menu-bar_01_01.jpg',1)"><img src="../photos/images/menu-bar_01.jpg" alt="Home" name="Home" width="100" height="50" border="0" id="Home" /></a>
                    <a href="profile.jsp?owner=<%= owner%>" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Profile','','../photos/images/hover/menu-bar_01_02.jpg',1)"><img src="../photos/images/menu-bar_02.jpg" alt="Profile" name="Profile" width="100" height="50" border="0" id="Profile" /></a>
                    <a href="groups.jsp?owner=<%= owner%>" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Groups','','../photos/images/hover/menu-bar_01_03.jpg',1)"><img src="../photos/images/menu-bar_03.jpg" alt="Groups" name="Groups" width="100" height="50" border="0" id="Groups" /></a>
                    <a href="search.jsp?owner=<%= owner%>" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Search','','../photos/images/hover/menu-bar_01_04.jpg',1)"><img src="../photos/images/menu-bar_04.jpg" alt="Search" name="Search" width="100" height="50" border="0" id="Search" /></a>
                    <a href="logout.jsp?owner=<%= owner%>" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Logoff','','../photos/images/hover/menu-bar_01_05.jpg',1)"><img src="../photos/images/menu-bar_05.jpg" alt="Logoff" name="Logoff" width="100" height="50" border="0" id="Logoff" /></a>
                </div>
            </div>
            <% 
                if ( isViewerOwner ) {
            %>
            <a href="requests.jsp?owner=<%= user.getViewer()%>" >Friend Requests</a>
            <%
                }
            %>
            <h1>Here are your relations beloved <% out.println(owner); %></h1> <br />
            <a href="logout.jsp?owner=<%= user.getViewer()%>" >Logout</a><br /><br /><br />
        </div>
    </body>
</html>
