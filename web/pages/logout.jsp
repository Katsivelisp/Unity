<%-- 
    Document   : logout
    Created on : Jul 20, 2011, 7:18:33 PM
    Author     : UNITY Developers
--%>

<jsp:useBean id="user" class="Beans.PersonBean" scope="session"/>

<%   
    if( ( user.getViewer() == null ) || session == null ) {
        response.sendRedirect("../index.jsp");
    }
    
    session.removeAttribute("user");
    session.invalidate();
    
    response.sendRedirect("../index.jsp");
%>