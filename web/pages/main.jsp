<%-- 
    Document   : main
    Created on : Jul 19, 2011, 1:57:46 PM
    Author     : UNITY Developers
--%>

<%@page import="JavaConnector.*"%>
<%@page import="java.sql.*" %>
<%@page import="SocialObjects.*"%>

<jsp:useBean id="user" class="Beans.PersonBean" scope="session"/>

<%
    Integer result;
    Utils util = new Utils(); 
    Person utilPerson = new Person();

    String owner = request.getParameter("owner");
    Long ownerDbId = util.getPersonObjectId(owner);
    Long viewerDbId = util.getPersonObjectId(user.getViewer());
    Integer ownerAcademicRole = util.getPersonAcademicRole(ownerDbId);

    boolean isViewerOwner = ( viewerDbId == ownerDbId ) ? true : false;   
    if( ( ! isViewerOwner ) || ( user.getViewer() == null ) || ownerDbId == 0 || session == null ) {
        response.sendRedirect("../index.jsp");
    }
    
    StudentObject student = null;
    TeacherObject teacher = null;
    StaffObject staff  = null;
    PersonObject person = null;
    
    /* get propriate social object */
    switch(ownerAcademicRole){
        case 1:
        case 2:
        case 3:
            student = utilPerson.getStudent(ownerDbId);
            person = utilPerson.getStudent(ownerDbId);

            if ( student == null )
                response.sendRedirect("../index.jsp");     
            user.setStudent(student);
            break;
        case 4:
        case 5:
            teacher = utilPerson.getTeacher(ownerDbId);
            person = utilPerson.getTeacher(ownerDbId);

            if ( teacher == null )
                response.sendRedirect("../index.jsp");        
            user.setTeacher(teacher);
            break;
        case 6:  
        case 7:
        case 8:
        case 9:
            staff = utilPerson.getStaff(ownerDbId);
            person = utilPerson.getStaff(ownerDbId);

            if ( staff == null )
                response.sendRedirect("../index.jsp");
            user.setStaff(staff);                                             
            break;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
        <link rel=StyleSheet href="../css/main_style.css" type="text/css" media="screen">
        <link rel=StyleSheet href="../css/nav.css" type="text/css" media="screen">
        <script type="text/javascript" src="../js/navBarFX.js"></script>
        <script type="text/javascript">
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
        </script>
    </head>
    <body onload="checkRequest(); MM_preloadImages
           ('../photos/images/hover/menu-bar_01_01.gif',
            '../photos/images/hover/menu-bar_01_02.gif',
            '../photos/images/hover/menu-bar_01_03.gif',
            '../photos/images/hover/menu-bar_01_04.gif',
            '../photos/images/hover/menu-bar_01_05.gif')">
        
        <%@include file="nav.jsp" %>   
        <div id="div_main">
            <div id="left"> </div>  
            <div id="content">
                <h2>Activity Stream</h2>
                <div id="content_iframe">
                    <% 
                        String iframeUrl = util.getIFrameUrl(user.getViewer(), owner, 
                           "http://localhost:8080/samplecontainer/examples/Wall1.xml" );
                    %>
                    <iframe id="remote_iframe_0" src="<%=iframeUrl%>" height="90%"
                             class="gadgets-gadget" frameborder="no" >
                    </iframe> 
                </div>
            </div>
            <div id="right" >
                <h1>Welcome to Unity!</h1>
                The first academic social network, fully designed for the needs of the 
                Academic Community of Harokopio University of Athens. Feel free to 
                socialize with your fellow students, colleagues, teachers and facilitators. 
                Don't hesitate to interact with them through social and academic 
                Applications that bring your Academic experience to a whole new level: 
                Strength through Unity, unity through Socializing.
                <h1>Harokopio University of Athens</h1>
                Unity has been fully integrated to the services provided by HUA 
                (Harokopio University of Athens) since September, 2011. All rights reserved.
                <br /> <br />       
                <a href="http://www.hua.gr">Harokopio University of Athens - Official Website</a>
                <br/><br/>
                <a href="http://forum.hua.gr"> Harokopio University of Athens - Official Forum</a>
                <br/><br/><br/>
                <center> <img src="../photos/hua2.gif" alt="HUA Logo" width="100px"> </center><br/>
            </div>         
        </div>                 
        <!-- End Content Column -->
        <!--<div id="footer">A Footer.</div> -->       
    </body>
</html>