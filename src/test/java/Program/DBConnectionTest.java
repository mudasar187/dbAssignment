package Program;


import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DBConnectionTest {


    @Test
    public void testConnectionIfValidFile() {

        // Arrange
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-right.properties");

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNotNull(connection);
    }


    @Test
    public void testConnectionIfNotValidFile() {

        // Arrange
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-wrongFile.properties");

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNull(connection);
    }


    @Test
    public void testConnectionIfNullInParameter() {

        // Arrange
        DBConnection dbConnection = new DBConnection(null);

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNull(connection);
    }


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