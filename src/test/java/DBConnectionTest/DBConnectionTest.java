package DBConnectionTest;


import Application.Database.Connection.DBConnection;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DBConnectionTest {


    @Test
    public void testConnectionIfValidFile() {

        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-right.properties");

        Connection connection = dbConnection.getConnection();

        assertNotNull(connection);
    }


    @Test
    public void testConnectionIfNotValidFile() {

        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-wrongFile.properties");

        Connection connection = dbConnection.getConnection();

        assertNull(connection);
    }


    @Test
    public void testConnectionIfNullInParameter() {

        DBConnection dbConnection = new DBConnection(null);

        Connection connection = dbConnection.getConnection();

        assertNull(connection);
    }


    @Test
    public void testConnectionIfFileNotExist() {

        DBConnection dbConnection = new DBConnection("fileNotExists");

        Connection connection = dbConnection.getConnection();

        assertNull(connection);
    }

    @Test
    public void testBooleanStatusIfConnected() {

        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-right.properties");

        boolean check = dbConnection.isConnected();

        assertTrue(check);
    }

    @Test
    public void testBooleanStatusIfNotConnected() {

        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-wrongFile.properties");

        boolean check = dbConnection.isConnected();

        assertFalse(check);

    }
}