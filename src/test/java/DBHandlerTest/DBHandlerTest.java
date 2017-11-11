package DBHandlerTest;

import Application.Database.Connection.DBConnection;
import Application.Database.DBHandler.DBHandler;
import Application.Database.Filereader.DBFileHandler;
import Application.Database.OutPutHandler.DBOutPutHandler;
import Application.Database.TableObject.DBTableObject;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DBHandlerTest {

    private DBFileHandler dbFileHandler;
    private DBTableObject dbTableObject;
    private DBHandler dbHandler;

    @BeforeClass
    public static void createDatabase() throws SQLException {
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-right.properties");

        try(Connection connection = dbConnection.getConnection();
        PreparedStatement createDatabase = connection.prepareStatement("CREATE DATABASE "+dbConnection.getDbName());)
        {
            createDatabase.executeUpdate();
        }
    }

    @AfterClass
    public static void dropDatabase() throws SQLException {
        DBConnection dbConnection = new DBConnection("src/test/resources/test-DB-right.properties");

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement dropDatabase = connection.prepareStatement("DROP DATABASE "+dbConnection.getDbName());)
        {
            dropDatabase.executeUpdate();
        }
    }

    @Before
    public void setUp() throws SQLException {

        dbHandler = new DBHandler(new DBConnection("src/test/resources/test-DB-right.properties"),
                new DBOutPutHandler());
        dbFileHandler = new DBFileHandler();
        dbTableObject = new DBTableObject();
    }

    @Test
    public void createTable() throws SQLException {

        dbFileHandler.makeObject("src/test/resources/inputFilesTest/fileIsRight.txt", dbTableObject);

        String returnString = dbHandler.createTable(dbTableObject);

        assertEquals(returnString, "### Table Employee created succsessfully ###");
    }

    @Test
    public void insertData() throws SQLException {

        dbFileHandler.makeObject("src/test/resources/inputFilesTest/fileIsRight.txt", dbTableObject);

        String returnString = dbHandler.insertData(dbTableObject);

        assertEquals(returnString, "### Inserted 1 rows in table Employee ###");

    }

    @Test
    public void getMetadata() throws SQLException {

        String returString = dbHandler.getMetaDataFromTable("Employee");

        assertNotNull(returString);
    }

    @Test
    public void getDataFromTable() throws SQLException {

        String returnString = dbHandler.getDataFromTable("Employee");

        assertNotNull(returnString);
    }

    @Test
    public void getAnyValue() throws SQLException {

        String returnString = dbHandler.getAnyValueFromAnyTable("Employee", "firstName", "Nicholas");

        assertNotNull(returnString);
    }

    @Test
    public void getAllTables() throws SQLException {

        String returnString = dbHandler.showAllTables();

        assertNotNull(returnString);
    }

}
