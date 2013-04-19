package JavaConnector;

import SocialObjects.PersonObject;
import SocialObjects.StaffObject;
import SocialObjects.StudentObject;
import SocialObjects.TeacherObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Unity Developers
 */
public class Person extends Basic {

    String sql;
    Statement stmt;
    Connection con;

    public Person() {
        try {
            // Connect to the database
            // attributes are initialized in Basic
            Class.forName(db_class_name);
            con = DriverManager.getConnection(db_url, db_user, db_password);
            stmt = con.createStatement();     
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
        }
    }
    
    /* TODO ftiaxe to age automata sti vasi */
    /* TODO insert birthday */
    public String createPerson(String person_id, String display_name, String acad_role, 
                               String student, String teacher, String staff, String department, 
                               String service, String gender, String job_interests, String about_me, 
                               String religion, String happiest_when, String scared_of, 
                               String political_views, String nickname, String status, 
                               String drinker, String smoker, String relationship_status) {
        
        /* convert null -> "" . from jsp null to mysql null("") */
        Utils util = new Utils();
        
        gender = util.convertNull(gender);
        job_interests = util.convertNull(job_interests);
        about_me = util.convertNull(about_me);
        religion = util.convertNull(religion);
        happiest_when = util.convertNull(happiest_when);
        scared_of = util.convertNull(scared_of);
        political_views = util.convertNull(political_views);
        nickname = util.convertNull(nickname);
        status = util.convertNull(status);
        smoker = util.convertNull(smoker);
        drinker = util.convertNull(drinker);
        relationship_status = util.convertNull(relationship_status);
        
        //java.sql.Date javaSqlBirthday = new java.sql.Date(birthday.getTime().getTime());
        
        /* create sql query */
        if ( acad_role.equalsIgnoreCase("student") )
            /* student */
            sql = "INSERT INTO person(person_id, display_name, acad_role, dept_id, gender, job_interests, "
                        + "about_me, religion, happiest_when, scared_of, political_views, nickname, status, "
                        + "drinker, smoker, relationship_status) "
                        + "VALUES('"+person_id+"', '"+display_name+"', "+Integer.parseInt( student )+", "
                        +Integer.parseInt(department)+", " + "'"+gender+"', '"+job_interests+"', '"+about_me+"', "
                        + "'"+religion+"', '"+happiest_when+"', '"+scared_of+"', '"+political_views+"', "
                        + "'"+nickname+"', '"+status+"', '"+drinker+"', '"+smoker+"', '"+relationship_status+"')";
        
        if ( acad_role.equalsIgnoreCase("teacher") )
            /* teacher*/
            sql = "INSERT INTO person(person_id, display_name, acad_role, dept_id, gender, job_interests, "
                        + "about_me, religion, happiest_when, scared_of, political_views, nickname, status, "
                        + "drinker, smoker, relationship_status) "
                        + "VALUES('"+person_id+"', '"+display_name+"', "+Integer.parseInt(teacher)+", "
                        + Integer.parseInt(department)+", " + "'"+gender+"', '"+job_interests+"', '"+about_me+"', "
                        + "'"+religion+"', '"+happiest_when+"', '"+scared_of+"', '"+political_views+"', "
                        + "'"+nickname+"', '"+status+"', '"+drinker+"', '"+smoker+"', '"+relationship_status+"')";
        
        if ( acad_role.equalsIgnoreCase("staff") )
            /* staff */
            sql = "INSERT INTO person(person_id, display_name, acad_role, dept_id, gender, job_interests, "
                        + "about_me, religion, happiest_when, scared_of, political_views, nickname, status, "
                        + "drinker, smoker, relationship_status) "
                        + "VALUES('"+person_id+"', '"+display_name+"', "+Integer.parseInt(staff)+", "
                        + Integer.parseInt(service)+", " + "'"+gender+"', '"+job_interests+"', '"+about_me+"', "
                        + "'"+religion+"', '"+happiest_when+"', '"+scared_of+"', '"+political_views+"', "
                        + "'"+nickname+"', '"+status+"', '"+drinker+"', '"+smoker+"', '"+relationship_status+"')";
        try {           
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    /* TODO gia ta integers vres ena tropo na kaneis convert null */
    public String addStudent(Long oid, Integer student_code, Integer admission_year, 
                        String fav_course, String hate_course, String graduated_from) {
        
        /* convert null -> "" . from jsp null to mysql null("") */
        Utils util = new Utils();
        fav_course = util.convertNull(fav_course);
        hate_course = util.convertNull(hate_course);
        graduated_from = util.convertNull(graduated_from);
        
        sql = "INSERT INTO student(oid, student_code, admission_year, fav_course, hate_course, graduated_from) "
                    + "VALUES( "+oid+", "+student_code+", "+admission_year+", '"+fav_course+"', '"
                    + hate_course+"', '"+graduated_from+"' )";    
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    /* TODO courses */
    public String addTeacher(Long oid, String position, String office_hours, String office_no,
                                                String research, String courses) {
        
        /* convert null -> "" . from jsp null to mysql null("") */
        Utils util = new Utils();
        position = util.convertNull(position);
        office_hours = util.convertNull(office_hours);
        office_no = util.convertNull(office_no);
        research = util.convertNull(research);
        courses = util.convertNull(courses);
        
        sql = "INSERT INTO teaching_staff(oid, position, office_hours, office_no, courses, research_focus) "
            + "VALUES( "+oid+", '"+position+"', '"+office_hours+"', '"+office_no+"', '"+courses+"', '"+research+"' )";
        
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    public String addStaff(Long oid, String position) {
        
        /* convert null -> "" . from jsp null to mysql null("") */
        Utils util = new Utils();
        position = util.convertNull(position);
        
        sql = "INSERT INTO administrative_staff(oid, position) VALUES( "+oid+", '"+position+"' )";
        
        try {           
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
      
    public String setFriendRequest(Long person_id, Long friend_id){      
        Utils util = new Utils();      
        sql = "INSERT INTO friend_request(person_id, friend_id) VALUES ("+person_id+", "+friend_id+" )";
        
        if ( util.isFellow(person_id, friend_id)) {
            return "already fellows";
        }     
        try {           
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }       
        return "Done!";
    }
    
    public ArrayList<PersonObject> getFriendRequest(Long person_id){     
        Utils util = new Utils();      
        sql = "SELECT person_id FROM friend_request WHERE friend_id = " + person_id + " AND accepted = 'pending'" ;
        
        ArrayList<PersonObject> persons = new ArrayList<PersonObject>();
        ResultSet rs;      
        try {          
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();
            Long oid;
            Integer role;
            while( rs.next() ) {        
                oid = rs.getLong("person_id");
                role =  util.getPersonAcademicRole(oid);

                if ( role < 4 ) {
                    persons.add(getStudent(oid) );
                }
                else if ( role < 6 ) {
                    persons.add(getTeacher(oid) );
                }
                else if ( role < 10 ) {
                    persons.add(getStaff(oid) );
                }
            }          
           /* if ( persons.size() == 0 ) {
                    return persons; // 0 persons              
            }*/          
        } catch( Exception e ) {
            System.out.println(e.getMessage());
            return null;
        }       
        return persons;
    }
    
    public String acceptRequest(Long person_id, Long friend_id) {    
        /* convert null -> "" . from jsp null to mysql null("") */
        Utils util = new Utils();
        
        sql = "UPDATE friend_request SET accepted = 'accepted' WHERE person_id = " + 
                person_id + " AND friend_id = " + friend_id;
        try {           
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    public String denyRequest(Long person_id, Long friend_id) {        
        Utils util = new Utils();               
        sql = "UPDATE friend_request SET accepted = 'denied' WHERE person_id = " + 
                person_id + " AND friend_id = " + friend_id;
        
        try {           
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }  
    
   /** create a connection between two persons
    *
    * personId ------ the person who requested the connection
    * friendId ------ the person from whom connection is requested
    * type     ------ the type of connection (supports FELLOW, TUTOR, FACILITATOR )
    *
    * This method is called when a person user wants to connect to another person user.
    * If the type of this connection is Fellow method is called only when the request has been accepted.
    * Otherwise connection is granted without request response.
    */
    public String addFriend(Long person_id, Long friend_id, String type){    
        if ( ! new Utils().isPerson(person_id))
            return "Sorry, but you are not a person!";
        if ( ! new Utils().isPerson(friend_id))
            return "Sorry, but the our fellow does not exist!";

        try {
            /* check friendship */
            if( new Utils().checkFriendshipRequest(person_id.toString(), friend_id.toString(), type) != 0 )
                return "Not a valid friendship";
            sql = "INSERT INTO friend (score, person_id, friend_id, ftype) "
                + "VALUES (0,"+person_id+","+friend_id+",'"+type+"')";

            //execute query
            stmt.executeUpdate(sql);
            if( type.equalsIgnoreCase("fellow")){
                /* write to db again because of bipolar (?) friendship !*/
                sql = "INSERT INTO friend (score, person_id, friend_id, ftype) "
                    + "VALUES (0,"+friend_id+","+person_id+", '"+type+"')";
                stmt.executeUpdate(sql);
            }
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    public String addIntermediate(Long person_id, Long friend_id, String type, Long appId){       
        if ( ! new Utils().isPerson(person_id))
            return "Sorry, but you are not a person!";
        if ( ! new Utils().isPerson(friend_id))
            return "Sorry, but the fellow does not exist!";

        try {
            sql = "INSERT INTO friend (score, person_id, friend_id, ftype, fAppId) "
                + "VALUES (0,"+person_id+","+friend_id+",'"+type+"',"+appId+")";
            //execute query
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    public String deleteIntermediate(Long person_id, Long friend_id, String type, Long appId){       
        if ( ! new Utils().isPerson(person_id))
            return "Sorry, but you are not a person!";
        if ( ! new Utils().isPerson(friend_id))
            return "Sorry, but the fellow does not exist!";

        try {
            sql = "DELETE FROM friend WHERE person_id = " +person_id+ " AND friend_id = " 
                +friend_id+ " AND ftype LIKE '%" +type+ "%' AND fAppId = " +appId; 
            //execute query
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    public String deleteAllIntermediates(Long person_id, String type, Long appId){       
        if ( ! new Utils().isPerson(person_id))
            return "Sorry, but you are not a person!";

        try {
            sql = "DELETE FROM friend WHERE person_id = " +person_id+ " AND ftype LIKE '%" 
                +type+ "%' AND fAppId = " +appId; 
            //execute query
            stmt.executeUpdate(sql);
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }

    public String deleteFriend(Long person_id, Long friend_id, String type){
        sql = "DELETE FROM friend WHERE person_id = "+person_id
                        + " AND friend_id = "+friend_id+" AND ftype ='"+type+"'";       
        if ( ! new Utils().isPerson(person_id))
            return "Sorry, but you are not a person!";
        if ( ! new Utils().isPerson(friend_id))
            return "Sorry, but the our fellow does not exist!";
        
        try {
            //execute query
            stmt.executeUpdate(sql);
            if( type.equalsIgnoreCase("fellow")){
                sql = "DELETE FROM friend WHERE person_id = "+friend_id
                    + " AND friend_id = "+person_id+" AND ftype = '"+type+"'";
                //execute query
                stmt.executeUpdate(sql);
            }
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            return "Error ocurred";
        }
        return "Done!";
    }
    
    public StudentObject getStudent(Long oid) {
        StudentObject student = new StudentObject();
            
        sql = "SELECT * FROM person WHERE oid = " + oid;
        String sqlMore = "SELECT * FROM student WHERE oid = " + oid;
        ResultSet rs;

        /* check if select query returns anything */
        try {
            stmt = con.createStatement();
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();

            /* get person attribute */
            if( rs.first() ) {
                   student.setOid(oid);
                   student.setPersonId(rs.getString("person_id"));
                   student.setAboutMe(rs.getString("about_me"));
                   student.setAcademicRole(rs.getInt("acad_role"));
                   student.setAge(rs.getInt("age"));
                   student.setBirthday(rs.getDate("birthday"));
                   student.setDepartment(rs.getInt("dept_id"));
                   student.setDisplayName(rs.getString("display_name"));
                   student.setDrinker(rs.getString("drinker"));
                   student.setGender(rs.getString("gender"));
                   student.setHappiestWhen(rs.getString("happiest_when"));
                   student.setJobInterests(rs.getString("job_interests"));
                   student.setNameId(rs.getInt("name_id"));
                   student.setNickname(rs.getString("nickname"));
                   student.setProfile_picture(rs.getString("profile_picture"));
                   student.setPoliticalViews(rs.getString("political_views"));
                   student.setRelationshipStatus(rs.getString("relationship_status"));
                   student.setReligion(rs.getString("religion"));
                   student.setScaredOf(rs.getString("scared_of"));
                   student.setSmoker(rs.getString("smoker"));
                   student.setStatus(rs.getString("status"));
            }   
            stmt.executeQuery(sqlMore);
            rs =  stmt.getResultSet();
            
            /* get student attribute */
            if( rs.first() ) {
                   student.setAdmissionYear( rs.getInt("admission_year") );
                   student.setFavCourse(rs.getString("fav_course"));
                   student.setGraduatedFrom(rs.getString("graduated_from"));
                   student.setHateCourse(rs.getString("hate_course"));
                   student.setStudentCode( rs.getString("student_code"));
            }
            return student;
        }
        catch( Exception e ) {
                System.out.println(e.getMessage());
        }      
        return null;
    }
    
    public TeacherObject getTeacher(Long oid) {
        TeacherObject teacher = new TeacherObject();
            
        sql = "SELECT * FROM person WHERE oid = " + oid;
        String sqlMore = "SELECT * FROM teaching_staff WHERE oid = " + oid; 
        ResultSet rs;

        /* check if select query returns anything */
        try {
            stmt = con.createStatement();
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();

            /* get person attribute */
            if( rs.first() ) {
                   teacher.setOid(oid);
                   teacher.setPersonId(rs.getString("person_id"));
                   teacher.setAboutMe(rs.getString("about_me"));
                   teacher.setAcademicRole(rs.getInt("acad_role"));
                   teacher.setAge(rs.getInt("age"));
                   teacher.setBirthday(rs.getDate("birthday"));
                   teacher.setDepartment(rs.getInt("dept_id"));
                   teacher.setDisplayName(rs.getString("display_name"));
                   teacher.setDrinker(rs.getString("drinker"));
                   teacher.setGender(rs.getString("gender"));
                   teacher.setHappiestWhen(rs.getString("happiest_when"));
                   teacher.setJobInterests(rs.getString("job_interests"));
                   teacher.setNameId(rs.getInt("name_id"));
                   teacher.setNickname(rs.getString("nickname"));
                   teacher.setPoliticalViews(rs.getString("political_views"));
                   teacher.setProfile_picture(rs.getString("profile_picture"));
                   teacher.setRelationshipStatus(rs.getString("relationship_status"));
                   teacher.setReligion(rs.getString("religion"));
                   teacher.setScaredOf(rs.getString("scared_of"));
                   teacher.setSmoker(rs.getString("smoker"));
                   teacher.setStatus(rs.getString("status"));          
            }
            stmt = con.createStatement();
            stmt.executeQuery(sqlMore);
            rs =  stmt.getResultSet();
            
            /* get teacher attribute */
            if( rs.first() ) {
                   teacher.setCourses(rs.getString("courses"));
                   teacher.setOfficeHours(rs.getString("office_hours"));
                   teacher.setOfficeNo(rs.getString("office_no"));
                   teacher.setPosition( rs.getString("position"));
                   teacher.setResearchFocus( rs.getString("research_focus") );
            }
            return teacher;
        }
        catch( Exception e ) {
                System.out.println(e.getMessage());
        }       
        return null;
    }
    
    public StaffObject getStaff(Long oid) {      
        StaffObject staff = new StaffObject();
            
        sql = "SELECT * FROM person WHERE oid = " + oid;
        String sqlMore = "SELECT * FROM administrative_staff WHERE oid = " + oid;      
        ResultSet rs;

        /* check if select query returns anything */
        try {
            stmt = con.createStatement();
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();

            /* get person attribute */
            if( rs.first() ) {
                   staff.setOid(oid);
                   staff.setPersonId(rs.getString("person_id"));
                   staff.setAboutMe(rs.getString("about_me"));
                   staff.setAcademicRole(rs.getInt("acad_role"));
                   staff.setAge(rs.getInt("age"));
                   staff.setBirthday(rs.getDate("birthday"));
                   staff.setDepartment(rs.getInt("dept_id"));
                   staff.setDisplayName(rs.getString("display_name"));
                   staff.setDrinker(rs.getString("drinker"));
                   staff.setGender(rs.getString("gender"));
                   staff.setHappiestWhen(rs.getString("happiest_when"));
                   staff.setJobInterests(rs.getString("job_interests"));
                   staff.setNameId(rs.getInt("name_id"));
                   staff.setNickname(rs.getString("nickname"));
                   staff.setPoliticalViews(rs.getString("political_views"));
                   staff.setProfile_picture(rs.getString("profile_picture"));
                   staff.setRelationshipStatus(rs.getString("relationship_status"));
                   staff.setReligion(rs.getString("religion"));
                   staff.setScaredOf(rs.getString("scared_of"));
                   staff.setSmoker(rs.getString("smoker"));
                   staff.setStatus(rs.getString("status"));                  
            }           
            stmt.executeQuery(sqlMore);
            rs =  stmt.getResultSet();
            
            /* get staff attribute */
            if( rs.first() ) {
                   staff.setPosition( rs.getString("position"));
            }            
            return staff;
        }
        catch( Exception e ) {
                System.out.println(e.getMessage());
        }       
        return null;
    }
}//end of class


