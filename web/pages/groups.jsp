<%-- 
    Document   : groups
    Created on : Jul 20, 2011, 6:43:05 PM
    Author     : Unity Developers
--%>

<%@ page import="JavaConnector.*" %>
<%@ page import="SocialObjects.*" %>
<%@ page import="ServletClasses.*" %>

<jsp:useBean id="user" class="Beans.PersonBean" scope="session"/>

<%
    Utils util = new Utils();
    Post post = new Post();
    Group utilGroup = new Group();
    Person utilPerson = new Person();

    String owner = request.getParameter("owner");
    Long ownerDbId = util.getPersonObjectId( owner );
    Integer acad_role = util.getPersonAcademicRole(ownerDbId);

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
    
    boolean isViewerOwner = ( person.getOid() == ownerDbId ) ? true : false;   
    if( ( user.getViewer() == null ) || ownerDbId == 0 || session == null ) {
        response.sendRedirect("../index.jsp");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta name="gmapkey" content="">
	<meta name="description" content="">
	<meta name="keywords" content="">

        <title>Groups Page</title>
        <link rel="StyleSheet" href="../css/nav.css" type="text/css" media="screen">
        <link rel="StyleSheet" href="../css/global.css" type="text/css" media="screen">
        <link rel="StyleSheet" href="../css/groups_style.css" type="text/css" media="screen">   
        <link rel="stylesheet" href="../js/jqtransformplugin/jqtransform.css" type="text/css" media="screen">
        <script type="text/javascript" src="../js/navBarFX.js"></script>
	<script type="text/javascript" src="../js/requiered/jquery.js" ></script>
	<script type="text/javascript" src="../js/jqtransformplugin/jquery.jqtransform.js" ></script>
        <script type="text/javascript" >           
            function createGroup() {
                var xmlhttp;    
                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                }else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        getGroups("fun");
                        getGroups("class");
                        document.getElementById("groupName").value = "";
                        document.getElementById("groupDesc").value = "";
                    }
                }
                
                var name;
                var type;
                var desc = document.getElementById("groupDesc").value;
                
                if ( document.getElementsByName("groupType")[0].checked ) {
                    name = document.getElementById("groupName").value;
                    type = "fun";
                } else {
                    var classes = document.getElementById("selectClass");
                    name = classes.value;          
                    type = "class";          
                }
                if ( name == "" || name == undefined ) {
                    //do nothing
                } else {      
                    var param = "owner=<%= ownerDbId%>&name="+name+"&type="+type+"&desc="+desc;
                    xmlhttp.open("POST","../servlet/CreateGroupServlet", false);
                    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                    xmlhttp.send(param);
                }
             } // end function
            
            function getGroups(type) {
                var xmlhttp;    

                if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
                  xmlhttp = new XMLHttpRequest();
                } else {// code for IE6, IE5
                  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }

                xmlhttp.onreadystatechange=function() {
                    if ( xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                        if ( type == "fun")
                            document.getElementById("groupsFun").innerHTML = xmlhttp.responseText;
                        else
                            document.getElementById("groupsClass").innerHTML = xmlhttp.responseText;
                    }
                }
                var param = "owner=<%= owner%>&type="+type;
                xmlhttp.open("POST","../servlet/GetGroupsServlet", false);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send(param);

             } // end function
        </script>
	<script type="text/javascript">         
          $(function(){
            $('form').jqTransform({imgPath:'../js/jqtransformplugin/img/'});
            $("#classes").hide();
            $("#groupTypeFun").click(function() {
                $("#groupNameDiv").show("slow");
                $("#classes").hide("slow");
            });

            $("#groupTypeClass").click(function() {
                $("#classes").show("slow");
                $("#groupNameDiv").hide("slow");
            });
          });
	</script>
    </head>
    <body onload="getGroups('fun'); getGroups('class'); MM_preloadImages
        ('../photos/images/hover/menu-bar_01_01.gif',
         '../photos/images/hover/menu-bar_01_02.gif',
         '../photos/images/hover/menu-bar_01_03.gif',
         '../photos/images/hover/menu-bar_01_04.gif',
         '../photos/images/hover/menu-bar_01_05.gif')">

        <%@include file="nav.jsp" %>
        <div id="div_main">
            <div id="left">
                <h1>Create Group</h1>
                <form name="form_input" >
                    <p class="rowElem" id="groupNameDiv"><label>Name :</label><br/> <br/>
                        <input type="text" name="inputtext" id="groupName">
                    </p>
                    <p class="rowElem" id="classes" ><label>Class :</label>
			<select id="selectClass" >
                            <option value="" selected="selected"> - Choose -</option>
                            <option value="Simulation">Simulation</option>
                            <option value="Distributed Systems">Distributed Systems</option>
                            <option value="Artificial Intelligence">Artificial Intelligence</option>
                            <option value="Data Base Systems">Data Base Systems</option>
                            <option value="Compilers">Compilers</option>
                            <option value="Programming I">Programming I</option>
                            <option value="Programming II">Programming II</option>
                            <option value="Optical Communication">Optical Communication</option>			
			</select><br />
                    </p><br /><br />
                    <p class="rowElem"><label>Type :</label> 
			<input type="radio" id="groupTypeFun" name="groupType" value="fun" checked >
                        <label>Fun</label>
                        <% 
                            if ( acad_role == 4 || acad_role == 5 ) {
                        %>
                        <input type="radio" id="groupTypeClass" name="groupType" value="class" >
                            <label>Class</label>        
                        <%
                            }
                        %>
                    </p><br /><br />
                    <p class="rowElem"><label>Description :</label><br /><br />
                        <textarea id="groupDesc" cols="26" rows="8" name="mytext"></textarea>
                    </p>
                    <center><p class="rowElem"><a href="#" onclick="createGroup();">
                        <input type="button" value="Create"></a>
                    </p> </center>             
                </form>  
            </div>    
            <div id="content"> 
                <h1>Fun Groups</h1>
                <p id="groupsFun"></p>
            </div>              
            <div id="right">
                <h1>Class Groups</h1>
                <p id="groupsClass"></p>
            </div> 
        </div>          
        <!--<div id="footer">FOOTER</div>-->   
    </body>
</html>
