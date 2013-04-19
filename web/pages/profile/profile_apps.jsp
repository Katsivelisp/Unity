<%-- 
    Document   : profile_apps
    Created on : Aug 9, 2011, 2:37:04 PM
    Author     : UNITY Developers
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="JavaConnector.*"%>
<%@page import="SocialObjects.*"%>
<%@page import="ServletClasses.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>

<script type="text/javascript">
    function goBack() {
        top.frames['remote_iframe_0'].location.href = 
                    'gadget_home.jsp?owner=<%=owner%>&viewer=<%=user.getViewer()%>';
        parent.document.getElementById("gadget-title").innerHTML = 
            '<img alt="App Drawer" title="App Drawer" src="../photos/drawer.png">&nbsp&nbspApp Drawer';
    }
    
    function Refresh() {
        top.frames['remote_iframe_0'].location.reload(true);
    }
    
    function collapse() {
        e=document.getElementById("gadget-slot");
        if (document.getElementById("col").getAttribute("src")=="../photos/collapse.png") {
            e.style.height = 30 + 'px';
            e.style.overflow='hidden';
            document.getElementById("col").setAttribute("src", "../photos/expand.png");
        }
        else {
            e.style.overflow='auto';
            e.style.height = 400 + 'px';
            document.getElementById("col").setAttribute("src", "../photos/collapse.png");
        }
    }
</script>

<div id="gadget-slot">
    <div id="gadget-properties">
        <div id="gadget-title">
            <img alt="App Drawer" id="drawer" title="App Drawer" 
                            src="../photos/drawer.png"/>&nbsp;&nbsp;App Drawer
        </div>
        <div id="gadget-options">
        <a href="#" onclick="collapse()">
            <img id="col" src="../photos/collapse.png" alt="Toggle" title="Toggle" /></a>&nbsp;&nbsp;
        <a href="#" onclick="Refresh()">
            <img src="../photos/gadget_refresh.png" alt="Refresh" title="Refresh" /></a>&nbsp;&nbsp;
        <a href="#" onclick="goBack()">
            <img src="../photos/gadget_home.png" alt="Gadget Home" title="Gadget Home"/></a>
        </div>
    </div>
    <%
        Application App = new Application();
        Utils util1 = new Utils();
        String slotUrl="";
        if (!App.getApplication(""+util1.getPersonObjectId(owner)).equals("No Apps")) {
            slotUrl = "gadget_home.jsp?owner="+owner+"&viewer="+user.getViewer();
    %>
    <iframe id="remote_iframe_0" scrolling="yes" frameborder="no" src="<%= slotUrl%>"></iframe>
    <%
        }
        else {
            if (owner.equals(user.getViewer())) {
    %>
    <br/><br/><br/>
    <div id="gadget-dialogue">
        <h2>You have no Apps in your App Drawer!</h2>
        <p>Visit the Apps page for more information.</p>
    </div>
    <%
            }
            else { 
    %>
    <br/><br/><br/>
    <div id="gadget-dialogue">
        <br /><br /><h2>There are no installed Gadgets!</h2>
    </div>
    <%
            }
        }
    %>

</div> <br/>