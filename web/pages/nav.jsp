<%-- 
    Document   : nav
    Created on : Aug 22, 2011, 6:13:13 PM
    Author     : Unity Developers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div id="navbar">
    <div id="navlogo" ><img src="../photos/unity_logo.png">
    </div>
    <div id="navmenu">
        <a href="main.jsp?owner=<%= user.getViewer()%>" 
           onmouseout="MM_swapImgRestore()" 
           onmouseover="MM_swapImage('Home','','../photos/images/hover/menu-bar_01_01.gif',1)">
            <img src="../photos/images/menu-bar_01.gif" alt="Home" name="Home" width="90" 
                                height="43" border="0" id="Home" /></a>
        <a href="profile.jsp?owner=<%= user.getViewer()%>" 
           onmouseout="MM_swapImgRestore()" 
           onmouseover="MM_swapImage('Profile','','../photos/images/hover/menu-bar_01_02.gif',1)">
            <img src="../photos/images/menu-bar_02.gif" alt="Profile" name="Profile" 
                 width="90" height="43" border="0" id="Profile" /></a>
        <a href="groups.jsp?owner=<%= user.getViewer()%>" 
           onmouseout="MM_swapImgRestore()" 
           onmouseover="MM_swapImage('Groups','','../photos/images/hover/menu-bar_01_03.gif',1)">
            <img src="../photos/images/menu-bar_03.gif" alt="Groups" name="Groups" 
                 width="90" height="43" border="0" id="Groups" /></a>
        <a href="apps.jsp?owner=<%= user.getViewer()%>" 
           onmouseout="MM_swapImgRestore()" 
           onmouseover="MM_swapImage('Applications','','../photos/images/hover/menu-bar_01_06.gif',1)">
            <img src="../photos/images/menu-bar_05.gif" alt="Applications" name="Applications" 
                 width="100" height="43" border="0" id="Applications" /></a>
        <a href="search.jsp?owner=<%= user.getViewer()%>" 
           onmouseout="MM_swapImgRestore()" 
           onmouseover="MM_swapImage('Search','','../photos/images/hover/menu-bar_01_04.gif',1)">
            <img src="../photos/images/menu-bar_04.gif" alt="Search" name="Search" 
                 width="90" height="43" border="0" id="Search" /></a>
        <a href="logout.jsp?owner=<%= user.getViewer()%>" 
           onmouseout="MM_swapImgRestore()" 
           onmouseover="MM_swapImage('Logout','','../photos/images/hover/menu-bar_01_05.gif',1)">
            <img src="../photos/images/menu-bar_07.gif" alt="Logout" name="Logout" 
                 width="90" height="43" border="0" id="Logout" /></a>
    </div>
    <div id="navperson">
        <a href="profile.jsp?owner=<%= user.getViewer()%>">
            <img src="<%= person.getProfile_picture()%>"/>
            <p><%= person.getDisplayName() %></p>
        </a>
    </div>
    <div id="navupdates">
        <a href="requests.jsp?owner=<%= user.getViewer()%>" >
            <img id="requestsImg" src="../photos/requests.png"/></a>
    </div>
</div>