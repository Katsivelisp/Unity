package JavaConnector;

import SocialObjects.GroupObject;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Unity Developers
 */
public class Group extends Basic {

    String sql;
    Utils util = new Utils();
    Statement stmt;
    Connection con;

    public Group() {
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

    public String createGroup(String id, Long person_id, String type, String description){        
        sql = "INSERT INTO person_group(id, person_id, type, description) "
                + "VALUES('"+id+"', "+person_id+", '"+type+"', '"+description+"')";
        
        // check if group type is class the person is a teacher
        if ( type.equalsIgnoreCase("class")) {
            if ( ! util.isTeacher(person_id ) )
                  return "Only teachers can create class groups!";
        }
        try {       
            stmt.executeUpdate(sql);
        }
        catch(Exception e){
           System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }
    
    public String deleteGroup(Long group_id, Long person_id){       
        sql = "UPDATE person_group SET status = 'deleted' WHERE oid = " 
                                    + group_id + " AND person_id = " + person_id;
        /* check if group exists */
        if ( ! util.isGroup(group_id) )
            return "The group you selected does not exist!";
        /* check if person is owner of the group */
        if ( ! util.isOwnerOfGroup(group_id, person_id)){
            return "Only the owner have the authority to delete this group!";
        }
        
        try {
            stmt.executeUpdate(sql);
        }
        catch(Exception e){
           System.out.println(e.getMessage());
            return "Error occurred";
        }
        return "Done!";
    }

    public String addMembertoGroup(Long group_id, Long person_id) {
        
        sql = "INSERT INTO membership(group_id, person_id) VALUES ("+group_id+", "+person_id+")";

        if( ! util.isPerson(person_id) )
            return "Sorry, but you are not a person!";

         /* check if group exists */
        if ( ! util.isGroup(group_id) )
            return "The group you selected does not exist!";

        try {
            stmt.executeUpdate(sql);            

        }
        catch(Exception e){
           System.out.println(e.getMessage());
            e.printStackTrace();
            return "Error occurred";
        }

        return "Done!";
    }
 
    public GroupObject getGroup(Long goid){
        sql = "SELECT * FROM person_group WHERE oid = " + goid + " AND status = 'active'";

        GroupObject group = new GroupObject(); 
        ResultSet rs;   
        try {         
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();
            
            // get members and insert them to list
            if ( rs.next() ) {
                //groupAndMembers;
                Long oid = rs.getLong("oid");
                String id = rs.getString("id");
                Long person_id = rs.getLong("person_id");
                String type = rs.getString("type");
                String status = rs.getString("status");
                String picture = rs.getString("picture");
                String description = rs.getString("description");       
                //get members of group
                ArrayList<Long> members = getMembers(oid);

                group.setOid(oid);
                group.setId(id);
                group.setPerson_id(person_id);
                group.setType(type);
                group.setStatus(status);
                group.setMembers(members);
                group.setPicture(picture);
                group.setDescription(description);              
                return group;         
            }
        }
        catch(Exception e){
           System.out.println(e.getMessage());
        }      
        return null;
    }

    public ArrayList<GroupObject> getGroups(String type){
        sql = "SELECT * FROM person_group WHERE type = '"+type+"' AND status = 'active'";
        
        ArrayList<GroupObject> groupAndMembers = new ArrayList<GroupObject>();       
        ResultSet rs;       
        try {       
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();
            int i = 0;
            // get members and insert them to list
            while ( rs.next() ) {
                GroupObject group = new GroupObject();
                //groupAndMembers;
                Long oid = rs.getLong("oid");
                Long person_id = rs.getLong("person_id");
                String id = rs.getString("id");       
                String status = rs.getString("status");
                String picture = rs.getString("picture");
                String description = rs.getString("description");
                //get members of group
                ArrayList<Long> members = getMembers( oid );
                
                group.setOid(oid);
                group.setId(id);
                group.setPerson_id(person_id);
                group.setType(type);
                group.setStatus(status);
                group.setMembers(members);
                group.setPicture(picture);
                group.setDescription(description);
                
                groupAndMembers.add(group);              
            }
        }
        catch(Exception e) {
           System.out.println(e.getMessage());
        }       
        return groupAndMembers;
    }

    public ArrayList<Long> getMembers( Long group_id ){
        sql = "SELECT m.person_id FROM membership as m JOIN person_group as p "
            + "ON p.oid = m.group_id WHERE p.oid = " + group_id + " AND p.status LIKE '%active%' ";

        ArrayList<Long> members = new ArrayList<Long>();
        ResultSet rs;

        /* check if group exists */
        if ( ! util.isGroup(group_id) ) {
            members.add(0, new Long(0));
            return members;
        }

        try {
            stmt = con.createStatement();
            stmt.executeQuery(sql);
            rs =  stmt.getResultSet();

            while ( rs.next() )
                    members.add( rs.getLong("person_id") );
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return members;
    }
    
    public String removeMemberfromGroup(Long group_id, Long person_id) {

        sql = "DELETE FROM membership WHERE group_id = "+group_id+" AND person_id = "+person_id;

        if( ! util.isPerson(person_id) )
            return "Sorry, but you are not a person!";

         /* check if group exists */
        if ( ! util.isGroup(group_id) )
            return "The group you selected does not exist!";

        try {
            stmt.executeUpdate(sql);

        }
        catch(Exception e){
           System.out.println(e.getMessage());
            e.printStackTrace();
            return "Error occurred";
        }

        return "Done!";
    }

   /* public String editGroup(Long group_id, Long person_id, int edit_id, Object changeParameter){

        String sql = "";

        //check if person is the owner of the group
        if ( ! util.isOwnerOfGroup(group_id, person_id) )
            return "Only owners can edit group";

        String type = util.groupType(group_id);

        if ( edit_id == 1 ) { // replace admin
            if ( type.equalsIgnoreCase("class")) {
                //check if new admin is a teacher
                if ( ! util.isTeacher( ( Long ) changeParameter ) )
                    return "Group owner must be a teacher!";
            }
                sql = "UPDATE person_group SET person_id = " + person_id + " WHERE oid = " + (Long) changeParameter;
        }
        else {  // rename group
            sql = "UPDATE ";
        }

        try {                  
            stmt.executeUpdate(sql);

        }
        catch(Exception e){
           System.out.println(e.getMessage());
            e.printStackTrace();
            return "Error occurred";
        }

        return "Done!";
    }*/

}


