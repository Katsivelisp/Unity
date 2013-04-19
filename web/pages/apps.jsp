<%-- 
    Document   : apps
    Created on : Aug 22, 2011, 6:13:13 PM
    Author     : Unity Developers
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Applications</title>
        <link rel=StyleSheet href="../css/apps_style.css" type="text/css" media="screen">
        <link rel=StyleSheet href="../css/nav.css" type="text/css" media="screen">
        <script type="text/javascript" src="../js/navBarFX.js"></script>
        <script type="text/javascript" >
         function sendRequest(app, option) {
            var xmlhttp;    
            if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
              xmlhttp = new XMLHttpRequest();
            } else {// code for IE6, IE5
              xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            
            xmlhttp.onreadystatechange = function() {
                if ( xmlhttp.readyState==4 && xmlhttp.status==200 ) {
                    top.location.reload(true);
                }    
            }
            
            var param = "viewer=<%= person.getOid()%>&app="+app;
            if (option == "install") {
                xmlhttp.open("POST","../servlet/InstallAppServlet", true);
            } else {
                xmlhttp.open("POST","../servlet/RemoveAppServlet", true);
            }             
            xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xmlhttp.send(param);          
         } // end function

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
            <div id="left"> <br /> <br />
                <center><img alt="Applications" src="../photos/app-home.png"></center><br />
                <div id="drawer-title">Getting started...</div>
                <p>Enhance your social experience: Unleash the power of Applications! Access 
                   all Academic Services with just one click!
                </p> <br/><br/>
                <center><img alt="Help" src="../photos/help.png"></center><br/>
                <div id="drawer-title">How to...</div>
                <p>Browse all available Applications and add them to your App Drawer! 
                    To add an App, click on the "Add Icon" (<img src="../photos/add.png" alt="Add" width="15">) 
                    and to remove one from the App Drawer, just click on the respective "Remove Icon" 
                    (<img src="../photos/remove.png" alt="Remove" width="15">)
                </p>
                <p>To access your Apps, just visit your profile and navigate through your Application Drawer!</p>
            </div>
            <div id="content">
                <h1>Applications</h1>
                <%      
                    Application app = new Application();
                    ArrayList<HashMap<String, String>> list = new ArrayList();
                    Long owner_oid = util.getPersonObjectId(owner);
                    list = app.getAvailableApps(owner_oid);
                    if (list.size() > 0l) {
                        for (int i = 0; i<list.size(); i++) {
                            Set set = list.get(i).keySet();
                            Iterator iter = set.iterator();
                            while (iter.hasNext()) {
                                Object o = iter.next();
                                String key = o.toString();
                                String value = list.get(i).get(key);                                                            
                %>
                <br/><br/>
                <div id="app-text1">
                    <div id="title">
                        <span id="app-function">
                            <a href="#" onclick="sendRequest('<%=key%>', 'install'); ">
                                <img title="Install App" alt="Install App" src="../photos/add.png"/></a>
                        </span>&nbsp;&nbsp;<%out.println(key);%><br/>
                        <div id="desc"><%out.println(value);%></div>
                    </div>
                </div>
                <%
                            }
                        }
                    }
                    if (list.size() == 0){
                %>
                <br/><br/><br/>
                <center>
                    <div id="app-text2">
                        <div id="title">There are no available Applications!<br /><br/>
                        You have installed them all!</div>
                    </div>
                </center>
                <%
                    }
                %>
            </div>         
            <div id="right" ><br /><br />
                <div id="drawer-title"><img alt="App Drawer" title="App Drawer" 
                     src="../photos/drawer.png">&nbsp;&nbsp; App Drawer
                </div><br />
                <%
                    ArrayList<HashMap<String, String>> applist = new ArrayList();
                    applist = app.getInstalledApps(owner);
                    if (applist.size() != 0){
                        for (int i = 0; i<applist.size(); i++){
                            Set set = applist.get(i).keySet();
                            Iterator iter = set.iterator();
                            while (iter.hasNext()){
                                Object o = iter.next();
                                String key = o.toString();
                                String value = applist.get(i).get(key);

                %>
                <br/><br/>
                <div id="app-text3">
                    <div id="title">
                        <span id="app-function">
                        <a href="#" onclick="sendRequest('<%=value%>', 'remove');">
                            <img title="Remove App" alt="Remove App" src="../photos/remove.png"/>
                        </a>
                        </span>&nbsp;&nbsp;<%out.println(value);%><br/>
                    </div>
                </div>
                <%
                            }
                        }
                    }
                    else{
                %>
                <br/><br/><br/>
                <center>
                    <div id="app-text4">
                        <div id="title">There are no Apps in your App Drawer.</div><br/>
                        You can always add some!
                    </div>
                </center>
                <%
                    }
                %>
            </div>
        </div><!-- End Content Column -->
        <!--<div id="footer">A Footer.</div>  -->        
    </body>
</html>