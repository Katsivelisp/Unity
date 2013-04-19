package JavaConnector;

import Beans.PersonBean;
import java.sql.*;
import java.util.Date;

/*
 *
 * @author Unity Developers
 */

/* TODO prepei opwsdipote na ftiaxw ena connection pool wste na ginetai kalutera
 * to create activity tou ka8e gegonotos */

public class Activity extends Basic {
    String sql;
    Statement stmt;
    Connection con;
    
    public Activity(){
        try {
            // Connect to the database
            // attributes are initialized in Basic
            Class.forName(db_class_name);
            con = DriverManager.getConnection(db_url, db_user, db_password);

            stmt = con.createStatement();
            
        }
        catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public String createActivity(String user_id, String title, String object, String target) {      
        String body =  composeBody(user_id, title, object, target);
        Date date = new Date();
        
        if ( target.contains("group") ) {
            target = target + object; // example group20, group37, group65451
        }

        sql = "INSERT INTO activity(user_id, title, object, body, target, posted_time) "
            + "VALUES( '"+user_id+"', '"+title+"', '"+object+"', '"+body+"', '"+target+"', "+ date.getTime() + ")";

        try {
            stmt.executeUpdate(sql);
        }

        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }       
        return "Done!";
    }
    
    public boolean createNewActivity(String user_id, String app, String title, String object, String target) {      
        String body =  composeBody(user_id, title, object, target);
        Date date = new Date();
        
        if ( target.contains("group") ) {
            target = target + object; // example group20, group37, group65451
        }

        sql = "INSERT INTO activity(user_id, app_id, title, object, body, target, posted_time) "
            + "VALUES( '"+user_id+"', '"+app+"', '"+title+"', '"+object+"', '"+body+"', '"
            +target+"', "+ date.getTime() + ")";

        try {
            stmt.executeUpdate(sql);
        }

        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }       
        return true;
    }
    
    public boolean deleteActivity(String user_id, String app, String object) {      
        sql = "DELETE FROM activity WHERE app_id LIKE '%"+app+ "%' AND user_id LIKE '%"
            +user_id+"%' AND object LIKE '%"+object+"%'";
        try {
            stmt.executeUpdate(sql);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }       
        return true;
    }
    
    public boolean deleteAppActivities(String user_id, String app) {      
        sql = "DELETE FROM activity WHERE app_id LIKE '"+app+ "%' AND user_id LIKE '%"+user_id+"%'";
        try {
            stmt.executeUpdate(sql);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }       
        return true;
    }
    
    private String composeBody(String user_id, String title, String object, String target){
        
        Utils util = new Utils();
        
        String body = "";
        String name = "";
        String obj = "";
        
        if ( title.equalsIgnoreCase("follow") ){
            name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
            obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+object+"\" >" + util.getPersonDisplayName( object ) +"</a>";
            body = name + " is now following " + obj;
        }
        else if ( title.equalsIgnoreCase("comment") ) {
            
        }
        else if ( title.equalsIgnoreCase("create") ) {
            
            name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
            obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/group.jsp?goid="+object+"\" >" + util.getGroupName(Long.parseLong(object) ) + " </a>";
            body = name + " created the group " + obj;
        }
        else if ( title.equalsIgnoreCase("post") ) {
            if ( ! target.equalsIgnoreCase("group")) { // person wall posts
                name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >" 
                    + util.getPersonDisplayName( user_id ) + " </a>";
                if ( user_id.equalsIgnoreCase(object)){
                    body = name + " posted a new <a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\">" + "message" + "</a>";
                }
                else {
                    obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+object+"\" >" 
                        + util.getPersonDisplayName( object ) + " ''s</a>";
                    body = name + " posted to " + obj + "profile";
                }
            }
            else { // group wall posts
                name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
                obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/group.jsp?goid="+object+"\" >" + util.getGroupName(Long.parseLong(object) ) + " </a>";
                body = name + " posted to the group " + obj;
            }
            
        }
        else if ( title.equalsIgnoreCase("join") ) {
            name = "<a target=\"_parent\" href=\"profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
            obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/group.jsp?goid="+object+"\" >" + util.getGroupName(Long.parseLong(object) ) + " </a>";
            body = name + " joined the " + obj + " group";
        }
        else if ( title.equalsIgnoreCase("delete") ) {
            name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
            obj = util.getGroupName(Long.parseLong(object) );
            body = name + " deleted <b>" + obj + " group</b>";

        }
        else if ( title.equalsIgnoreCase("remove") ) {  
            name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
            obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/group.jsp?goid="+object+"\" >" + util.getGroupName(Long.parseLong(object) ) + " </a>";
            body = name + " has been removed from" + obj + " group"; 
        }
        else if ( title.equalsIgnoreCase("fellow") ) {
            name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
            obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+object+"\" >" + util.getPersonDisplayName( object ) + "</a>";
            body = name +" and "+ obj +" are now fellows" ;
        }
        else if ( title.equalsIgnoreCase("add") ) {
            
        }
        else if ( title.equalsIgnoreCase("intermediate") ) {
            name = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+user_id+"\" >"+util.getPersonDisplayName(user_id)+"</a>";
            obj = "<a target=\"_parent\" href=\"http://localhost:8084/Web/pages/profile.jsp?owner="+object+"\" >" + util.getPersonDisplayName( object ) + "</a>";
            body = name +" made intermediate "+ obj +" for an App" ;
        }
        
        return body;
    } 
    
}
