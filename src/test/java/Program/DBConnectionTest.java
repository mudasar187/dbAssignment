package Program;


import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DBConnectionTest {


    /**
     * Test with valid data in properties file
     */
    @Test
    public void testConnectionIfValidFile() {
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-right.properties");
        Connection connection = dbConnection.getConnection();

        assertNotNull(connection);
    }


    /**
     * Test with invalid data in properties file
     */
    @Test
    public void testConnectionIfNotValidFile() {
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-wrongFile.properties");
        Connection connection = dbConnection.getConnection();

        assertNull(connection);
    }


    /**
     * Test with null parameter
     */
    @Test
    public void testConnectionIfNullInParameter() {
        DBConnection dbConnection = new DBConnection(null);

        assertNull(dbConnection.getConnection());
    }


    /**
     * Test if property file not exists
     */
    @Test
    public void testConnectionIfFileNotExist() {
        DBConnection dbConnection = new DBConnection("fileNotExists");

        assertNull(dbConnection.getConnection());
    }
}