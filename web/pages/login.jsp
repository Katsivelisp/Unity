<%-- 
    Document   : login
    Created on : Jul 19, 2011, 1:15:07 PM
    Author     : UNITY Developers
--%>


<jsp:useBean id="user" class="Beans.PersonBean" scope="session"/>
<jsp:setProperty name="user" property="*"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="JavaConnector.Utils"%>

<html>
    <head>
        <script type="text/javascript">
            function delayer(){
                window.location = "../index.jsp";
            }
        </script>
    </head>
    <body>

    <%
        Utils util = new Utils();
        String person_id = util.transformEmailToId(user.getViewer());
        user.setViewer(person_id);

        /*if ( ! util.LDAPauth(user.getViewer()+"@hua.gr", user.getPassword()) )
            out.println("<b>You piece of shit do not belong to hua!</b>");
        else if ( ! util.isPerson(person_id) )
            out.println("<b>No account found! Go on, sign up..</b><br><br>You will be redirected...");

        else {*/
            response.sendRedirect("main.jsp?owner=" + person_id);
        /*}*/ 
    %>   
    </body>
</html>