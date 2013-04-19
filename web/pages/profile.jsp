<%-- 
    Document   : profile
    Created on : Jul 20, 2011, 6:23:03 PM
    Author     : Unity Developers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="JavaConnector.*"%>
<%@page import="ServletClasses.*"%>
<%@page import="java.util.ArrayList"%>

<%@include file="profile/profile_basics.jsp" %>
    
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Page</title>
        <link rel="stylesheet" href="../css/colorbox.css" media="screen">
        <link rel="StyleSheet" href="../css/global.css" type="text/css" media="screen">
        <link rel="StyleSheet" href="../css/profile_style.css" type="text/css" media="screen">
        <link rel="StyleSheet" href="../css/personal_info_style.css" type="text/css" media="screen">
        <link rel="StyleSheet" href="../css/nav.css" type="text/css" media="screen">
        
        <script type="text/javascript" src="../js/requiered/jquery.js"></script>
        <script type="text/javascript" src="../js/jquery_effects.js"></script>
        <script type="text/javascript" src="../js/functions.js" ></script>
        <script type="text/javascript" src="../js/navBarFX.js"></script>
        <script type="text/javascript" src="../js/colorbox/jquery.colorbox.js"></script>         
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
                         }
                         else {
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
            
            function sendRequest() {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState==4 && xmlhttp.status==200 ) {
                        setTimeout("location.reload(true);", 7000);
                    }
                }

                var param = "viewer=<%= person.getOid()%>&owner=<%= personOwner.getOid()%>";
                xmlhttp.open("POST","../servlet/RequestFellowServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             } // end function
             
             function addTutor() {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState==4 && xmlhttp.status==200 ) {
                        setTimeout("location.reload(true);", 7000);
                    }
                }

                var param = "viewer=<%= person.getOid()%>&owner=<%= personOwner.getOid()%>";
                xmlhttp.open("POST","../servlet/FollowTutorServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             } // end function
             
             function addFacilitator() {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState==4 && xmlhttp.status==200 ) {
                        setTimeout("location.reload(true);", 7000);
                    }
                }

                var param = "viewer=<%= person.getOid()%>&owner=<%= personOwner.getOid()%>";
                xmlhttp.open("POST","../servlet/FollowFacilitatorServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             } // end function
             
             function addIntermediate() {
                window.location.href("intermediate.jsp");
             } // end function
             
             function deleteFriend(type) {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState==4 && xmlhttp.status==200 ) {
                        setTimeout("location.reload(true);", 7000);
                    }
                }

                var param = "viewer=<%= person.getOid()%>&owner=<%= personOwner.getOid()%>&type="+type;
                xmlhttp.open("POST","../servlet/DeleteFellowServlet", true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);
             } // end function          
        </script>
    </head>
    <body onload="checkRequest(); MM_preloadImages
            ('../photos/images/hover/menu-bar_01_01.jpg',
             '../photos/images/hover/menu-bar_01_02.jpg',
             '../photos/images/hover/menu-bar_01_03.jpg',
             '../photos/images/hover/menu-bar_01_04.jpg',
             '../photos/images/hover/menu-bar_01_05.jpg');">

        <%@include file="nav.jsp" %>
        <div id="div_main">        
            <div id="left">
                <%@ include file="profile/profile_info.jsp" %> 
                <div id="contacts">
                    <h3>Contacts:</h3>
                    <iframe scrolling="no" frameborder="no" id="remote_iframe_1" 
                            src="<%= util.getIFrameUrl(user.getViewer(), owner, 
                                "http://localhost:8080/samplecontainer/examples/randomRelations.xml") %>">
                    </iframe>
                    <a class="example7" href="<%= util.getIFrameUrl(user.getViewer(), owner, 
                            "http://localhost:8080/samplecontainer/examples/fetchAll.xml") %>">View All
                    </a><br /><br />
                </div>
            </div>
            <div id="content">
                <div id="contentheader">
                    <h1 style="display: inline;"><% out.println(util.getPersonDisplayName(owner));%></h1>
                    <%
                        if ( isViewerOwner ){
                    %>
                    <a href="edit.jsp?owner=<%= person.getPersonId()%>" >
                            <img src="../photos/edit.png" id="" width="20px" height="20px" 
                                                        title="Edit your profile"/></a>
                    <%    
                        }
                        if ( personOwner.getAcademicRole() < 4 ) { 
                            /* student must show only request fellow */
                            if ( ! isViewerOwner ) {
                                if ( ( ! areFellows ) && ( ! hasRequestedFellowship ) ){ // not fellow
                                    out.println("<button id=\"addFellow\" "
                                       + "onclick=\"sendRequest();\" class=\"green\">Add Fellow</button>");
                                }
                            }                
                        }
                        else if ( personOwner.getAcademicRole() < 6 ) {
                            /* tutor must show request fellow and follow */
                            boolean isTutor = util.isTutor( person.getOid(), personOwner.getOid()  );
                            if ( ! isViewerOwner ) {
                                if ( ! isTutor && person.getAcademicRole() < 4 ){ // not viewer's tutor
                                    out.println("<button id=\"addFellow\" "
                                            + "onclick=\"addTutor();\" class=\"green\">Follow</button>");
                                }
                                if ( ( ! areFellows ) && ( ! hasRequestedFellowship ) ){ // not fellow
                                out.println("<button id=\"addFellow\" "
                                        + "onclick=\"sendRequest();\" class=\"green\">Add Fellow</button>");
                                }
                            }
                        }
                        else if ( personOwner.getAcademicRole() < 10 ) {
                            /* staff must show request fellow and follow */
                            boolean isFacilitator = util.isFacilitator(person.getOid(), personOwner.getOid());
                            if ( ! isViewerOwner ) {
                                if ( ! isFacilitator && person.getAcademicRole() < 4){ // not following
                                    out.println("<button id=\"addFellow\" "
                                         + "onclick=\"addFacilitator();\" class=\"green\">Follow</button>");
                                }
                                if ( ( ! areFellows ) && ( ! hasRequestedFellowship ) ){ // not fellow
                                    out.println("<button id=\"addFellow\" "
                                         + "onclick=\"sendRequest();\" class=\"green\">Add Fellow</button>");
                                }
                            }
                        }
                        if( areFellows || util.isFacilitator( person.getOid(), personOwner.getOid() ) ){
                            if ( ! isViewerOwner ) {
                                out.println("<button id=\"addIntermediate\" "
                                    + "onclick=\"location.href='intermediate.jsp?owner="+ user.getViewer() 
                                    +"&interm="+owner+"'\" class=\"green\">Intermediate</button>");
                            }
                        }
                    %>
                    <img id="Ok" src="../photos/tick.png" width="50" height="50" 
                         style="display: inline; float:left; padding-top: 0; 
                                margin-top:0; margin-left: 5;" />
                </div>
                <%
                    String iframeUrl = util.getIFrameUrl(user.getViewer(), owner, 
                        "http://localhost:8080/samplecontainer/examples/fetchAll.xml" );
                %>
                <div id="posts">
                    <h2>Posts</h2>
                    <%@ include file="profile/profile_create_posts.jsp" %><br />
                    <%@ include file="profile/profile_get_posts.jsp" %>
                </div> 
            </div>
            <div id="right">
                <%@include file="profile/profile_apps.jsp" %>
            </div>
        </div>    
    </body>
</html>