package Program;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Class for connection to database
 *
 * Last modified 04 october 2017
 *
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
     * Read the file properties and set hostName, dbName, userName, password, port
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
            System.out.println("### Something wrong with properties file, please check file and try again ###");
        }
    }


    /**
     * Get connection to database
     * Making connection without dbName, creating own method for that so user dont need to create database manually
     * @return Connection
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
            // se.printStackTrace();
            System.out.println("\n### Connection error ###");
        }
        return connection;
    }


    /**
     * Get and Set for dbName (database name)
     * I have set and get dbName so i can use it in DBHandler class in createDatabase() method
     */

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}