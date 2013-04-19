package JavaConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Unity Developers
 */
public class Application extends Basic {
    String sql;
    Statement stmt;
    Connection con;
    
    public Application(){
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
    
    public String installApp(Long app_id, long user_id) {    
        Utils a = new Utils();
        String user_pid = a.getPersonName(user_id);
        
        String map_id=createMap(app_id, user_pid);
        if (!map_id.equals("Map creation failure")) {    
            if (!addMapValues(app_id, map_id).equals("OK"))
                return "Error occurred";      
        } else
            return "Error occurred";    
        sql = "INSERT INTO person_application(person_id, application_id) "
                                    + "VALUES( '"+user_id+"', '"+app_id+"')";
        try {
            stmt.executeUpdate(sql);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }      
        return "Done!";
    }
    
    public boolean installIntermApp(Long app_id, Long user_id, Long interm) {  
        sql = "INSERT INTO person_application(person_id, application_id, interm_id) "
                                + "VALUES( "+user_id+", "+app_id+", "+interm+")";
        try {
            stmt.executeUpdate(sql);     
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }      
        return true;
    }
    
    public boolean removeIntermApp(Long app_id, Long user_id, Long interm) {  
        sql = "DELETE FROM person_application WHERE person_id = " +user_id
            + " AND application_id = " +app_id+ " AND interm_id = "+interm+";";
        try {
            stmt.executeUpdate(sql);     
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }      
        return true;
    }
    
    private String createMap(Long app_id, String user_id) {
        String map_id = "Map creation failure";
        try {
            sql = "INSERT INTO application_datamap(application_id, person_id) "
                        + "VALUES( '"+app_id+"', '"+user_id+"');";
            stmt.executeUpdate(sql);   
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Map creation failure";
        }
        try {
            sql = "SELECT oid from application_datamap "
                + "WHERE application_id="+app_id+" AND person_id='"+user_id+"';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                map_id = ""+rs.getInt("oid");
            } 
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return map_id;
    }
    
    private String addMapValues(Long app_id, String map_id) {    
        sql = "SELECT field, default_value from application_property "
                                    + "WHERE application_id="+app_id+";";
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                 HashMap<String,String> results = new HashMap<String,String>();
                 results.put(rs.getString("field"), rs.getString("default_value"));
                 list.add(results);
            }
            rs.close();
            try {
                for (int k=0; k<list.size(); k++) {
                    Set set = list.get(k).keySet();
                    Iterator iter = set.iterator();
                    while (iter.hasNext()) {
                        Object o = iter.next();
                        String key = o.toString();
                        String value = list.get(k).get(key);
                        sql = "INSERT INTO application_datavalue(name, value, application_datamap_id) "
                            + "VALUES( '"+key+"', '"+value+"','"+map_id+"' );";
                        stmt.executeUpdate(sql);
                    }   
                }
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
                return "Error occurred";
            } 
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }      
        return "OK";
    }
    
    public ArrayList<HashMap<String, String>> getInstalledApps(String user) {     
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Utils util = new Utils();     
        long user_id=util.getPersonObjectId(user);
        
        sql = "SELECT oid,id from application "
            + "WHERE oid IN ( SELECT application_id FROM person_application "
            + "WHERE person_id="+user_id+" );";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> results = new HashMap<String,String>();
                results.put(rs.getString("oid"), rs.getString("id"));
                list.add(results);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }       
        return list;
    }
    
    public ArrayList<HashMap<String, String>> getAvailableApps(long user){      
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Utils util = new Utils();
        int acad_role = util.getPersonAcademicRole(user);
        
        sql = "SELECT id, description FROM application "
            + "WHERE oid IN ( SELECT app_id FROM application_access WHERE role_id="+acad_role+" ) "
            + "AND oid NOT IN (SELECT application_id FROM person_application WHERE person_id = "
            +user+" AND interm_id IS NULL);";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> results = new HashMap<String,String>();
                results.put(rs.getString("id"), rs.getString("description"));
                list.add(results);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }     
        return list;
    }
    
    public ArrayList<String> getIntermediateApps(Long personId, Long friendId) {     
        ArrayList<String> list = new ArrayList<String>();           
        sql = "SELECT fAppId from friend "
            + "WHERE person_id="+personId+" AND friend_id="+friendId+" AND ftype='intermediate' ;";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("fAppId"));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }       
        return list;
    }
      
    public String getApplication(String user_id) {
        String iframeUrl="No Apps";
        sql = "SELECT url from application WHERE oid IN "
            + "( SELECT application_id FROM person_application WHERE person_id="+user_id+" );";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                iframeUrl = rs.getString("url");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return iframeUrl;
    }
    
    public Long getApplicationObjectId(String app) {
        Long oid = new Long(-1);
        sql = "SELECT oid from application WHERE id = '"+app+"'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                oid = rs.getLong("oid");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return oid;
        }
        return oid;
    }
    
    public String getApplicationUrl(String app_id) {
        String iframeUrl="No Apps";
        sql = "SELECT url from application WHERE oid = "+app_id+";";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                iframeUrl = rs.getString("url");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error occurred";
        }
        return iframeUrl;
    }
    
    public String deleteApp(long app_id, long user_id) {    
        Utils util = new Utils();

        sql = "DELETE FROM person_application "
            + "WHERE person_id="+user_id+" AND application_id="+app_id+";";
        String sql1 = "DELETE FROM application_datamap "
                    + "WHERE person_id='"+util.getPersonName(user_id)+"' AND application_id="+app_id+";";
        String sql2 = "DELETE FROM application_datavalue "
                    + "WHERE application_datamap_id "
                    + "IN (SELECT oid FROM application_datamap "
                    + "WHERE person_id='"+util.getPersonName(user_id)+"' AND application_id="+app_id+");";
        try {
            stmt.executeUpdate(sql);     
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql1);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Error Ocurred";
        } 
        return "OK";
    }
}
