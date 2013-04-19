<%-- 
    Document   : search
    Created on : Jul 20, 2011, 6:51:15 PM
    Author     : Unity Developers
--%>

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
        <title>Search Page</title>
        <link rel=StyleSheet href="../css/nav.css" type="text/css" media="screen">
        <link rel="StyleSheet" href="../css/global.css" type="text/css" media="screen">
        <script type="text/javascript" src="../js/navBarFX.js"></script>
        <script type="text/javascript" >
            function showHintEmail(str) {
                var xmlhttp;
                if (str.length==0) { 
                    document.getElementById("txtHint").innerHTML="";
                    return;
                }
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                
                xmlhttp.onreadystatechange=function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
                    }
                }
                xmlhttp.open("GET","../servlet/SearchHintServlet?str="+str, true);
                xmlhttp.send();
            }
            
            function showHintName(str) {
                var xmlhttp;
                if (str.length==0) { 
                    document.getElementById("txtHintName").innerHTML="";
                    return;
                }
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                
                xmlhttp.onreadystatechange=function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        document.getElementById("txtHintName").innerHTML=xmlhttp.responseText;
                    }
                }
                xmlhttp.open("GET","../servlet/SearchHintNameServlet?str="+str, true);
                xmlhttp.send();
            }
            
            function showHintGroup(str) {
                var xmlhttp;
                if (str.length==0) { 
                    document.getElementById("txtHintGroup").innerHTML="";
                    return;
                }
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                
                xmlhttp.onreadystatechange=function() {
                    if (xmlhttp.readyState==4 && xmlhttp.status==200)
                        document.getElementById("txtHintGroup").innerHTML = xmlhttp.responseText;
                }
                xmlhttp.open("GET", "../servlet/SearchHintGroupServlet?owner=<%= owner%>&str="+str, true);
                xmlhttp.send();
            }
        </script>
    </head>
    <body onload="MM_preloadImages
        ('../photos/images/hover/menu-bar_01_01.jpg',
        '../photos/images/hover/menu-bar_01_02.jpg',
        '../photos/images/hover/menu-bar_01_03.jpg',
        '../photos/images/hover/menu-bar_01_04.jpg',
        '../photos/images/hover/menu-bar_01_05.jpg')">

        <%@include file="nav.jsp" %>
                
        <br/><br/><br/>
        <span>
        <h1>Search Person by Email</h1>
        <h3>Start typing the email of the person in the input field below : </h3>
            Email : <input type="text" id="txt" onkeyup="showHintEmail(this.value)" />
        <p>People : <span id="txtHint"></span></p>
        </span>
          
        <span>
        <h1>Search Person By Name</h1>
        <h3>Start typing the name of the person in the input field below : </h3>
            Name : <input type="text" id="txtName" onkeyup="showHintName(this.value)" />
        <p>People : <span id="txtHintName"></span></p>
        </span>
        
        <span>
        <h1>Search Group by Name</h1>
        <h3>Start typing the name of the group in the input field below : </h3>
            Name : <input type="text" id="txtGroup" onkeyup="showHintGroup(this.value)" />
        <p>Group : <span id="txtHintGroup"></span></p>
        </span>
    </body>
</html>
