package JavaConnector;

import SocialObjects.PostObject;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Unity Developers
 */
public class Post extends Basic {

    String sql;
    Statement stmt;
    Connection con;
    Utils util;

    public Post() {   
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

    public String createPost(Long oid, String message, String type, Long post_to, String post_target){      
        sql = "INSERT INTO post(person_id, message, posted_type, posted_to, posted_target) "
            + "VALUES ("+oid+", '"+message+"', '"+type+"', "+post_to+", '"+post_target+"' )";
        util = new Utils();
        
        if( ! util.isPerson(oid) )
            return "Sorry, but you are not a person!";
        if ( ! util.isPerson(post_to) )
            if ( ! util.isGroup(post_to))
                    return "The wall dows not exist";
        try {
            stmt.executeUpdate(sql);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
     
    /* wall_id is posted_to */
    public ArrayList<PostObject> getWallPosts(Long wall_id, String post_type) {
        ArrayList<PostObject> posts = new ArrayList<PostObject>();
        if ( post_type.equalsIgnoreCase("wall"))
            sql = "SELECT * FROM post WHERE posted_to = " + wall_id 
                + " AND  ( posted_type = 'wall' OR posted_type = 'fellow' )";
        else
            sql = "SELECT * FROM post WHERE posted_to = " + wall_id + " AND posted_type = 'group'";
        ResultSet rs;

        try {
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();          
            // get posts for a specifiec wall and insert them to list
            while ( rs.next() ) {

                Long oid = rs.getLong("oid");
                Long person_id = rs.getLong("person_id");
                Long posted_to = rs.getLong("posted_to");
                String message = rs.getString("message");
                String posted_type = rs.getString("posted_type");
                String posted_target = rs.getString("posted_target");
                Timestamp posted_time = rs.getTimestamp("posted_time");

                PostObject post = new PostObject( oid, person_id, posted_to, message, 
                                            posted_type, posted_target, posted_time );

                posts.add(post);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return posts;
    }

    public String deletePost(Long post_id) {
        sql = "DELETE FROM post WHERE oid = " + post_id;

        try {
            stmt.executeUpdate(sql);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }

    public String checkPost( Long oid, String message, String type, Long post_to, String post_target ) {
        util = new Utils();
        
        if ( ! util.isPerson(oid) ) {
            return "the person that wishes to post does not exist mf";
        }

        if ( type.equalsIgnoreCase("wall")) {
            if ( post_target == null || post_target.equalsIgnoreCase("") ) {
                return "re-enter post target";
            }

            if ( oid != post_to ) {
                return "an error occurred";
            }
        }
        else if ( type.equalsIgnoreCase("group")) {
            if ( ! util.isGroup(post_to) ) {
                return "there is no such group";
            }

            /* if person not member of the group he/she cannot post to it's wall */
            if ( ! util.isMemberOfGroup(post_to, oid) ) {
                return "only members of the group can post to group's wall";
            }
        }
        else if ( type.equalsIgnoreCase("fellow")) {
            if ( ! util.isFellow(oid, post_to)) {
                return "you can only post on a fellow's wall";
            }
            
            if ( post_target == null || post_target.equalsIgnoreCase("") ) {
                return "re-enter post target";
            }
        }
        else {
            return "re-enter correct post type";
        }  
        return "ok";
    }
}
