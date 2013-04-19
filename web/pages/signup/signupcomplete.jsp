<%-- 
    Document   : signupcomplete
    Created on : Jul 25, 2011, 3:17:29 PM
    Author     : UNITY Developers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>
<%@page import="JavaConnector.Person"%>
<%@page import="JavaConnector.Utils"%>

<jsp:useBean id="newuser" class="Beans.SignUpBean" scope="session"/>
<jsp:setProperty name="newuser" property="*"/>

<%
    if( ( newuser.getEmail() == null ) || session == null ) {
        response.sendRedirect("../../index.jsp");
    }
%>        
<%          
    Person person = new Person();
    Utils util = new Utils();

    //Calendar birthday = Calendar.getInstance();
    //birthday.set(newuser.getYear(), newuser.getMonth(), newuser.getDay());

    //birthday.set( Integer.parseInt( newuser.getYear() ), Integer.parseInt( 
    //                newuser.getMonth() ), Integer.parseInt( newuser.getDay() ) );

    out.println( "<br><br><b>Account Informations</b><br><br>" );

    out.println( "academic_role : " + newuser.getAcademic_role() + "<br>" );
    out.println( "student : " + newuser.getStudent() + "<br>" );
    out.println( "teacher : " + newuser.getTeacher() + "<br>" );
    out.println( "staff : " + newuser.getStaff() + "<br>" );
    out.println( "department : " + newuser.getDepartment() + "<br>" );
    out.println( "service : " + newuser.getService() + "<br>" );

    /* social information */
    out.println( "<br><br><b>Social Informations</b><br><br>" );

    out.println( "gender : " + newuser.getGender() + "<br>" );
   /* out.println( "birthday : " + birthday.DAY_OF_MONTH + "<br>" );*/
    out.println( "about : " + newuser.getAbout() + "<br>" );
    out.println( "status : " + newuser.getStatus() + "<br>" );
    out.println( "nickname : " + newuser.getNickname() + "<br>" );
    out.println( "happiest : " + newuser.getHappiest_when() + "<br>" );
    out.println( "scared of : " + newuser.getScared_of() + "<br>" );
    out.println( "job : " + newuser.getJob_interests() + "<br>" );
    out.println( "political : " + newuser.getPolitical_views() + "<br>" );
    out.println( "religion : " + newuser.getReligion() + "<br>" );
    out.println( "drinker : " + newuser.getDrinker() + "<br>" );
    out.println( "smoker : " + newuser.getSmoker() + "<br>" );
    out.println( "relationship status : " + newuser.getRelationship_status() + "<br>" );

    /* academic information */
    out.println( "<br><br><b>Academic Informations</b><br><br>" );

    System.out.println( "position2 : " + newuser.getPosition2() + "<br>" ); 
    System.out.println( "position1 : " + newuser.getPosition1() + "<br>" ); 
    System.out.println( "position : " + newuser.getPosition() + "<br>" ); 
    
    String person_id = util.transformEmailToId(newuser.getEmail());
    String result = person.createPerson(person_id, newuser.getName(), 
            newuser.getAcademic_role(), newuser.getStudent(), newuser.getTeacher(), 
            newuser.getStaff(), newuser.getDepartment(), newuser.getService(), 
            newuser.getGender(),  newuser.getJob_interests(), newuser.getAbout(), 
            newuser.getReligion(), newuser.getHappiest_when(), newuser.getScared_of(), 
            newuser.getPolitical_views(), newuser.getNickname(), newuser.getStatus(), 
            newuser.getDrinker(), newuser.getSmoker(), newuser.getRelationship_status() );  
     if ( result.equalsIgnoreCase("Done!") ){
        Long oid = util.getPersonObjectId(person_id);

        if ( newuser.getAcademic_role().equalsIgnoreCase("student")) {
             /* academic  information student */
            out.println( "student code : " + newuser.getStudent_code() + "<br>" );
            out.println( "admission : " + newuser.getAdmission_year() + "<br>" );
            out.println( "favourite course : " + newuser.getFav_course() + "<br>" );
            out.println( "hate course : " + newuser.getHate_course() + "<br>" );
            out.println( "graduated from : " + newuser.getGraduated_from() + "<br>" );

            result = person.addStudent(oid, Integer.parseInt( newuser.getStudent_code() ), 
                    Integer.parseInt( newuser.getAdmission_year()), newuser.getFav_course(), 
                    newuser.getHate_course(), newuser.getGraduated_from());
        }
        if ( newuser.getAcademic_role().equalsIgnoreCase("teacher")) {
            /* academic  information teacher */
            out.println( "office hours : " + newuser.getOffice_hours() + "<br>" );
            out.println( "office no: " + newuser.getOffice_no() + "<br>" );
            out.println( "courses : " + newuser.getCourse() + "<br>" );
            out.println( "research : " + newuser.getResearch() + "<br>" );
            if (newuser.getTeacher().equalsIgnoreCase("4")){
                out.println( "position : " + newuser.getPosition() + "<br>" );
            
                result = person.addTeacher(oid, newuser.getPosition(), newuser.getOffice_hours(),
                                       newuser.getOffice_no(), newuser.getResearch(), newuser.getCourse());
            }
            else if (newuser.getTeacher().equalsIgnoreCase("5")){
                out.println( "position : " + newuser.getPosition1() + "<br>" );
            
                result = person.addTeacher(oid, newuser.getPosition1(), newuser.getOffice_hours(),
                                       newuser.getOffice_no(), newuser.getResearch(), newuser.getCourse());
            }
        }
        if ( newuser.getAcademic_role().equalsIgnoreCase("staff")) {
             /* academic  information staff */
            out.println( "position : " + newuser.getPosition2() + "<br>" );    
            result = person.addStaff(oid, newuser.getPosition2() );
        }
        Thread.sleep(1000);
        response.sendRedirect("../../index.jsp");
    }         
%>
