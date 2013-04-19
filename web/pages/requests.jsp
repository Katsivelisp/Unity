<%-- 
    Document   : requests
    Created on : Jul 29, 2011, 12:11:32 PM
    Author     : UNITY Developers
--%>

<%@page import="JavaConnector.*"%>
<%@page import="java.sql.*" %>
<%@page import="SocialObjects.*"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="profile/profile_basics.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Friend Requests</title>
        <link rel="StyleSheet" href="../css/main_style.css" type="text/css" media="screen">
        <link rel="StyleSheet" href="../css/nav.css" type="text/css" media="screen" >
        <link rel="StyleSheet" href="../css/global.css" type="text/css" media="screen" >
        <script type="text/javascript" src="../js/navBarFX.js"></script>
        <script type="text/javascript" src="../js/functions.js" ></script>
        <script type="text/javascript" >
            function getRequests() {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        document.getElementById("requests").innerHTML = xmlhttp.responseText;
                    }
                }

                var param = "owner=<%= viewerDbId%>";
                xmlhttp.open("POST","../servlet/GetRequestServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             } // end function          
             
             function acceptRequest(oid) {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange = function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        document.getElementById("requests").innerHTML = xmlhttp.responseText;
                    }
                }
                
                var param = "owner=<%= personOwner.getOid()%>&requestor="+oid;
                xmlhttp.open("POST","../servlet/AcceptRequestServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);               
             } // end function
             
             function denyRequest(oid) {                
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState==4 && xmlhttp.status==200 ) {
                        document.getElementById("requests").innerHTML = xmlhttp.responseText;
                    }
                }

                var param = "owner=<%= personOwner.getOid()%>&requestor=" + oid;
                xmlhttp.open("POST","../servlet/DenyRequestServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             }// end function

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
                
                var param = "owner=<%= ownerDbId%>";
                xmlhttp.open("POST","../servlet/CheckRequestFellowServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             } // end function

             
        </script>        
    </head>
    <body onload="checkRequest(); getRequests(); timedRefresh(10000); MM_preloadImages
        ('../photos/images/hover/menu-bar_01_01.jpg',
         '../photos/images/hover/menu-bar_01_02.jpg',
         '../photos/images/hover/menu-bar_01_03.jpg',
         '../photos/images/hover/menu-bar_01_04.jpg',
         '../photos/images/hover/menu-bar_01_05.jpg')">

        <%@include file="nav.jsp" %>  
        
        <div id="div_main">
            <div id="left"></div>
            <div id="content"> 
                <div id="requests"></div> 
            </div>
            <div id="right"></div>
        </div>
    </body>
</html>
