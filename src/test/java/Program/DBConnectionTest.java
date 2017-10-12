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

        // Arrange
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-right.properties");

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNotNull(connection);
    }


    /**
     * Test with invalid data in properties file
     */
    @Test
    public void testConnectionIfNotValidFile() {

        // Arrange
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-wrongFile.properties");

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNull(connection);
    }


    /**
     * Test with null parameter
     */
    @Test
    public void testConnectionIfNullInParameter() {

        // Arrange
        DBConnection dbConnection = new DBConnection(null);

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNull(connection);
    }


    /**
     * Test if property file not exists
     */
    @Test
    public void testConnectionIfFileNotExist() {

        // Arrange
        DBConnection dbConnection = new DBConnection("fileNotExists");

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNull(connection);
    }
}