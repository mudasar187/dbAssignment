package Application.Database.Connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * <p>Connection class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Connection
 * Class for connection to database
 * <p>
 * Last modified 18 november 2017
 */
public class DBConnection {

    /**
     * Connection metadata
     */
    private String hostName;
    private String dbName;
    private String userName;
    private String passWord;
    private int port;
    private Properties propFile;
    private MysqlDataSource mysqlDataSource;


    /**
     * Default constructor
     *
     * @param properties a {@link java.lang.String} object.
     */
    public DBConnection(String properties)
    {

        try
        {
            propFile = new Properties();
            FileInputStream fileInputStream = new FileInputStream(properties);
            propFile.load(fileInputStream);
            userName = propFile.getProperty("userName");
            hostName = propFile.getProperty("hostName");
            dbName = propFile.getProperty("dbName");
            passWord = propFile.getProperty("passWord");
            port = Integer.parseInt(propFile.getProperty("port"));
            fileInputStream.close();
        }
        catch (IOException se)
        {
            System.out.println("### Properties file not found ###");

        }
        catch (NullPointerException n)
        {
            System.out.println("### Properties file not added ###");
        }
    }


    /**
     * Get connection to database
     *
     * @return connection
     */
    public Connection getConnection() {

        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setServerName(hostName);
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setPassword(passWord);
        mysqlDataSource.setPort(port);
        Connection connection = null;
        try
        {
            connection = mysqlDataSource.getConnection();

        }
        catch (SQLException se)
        {
//            se.printStackTrace();
            System.out.println("\n### Connection error ###");
        }
        return connection;
    }


    /**
     * Created a getter so i can access this in DBHandler.class
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDbName() {

        return dbName;
    }


    /**
     * Method to check status of connection
     *
     * @return true if connection is valid / false if not
     */
    public boolean isConnected() {

        if (getConnection() != null)
        {
            return true;
        }
        return false;
    }
}
