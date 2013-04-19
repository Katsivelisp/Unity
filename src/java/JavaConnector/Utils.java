package JavaConnector;

/* Connection Pooling */

import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author UNITY Developers
 */

public class Utils extends Basic {

    String sql;
    Connection con;
    Statement stmt = null;

    public Utils(){
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

//TODO Sxoliase tin sunartisi
public int checkFriendshipRequest(String personId, String friendId, String type ){

    String sqlP = "SELECT acad_role FROM person WHERE oid = '"+personId+"'";
    String sqlF = "SELECT acad_role FROM person WHERE oid = '"+friendId+"'";

    ResultSet rsP, rsF;
    int personIdRole, friendIdRole;

    /* get person and friend academic role */
    try {

        stmt.executeQuery(sqlP);
        rsP =  stmt.getResultSet();
        rsP.first();
        personIdRole = rsP.getInt("acad_role");

        stmt.executeQuery(sqlF);
        rsF = stmt.getResultSet();
        rsF.first();
        friendIdRole = rsF.getInt("acad_role");

        rsP.close();
        rsF.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
    }

    /* check if academic role gives to the person the possibility to connect with
     * a friend with a "type" conection */
    int result = validateFriendship(personIdRole, friendIdRole, type);

    /* if 0 then success */
    if( result == 0 )
        return 0;

    return -1;

}

//TODO Sxoliase tin sunartisi
public int validateFriendship(int roleP, int roleF, String type ){

    if( type.equalsIgnoreCase("fellow"))
    {
        /* fellow relationship is valid for all roles */
        return 0;
    }

    else if(type.equalsIgnoreCase("tutor"))
    {
        /* tutor relationship is possible only when person is a
         * student and friend is a teacher */
        if( ( roleP > 0 && roleP < 4 ) && ( roleF > 3 && roleF < 6) )
            return 0;
        else
            return -1;
    }
    else if (type.equalsIgnoreCase("facilitator")) {

        /* facilitator relationship is possible only when person is an
         * non-administrative staff and friend is an administrative staff */
        if( (roleP > 0 && roleP < 6) && (roleF > 5 && roleF < 10) )
            return 0;
        else
            return -1;
    }

    return -1;
}

/* Check if person exists by oid */
public boolean isPerson(Long oid) {
    sql = "SELECT * FROM person WHERE oid = " + oid;

    ResultSet rs;

    /* check if select query returns anything */
    try {
    
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();


        /* the person exists */
        if ( rs.first() )
                return true;
        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }

    /* there is no person with given person_id */
    return false;
}

/* Check if person exists by person_id */
public boolean isPerson(String person_id) {
    sql = "SELECT * FROM person WHERE person_id = '"+ person_id + "'";

    ResultSet rs;

    /* check if select query returns anything */
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();


        /* the person exists */
        if ( rs.first() )
                return true;
        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }

    /* there is no person with given person_id */
    return false;
}

public String getPersonName(Long oid){
    sql = "SELECT person_id FROM person WHERE oid = " + oid;   
    ResultSet rs;
    
    /* get person and friend academic role */
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();    
        if ( rs.first() )
            return rs.getString("person_id");
        rs.close();      
        return null;
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }    
    return "something went wrong";
}

public String getPersonDisplayName(String person_id){
    sql = "SELECT display_name FROM person WHERE person_id = '" + person_id + "'";
    
    ResultSet rs;
    
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        if ( rs.first() )
            return rs.getString("display_name");

        rs.close();
        
        return null;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    return "something went wrong";
}

public String getPersonDisplayName(Long oid){
    sql = "SELECT display_name FROM person WHERE oid = " + oid;
    
    ResultSet rs;
    
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        if ( rs.first() )
            return rs.getString("display_name");

        rs.close();
        
        return null;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    return "something went wrong";
}

public String getPersonProfilePicture(Long oid){
    sql = "SELECT profile_picture FROM person WHERE oid = " + oid;
    
    ResultSet rs;
    
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        if ( rs.first() )
            return rs.getString("profile_picture");

        rs.close();
        
        return null;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    return "something went wrong";
}

public String getPersonProfilePicture(String person_id){
    sql = "SELECT profile_picture FROM person WHERE person_id = '" + person_id + "'";
    
    ResultSet rs;
    
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        if ( rs.first() )
            return rs.getString("profile_picture");

        rs.close();
        
        return null;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    return "something went wrong";
}

/* First attr of hashMap is person_id and second attr is display_name of person */
public HashMap<String, String> getAllIdsAndNames() {
    HashMap<String, String> names = new HashMap<String, String>();
    
    sql = "SELECT person_id, display_name FROM person";
    ResultSet rs;
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        while ( rs.next() ) {
            names.put(rs.getString("person_id"), rs.getString("display_name"));            
        }
        rs.close();
        
        return names;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    
    return names;
}

public ArrayList<String> getAllIds() {
     ArrayList<String> ids = new  ArrayList<String>();
    
    sql = "SELECT person_id FROM person";
    ResultSet rs;
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        while ( rs.next() ) {
            ids.add(rs.getString("person_id"));            
        }
        rs.close();
        
        return ids;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    
    return null;
}

public ArrayList<String> getAllNames() {
     ArrayList<String> names = new  ArrayList<String>();
    
    sql = "SELECT display_name FROM person";
    ResultSet rs;
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        while ( rs.next() ) {
            names.add(rs.getString("display_name"));            
        }
        rs.close();
        
        return names;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    
    return null;
}

public ArrayList<Long> getAllGroupOids() {
     ArrayList<Long> oids = new  ArrayList<Long>();
    
    sql = "SELECT oid FROM person_group";
    ResultSet rs;
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        while ( rs.next() ) {
            oids.add(rs.getLong("oid"));            
        }
        rs.close();
        
        return oids;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    
    return null;
}

public ArrayList<String> getAllGroupNames() {
     ArrayList<String> names = new  ArrayList<String>();
    
    sql = "SELECT id FROM person_group";
    ResultSet rs;
    /* get person and friend academic role */
    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        while ( rs.next() ) {
            names.add(rs.getString("id"));            
        }
        rs.close();
        
        return names;

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    
    
    return null;
}

public Long getPersonObjectId(String person_id){
    
    sql = "SELECT oid FROM person WHERE person_id = '" + person_id +"'";
    
    ResultSet rs;
    try { /* get person and friend academic role */
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        if (rs.first())
            return rs.getLong("oid");
        rs.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    } 
    return new Long(0);
}

public Long getGroupObjectId(Long person_id, String id, String type){
    sql = "SELECT oid FROM person_group WHERE person_id = " + person_id 
                                    +" AND id = '"+id+"' AND type = '"+type+"'";
    
    ResultSet rs;  
    /* get person and friend academic role */
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        if ( rs.first() )
            return rs.getLong("oid");
        rs.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }  
    return new Long(0);
}

public Integer getPersonAcademicRole(Long oid){
    
    sql = "SELECT acad_role FROM person WHERE oid = " + oid;
    ResultSet rs;
    Integer role = 0;
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        if ( rs.first() )
            role = rs.getInt("acad_role");
        rs.close();   
        return role;
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }   
    return role;
}

public String convertAcademicRole(Integer role){

    switch( role ){
        case 1:
            return "Undegraduate student";
        case 2:
            return "Postgraduate Student";
        case 3:
            return "Phd Student";
        case 4:
            return "Faculty Teacher";
        case 5:
            return "Temporary Teacher";
        case 6:
            return "Secretary";
        case 7:
            return "ETEP";
        case 8:
            return "Library";
        case 9:
            return "Misc";
    }
    
    return "";
}

public String convertDepartment( Integer department ){

    switch( department ){
        case 1:
            return "Informatics & Telematics";
        case 2:
            return "Nutriotions & Dietics";
        case 3:
            return "Geography";
        case 4:
            return "Home Economics and Ecology";
    }
    
    return "";
}


/* check if two personas have a fellow relationship */
public boolean isFellow(Long person_id, Long fellow_id) {

    sql = "SELECT * FROM friend WHERE person_id = " + person_id + " AND friend_id = " + 
            fellow_id + " AND ftype = 'fellow'";

    ResultSet rs;

    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();

        /* the personas are fellows */
        if ( rs.first() )
                return true;
        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    /* the personas are not fellows */
    return false;
}

/* check if a person has requested a fellow relationship from another person  */
public boolean hasRequestedFellowship(Long person_id, Long fellow_id) {

    sql = "SELECT * FROM friend_request WHERE person_id = " + person_id + " AND friend_id = " + 
            fellow_id + " AND accepted = 'pending'";

    ResultSet rs;

    try {

        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();

        /* the personas are fellows */
        if ( rs.first() )
                return true;
        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    /* the personas are not fellows */
    return false;
}

/* check if a person has requested a fellow relationship from another person  */
public boolean hasRequests(Long person_id) {

    sql = "SELECT * FROM friend_request WHERE friend_id = " + person_id 
                                                + " AND accepted = 'pending'";
    ResultSet rs;
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        /* the personas are fellows */
        if ( rs.first() )
                return true;
        rs.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }
    /* the personas are not fellows */
    return false;
}

/* check if a person have a tutor relationship with another */
public boolean isTutor( Long person_id, Long tutor_id) {

    sql = "SELECT * FROM friend WHERE person_id = " + person_id + " AND friend_id = " + 
            tutor_id + " AND ftype = 'tutor'";
    ResultSet rs;
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        /* the persona is a tutor */
        if ( rs.first() )
                return true;
        rs.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }
    /* the persona is not a tutor */
    return false;
}

/* check if a person have a tutor relationship with another */
public boolean isFacilitator( Long person_id, Long facilitator_id) {

    sql = "SELECT * FROM friend WHERE person_id = " + person_id + " AND friend_id = " + 
            facilitator_id + " AND ftype = 'facilitator'";  
    ResultSet rs;
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();

        /* the persona is a tutor */
        if ( rs.first() )
                return true;
        rs.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }
    /* the persona is not a tutor */
    return false;
}

/* check if the oid is a teacher or not */
public boolean isTeacher( Long oid ) {

    sql = "SELECT * FROM person WHERE oid = " + oid + " AND acad_role > 3 AND acad_role < 6";

    ResultSet rs;

    /* check if select query returns anything */
    try {
        stmt = con.createStatement();
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();

        /* the person is a teacher */
        if ( rs.first() )
                return true;

        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }
    /* not a teacher */
    return false;
}

public boolean isGroup( Long group_id ) {

    sql = "SELECT * FROM person_group WHERE oid = " + group_id;

    ResultSet rs;

    /* check if select query returns anything */
    try {
        stmt = con.createStatement();
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();


        /* the group exists */
        if ( rs.first() )
                return true;
        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }

    /* there is no group with given group_id */
    return false;
}

public boolean isOwnerOfGroup(Long group_id, Long person_id) {
    sql = "SELECT * FROM person_group WHERE oid = " + group_id + " AND person_id = " + person_id;

    ResultSet rs;
    /* check if select query returns anything */
    try {
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        
        /* the person is the group's admin */
        if ( rs.first() ) 
                return true;   
        rs.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }
    /* not an admin */
    return false;
}

public boolean isMemberOfGroup(long group_id, long person_id) {
    sql = "SELECT * FROM person_group p JOIN membership m ON p.oid = m.group_id "
            + "WHERE ( p.oid = " + group_id + " AND m.person_id = " + person_id + ") "
            + "OR ( p.oid = " + group_id + " AND p.person_id = " + person_id + ");";

    ResultSet rs;
    /* check if select query returns anything */
    try {
        stmt = con.createStatement();
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();
        /* the person is a member of the group */
        if ( rs.first() ) { 
            System.out.println( rs.getLong("person_id") );
            return true;
        }
        rs.close();
    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
    }
    /* not a member */
    return false;
}

/* return the type of a group */
public String groupType(long oid) {

    sql = "SELECT type FROM person_group WHERE oid = " + oid;

    ResultSet rs;

    /* check if select query returns anything */
    try {
        stmt = con.createStatement();
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();

        /* if there is a result return group type */
        if ( rs.first() )
                return rs.getString("type");
        
        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }
    /* non type found */
    return "null";
}

/* return the name of a group give its id */
public String getGroupName(Long oid) {

    sql = "SELECT * FROM person_group WHERE oid = " + oid;

    ResultSet rs;

    /* check if select query returns anything */
    try {
        stmt = con.createStatement();
        stmt.executeQuery(sql);
        rs =  stmt.getResultSet();

        /* if there is a result return group type */
        if ( rs.first() ) 
                return rs.getString("id");
        
        rs.close();

    }
    catch( Exception e ) {
            System.out.println(e.getMessage());
            e.printStackTrace();
    }

    /* non type found */
    return "null";
}

/* get time passed between two timestamps */
public ArrayList<String> getTimePassed(Long t1, Long t2) {

    ArrayList<String> result = new ArrayList<String>();

    /* time passed in milliseconds */
    Long time = t1 - t2;

    Long diffSeconds = time / 1000;
    if ( diffSeconds < 60 ) {
        result.add(0, diffSeconds.toString());
        result.add(1, "seconds");
        
        return result;       
    }
        

    Long diffMinutes = time / (60 * 1000);
    if ( diffMinutes < 60 ) {
        result.add(0, diffMinutes.toString());
        result.add(1, "minutes");
        return result;
    }
        

    Long diffHours = time / (60 * 60 * 1000);
    if ( diffHours < 60 ) {
        result.add(0, diffHours.toString());
        result.add(1, "hours");
        return result;
    }
        

    Long diffDays = time / (24 * 60 * 60 * 1000);
    if ( diffDays < 60 ){
        result.add(0, diffDays.toString());
        result.add(1, "days");
        return result;
    }

    Long diffMonths = time / (30 * 24 * 60 * 60 * 1000);
    if ( diffDays < 60 ){
        result.add(0, diffMonths.toString());
        result.add(1, "months");
        return result;
    }

    Long diffYears = time / (12 * 30 * 24 * 60 * 60 * 1000);
    if ( diffYears < 60 ){
        result.add(0, diffYears.toString());
        result.add(1, "years");
        return result;
    }
        
    return null;
}


public String convertNull(String str){
    if(str==null)
        str="";
    else if(str.equals("null"))
        str="";
    else if((str.trim()).equals(""))
        str="";
    else if(str.equals(null))
        str="";
    else
         str=str.trim();
    
    return str;
}

public String transformEmailToId(String email){
    int index = email.lastIndexOf("@hua.gr");
    return email.substring(0, index);
}

public boolean LDAPauth(String username,String password) {       
    String msg="";
    try {      
        Hashtable env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://hercules.hua.gr:3268");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_PRINCIPAL, "cn="+username+",OU=Users, dc=hua, dc=gr");
        env.put(Context.SECURITY_PRINCIPAL, username);
       // msg+=username+password;
        env.put(Context.SECURITY_CREDENTIALS, password);
        // Create initial context
        DirContext ctx = new InitialDirContext(env);
        ctx.close();
        return true;
    } catch (NamingException e) {
        msg+=e.toString();
    }
    return false;
}

public String getIFrameUrl(String viewer, String owner, String url) {   
     String iframeUrl = ""; 
     Long appid = new Long(0);

     // generate SecurityToken
     String viewerId = viewer;
     String ownerId = owner;
     String domain = "default";
     String container = "default";
     long moduleId = 0;
     try {
        String separator = URLEncoder.encode(":", "UTF-8");
        String urlEncoded = url.replaceAll(":", "%253A");
        urlEncoded = urlEncoded.replaceAll("/", "%252F");
        StringBuilder string = new StringBuilder();

        string.append(URLEncoder.encode(ownerId, "UTF-8")).append(separator).
               append(URLEncoder.encode(viewerId, "UTF-8")).append(separator).
               append(Long.toString(appid)).append(separator).
               append(URLEncoder.encode(domain, "UTF-8")).append(separator).
               append(urlEncoded).append(separator).
               append(Long.toString(moduleId)).append(separator).append(container);   
        String securityToken = string.toString();

        // generate iframeUrl
        String serverBase = "http://localhost:8080/gadgets/";     
        String parent = URLEncoder.encode("http://localhost:8080", "UTF-8");
        iframeUrl = serverBase + "ifr?" + "&st=" + securityToken 
                  + "&url=" + URLEncoder.encode(url, "UTF-8");
     } catch (Exception e) {
        System.out.println(e.getMessage());
     } 
     return iframeUrl;
}
}//end of class

