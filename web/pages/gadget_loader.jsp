<%-- 
    Document   : gadget_loader
    Created on : Aug 25, 2011, 3:14:06 PM
    Author     : valentino
--%>

<%@page import="JavaConnector.Application"%>
<%@page import="JavaConnector.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gadget Loader</title>
    </head>
    <body>    
        <%    
            Application app = new Application();
            Utils util = new Utils();
            String url = util.getIFrameUrl(request.getParameter("owner"), 
                    request.getParameter("owner"), app.getApplicationUrl(request.getParameter("apps")));
            response.sendRedirect(url);
        %>
    </body>
</html>
