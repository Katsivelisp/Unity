<%-- 
    Document   : profile_info
    Created on : Aug 1, 2011, 10:09:28 PM
    Author     : UNITY Developers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="profile"> 
    <div id="profile_pic">
        <img id ="profileImg" src="<%=personOwner.getProfile_picture()%>" />
    </div>
    <div id="relantionship">
        <%          
            if ( isOwnerFellow ) {
                out.println( "<div id=\"relFellow\" ><h4>Fellow</h4><a href=\"#\">"
                     + "<img src=\"../photos/user_delete.png\" onclick=\"deleteFriend('fellow');\" "
                     + "title=\"Remove Fellow\" /></a></div>");
            }
            else if ( hasRequestedFellowship ) {
                out.println( "<div id=\"awaitingReply\" ><h4 style=\"color:grey;\">Awaiting reply</h4></div>");
            }            
            if ( isOwnerTutor ) {
                out.println( "<div id=\"relTutor\"><h4>Following Tutor</h4><a href=\"#\">"
                     + "<img src=\"../photos/user_delete.png\" onclick=\"deleteFriend('tutor');\" "
                     + "title=\"Unfollow Tutor\" /></a></div>");
            }
            if ( isOwnerFacilitator ) {
                out.println( "<div id=\"relFacilitator\"><h4>Following Facilitator</h4><a href=\"#\">"
                        + "<img src=\"../photos/user_delete.png\" onclick=\"deleteFriend('facilitator');\" "
                        + "title=\"Unfollow Facilitator\" /></a></div>");
            }
        %>              
    </div>
    <div id="personal_info" >
        <div id="personal_info_academic">
            <span class="info_title"><b>Academic</b></span>
            <hr class="info_hr" />
            <div class="information">              
                <b>Role : </b> <%=util.convertAcademicRole( personOwner.getAcademicRole() ) %><br/>
                <b>Department : </b><%=util.convertDepartment( personOwner.getDepartment() )%><br/>
                <%
                    String printThis = null;
                    if ( personOwner.getAcademicRole() == 1 ) { 
                        printThis = "<b>Admission Year : </b>"+studentOwner.getAdmissionYear()+"<br />";
                        printThis += "<b>Student UID : </b>"+studentOwner.getStudentCode()+"<br />";
                        printThis += "<b>Favourite Course : </b>"+studentOwner.getFavCourse()+"<br />";
                        printThis += "<b>Hate Course : </b>"+studentOwner.getHateCourse()+"<br />";                                                                        
                    }
                    else if ( personOwner.getAcademicRole() == 2 ||  personOwner.getAcademicRole() == 3 ) { 
                        printThis = "<b>Admission Year : </b>"+studentOwner.getAdmissionYear()+"<br />";
                        printThis += "<b>Student UID : </b>"+studentOwner.getStudentCode()+"<br />";
                        printThis += "<b>Favourite Course : </b>"+studentOwner.getFavCourse()+"<br />";
                        printThis += "<b>Hate Course : </b>"+studentOwner.getHateCourse()+"<br />";
                        printThis += "<b>Graduated : </b>"+studentOwner.getGraduatedFrom()+"<br />";

                    }
                    else if ( personOwner.getAcademicRole() == 4 ) {
                        printThis = "<b>Position : </b>"+teacherOwner.getPosition()+"<br />";
                        printThis += "<b>Office hours : </b>"+teacherOwner.getOfficeHours()+"<br />";
                        printThis += "<b>Office : </b>"+teacherOwner.getOfficeNo()+"<br />";
                    }
                    else if ( personOwner.getAcademicRole() == 5 ) { 
                        printThis += "<b>Office hours : </b>"+teacherOwner.getOfficeHours()+"<br />";
                        printThis += "<b>Office : </b>"+teacherOwner.getOfficeNo()+"<br />"; 
                    }
                    else if ( personOwner.getAcademicRole() > 5 ) { 
                        printThis = "<b>Position : </b>"+staffOwner.getPosition()+"<br />"; 
                    }
                    out.println(printThis);
                %>
            </div>
        </div>
        <div id="personal_info_social">
            <span class="info_title"><b>Social</b></span>
            <hr class="info_hr"/>
            <b>About me : </b><%=personOwner.getAboutMe()%><br/>
            <b>Gender : </b><%=personOwner.getGender() %><br/>
            <b>Relationship Status : </b><%=personOwner.getRelationshipStatus() %><br/>
        </div>
    </div>
</div>