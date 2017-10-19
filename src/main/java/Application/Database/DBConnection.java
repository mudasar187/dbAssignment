package Application.Database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * DBConnection
 * Class for connection to database
 * <p>
 * Last modified 19 october 2017
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
     * @param properties, file path
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
            se.printStackTrace();
            System.out.println("\n### Connection error ###");
        }
        return connection;
    }


    /**
     * Method to acsess in DBHandler.class
     *
     * @return dbName, name of the database
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