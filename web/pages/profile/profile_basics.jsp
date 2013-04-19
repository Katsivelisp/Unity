<%-- 
    Document   : profile_basics
    Created on : Aug 1, 2011, 9:44:01 PM
    Author     : UNITY Developers
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="JavaConnector.*"%>
<%@page import="SocialObjects.*"%>
<%@page import="ServletClasses.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>

<jsp:useBean id="user" class="Beans.PersonBean" scope="session"/>
    
<%
    Utils util = new Utils();
    Post post = new Post();
    Person utilPerson = new Person();

    String owner = request.getParameter("owner");
    Long ownerDbId = util.getPersonObjectId(owner);
    Integer ownerAcademicRole = util.getPersonAcademicRole(ownerDbId);
    Long viewerDbId = util.getPersonObjectId(user.getViewer());
    
    if( ( user.getViewer() == null ) || ownerDbId < 0 || session == null ) {
        response.sendRedirect("../index.jsp");
    }
    
    PersonObject person; // viewer
    PersonObject personOwner; // owner
    
    StudentObject studentOwner = null;
    TeacherObject teacherOwner = null;
    StaffObject staffOwner = null;    
    
    /* get viewer */
    if( user.getStudent() != null ) {
        person = user.getStudent();
    }
    else if (user.getTeacher() != null ) {
        person = user.getTeacher();
    }
    else {
        person = user.getStaff();
    }

    /* get owner */
    if( ownerAcademicRole < 4 ) {
        personOwner = utilPerson.getStudent(ownerDbId);
        studentOwner = utilPerson.getStudent(ownerDbId);
    }
    else if ( ownerAcademicRole < 6 ) {
        personOwner = utilPerson.getTeacher(ownerDbId);
        teacherOwner = utilPerson.getTeacher(ownerDbId);
    }
    else {
        personOwner = utilPerson.getStaff(ownerDbId);
        staffOwner = utilPerson.getStaff(ownerDbId);
    }              
    
    boolean isViewerOwner =  ( viewerDbId ==  ownerDbId ) ? true : false;
    boolean isOwnerFellow = util.isFellow(viewerDbId, ownerDbId);
    boolean isOwnerTutor = util.isTutor(viewerDbId, ownerDbId);
    boolean isOwnerFacilitator = util.isFacilitator(viewerDbId, ownerDbId);
    
    //request.setAttribute("viewerId", person.getOid());
    //request.setAttribute("ownerId", ownerDbId);
    
    boolean hasRequestedFellowship = util.hasRequestedFellowship(viewerDbId, ownerDbId );          
    boolean areFellows = util.isFellow(viewerDbId, ownerDbId);
        
    String post_type = "fellow";    // fellow's wall
    if( isViewerOwner )
        post_type = "wall";         // own wall
%>