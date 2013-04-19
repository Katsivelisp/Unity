<%-- 
    Document   : gadget_home
    Created on : Aug 10, 2011, 2:56:36 PM
    Author     : UNITY Developers
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="JavaConnector.Application"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gadget Home</title>
    </head>
    <body>      
        <script type="text/javascript">
            function gadgetName() {
                parent.document.getElementById("gadget-title").innerHTML = 
                                            apps.options[apps.selectedIndex].text;
            }
        </script>
        <%
            String owner=request.getParameter("owner");
            String viewer=request.getParameter("viewer");
            if (owner.equals(viewer)) {
        %>
        <br/><br/><br/>
        <center><font color="#564b47" size="3px">Browse and load your installed Applications:
                </font><br/><br/>
            <form id="form1" action="gadget_loader.jsp?owner=<%=owner%>&viewer=<%=viewer%>" method="POST">
                <select id="apps" name="apps">
                <%
                    Application app = new Application();
                    ArrayList<HashMap<String, String>> list = new ArrayList();
                    list = app.getInstalledApps(owner);
                    if (list != null) {
                       for (int i = 0; i<list.size(); i++) {
                            Set set = list.get(i).keySet();
                            Iterator iter = set.iterator();
                            while (iter.hasNext()) {
                                Object o = iter.next();
                                String key = o.toString();
                                String value = list.get(i).get(key);                                    
                %>
                <option value="<%out.println(key);%>">
                <%
                    out.println(value);
                %>
                <%
                            }
                       }
                    }           
                %>
                </select><br/><br/>
                <input type="submit" value="Load Application" onclick="gadgetName()"/>
            </form><br/>
            <div  id="gadget-promo"><img src="../photos/process.png"/></div>
        </center>
        <%
            }
            else {
                out.println("<br/><br/><br /><br /><center>You are just a viewer and you cannot <br /><br /> "
                                     + "see the installed App of this user!</center>");
            }
        %>    
    </body>
</html>
