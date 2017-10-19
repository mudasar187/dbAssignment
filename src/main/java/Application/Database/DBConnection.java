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
     * Read the config file
     *
     * @param properties, get config file path
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
     * Making connection without dbName, creating own method for that so user dont need to create database manually
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
     * Get method so I can access them in the DBHandler class
     *
     * @return dbName, name of the database
     */
    public String getDbName() {

        return dbName;
    }


    /**
     * Checks if connection is valid or not
     * Have this method so I can launch the application in the Program.class if connection is valid,
     * if it is not valid then the program does not run and u will get an error message
     *
     * @return true if connection is valid, return false if connection is not valid
     */
    public boolean isConnected() {

        if (getConnection() != null)
        {
            return true;
        }
        return false;
    }
}