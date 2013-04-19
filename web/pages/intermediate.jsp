<%-- 
    Document   : intermediate
    Created on : Jun 19, 2012, 1:26:53 AM
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
    String interm = request.getParameter("interm");
    Long ownerDbId = util.getPersonObjectId(owner);
    Long intermDbId = util.getPersonObjectId(interm);

    Integer ownerAcademicRole = util.getPersonAcademicRole(ownerDbId);
   
    if( ownerDbId == 0 || session == null ) {
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
         function sendIntermRequest(app, option) {
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
            var param = "viewer=<%= person.getOid()%>&interm=<%= intermDbId%>&app="+app;
            if (option == "install") {
                xmlhttp.open("POST","../servlet/InstallIntermAppServlet", true);
            } else {
                xmlhttp.open("POST","../servlet/RemoveIntermAppServlet", true);
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
                <p> Choose one App to authorize the intermediate to run it for you! 
                    On the right side are the Apps you have already authorized. Click on the 
                    "Add Icon" (<img src="../photos/add.png" alt="Add" width="15">) to authorize 
                    the intermediate for the selected App. To remove one from the App Drawer, 
                    just click on the respective "Remove Icon" 
                    (<img src="../photos/remove.png" alt="Remove" width="15">)
                </p>
                <p>When the intermediate runs the App for you you will be informed for the 
                    progress of the application he made for you!</p>
            </div>
            <div id="content"><br /><br /><br />
                <div id="drawer-title"><img alt="App Drawer" title="App Drawer" 
                     src="../photos/drawer.png">&nbsp;&nbsp; Your App Drawer
                </div><br />
                <%      
                    Application app = new Application();
                    ArrayList<HashMap<String, String>> applist = new ArrayList();
                    applist = app.getInstalledApps(owner);
                    ArrayList<String> intermApp = new ArrayList();
                    intermApp = app.getIntermediateApps(ownerDbId, intermDbId);
                    if (applist.size() > 0) {
                        for (int i = 0; i<applist.size(); i++) {
                            Set set = applist.get(i).keySet();
                            Iterator iter = set.iterator();
                            while (iter.hasNext()) {
                                Object o = iter.next();
                                String key = o.toString();
                                String value = applist.get(i).get(key); 
                                if (!intermApp.isEmpty()){
                                    if(intermApp.contains(key))
                                        break;     
                                }
                                else{
                                    // do nothing
                                }
                %>
                <br/><br/>
                <div id="app-text1">
                    <div id="title">
                        <span id="app-function">
                            <a href="#" onclick="sendIntermRequest('<%=value%>', 'install'); ">
                                <img title="Install App" alt="Install App" src="../photos/add.png"/></a>
                        </span>&nbsp;&nbsp;<%out.println(value);%><br/>
                    </div>
                </div>
                <%
                            }
                        }
                    }
                    if (applist.size() == 0){
                %>
                <br/><br/><br/>
                <center>
                    <div id="app-text2">
                        <div id="title">There are no Apps in your App Drawer.</div><br/>
                        You can always add some!
                    </div>
                </center>
                <%
                    }
                %>
            </div>         
            <div id="right" ><br /><br />
                <h2>Intermediated Apps</h2>
                
                <%
                    if (applist.size() > 0){
                        for (int i = 0; i<applist.size(); i++){
                            Set set = applist.get(i).keySet();
                            Iterator iter = set.iterator();
                            if (intermApp.isEmpty()){
                %>
                <br/><br/><br/>
                <center>
                    <div id="app-text4">
                        <div id="title">There are no Apps in Intermediate's App Drawer.</div><br/><br />
                        You can authorize him to run some Apps for you!
                    </div>
                </center>
                <%
                                break;
                            }
                            while (iter.hasNext()){
                                Object o = iter.next();
                                String key = o.toString();
                                String value = applist.get(i).get(key);
                                if (!intermApp.isEmpty()){
                                    if(!intermApp.contains(key))
                                        break;     
                                }  
                %>
                <br/><br/>
                <div id="app-text3">
                    <div id="title">
                        <span id="app-function">
                        <a href="#" onclick="sendIntermRequest('<%=value%>', 'remove');">
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
                        You have to install some and then authorize the intermediate!
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