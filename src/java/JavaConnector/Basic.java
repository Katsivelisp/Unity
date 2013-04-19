package JavaConnector;

/**
 *
 * @author Unity Developers
 */
public abstract class Basic {
    
    /* Connection utils */
    protected static final String server = "http://localhost";
    protected static final String P_port = ":8084"; // project port
    protected static final String S_port = ":8080"; // shindig port
    protected static final String P_name = "/Web"; // project name
    
    protected static final String db_class_name = "com.mysql.jdbc.Driver";
    protected static final String db_url = "jdbc:mysql://195.130.90.176:3306/shindig";
    protected static final String db_user = "shindig";
    protected static final String db_password = "shindig";
    
}
