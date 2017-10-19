package Application;

import Application.Database.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;


public class DBHandlerTest {

    private DBHandler dbHandler;
    private DBFileHandler dbFileHandler;
    private DBTableObject table;

    @Before
    public void setUp() {
        dbHandler = new DBHandler(new DBConnection("src/test/resources/test-DB-right.properties"),
                new DBOutPutHandler());
        dbFileHandler = new DBFileHandler();
        table = new DBTableObject();
    }

    @After
    public void tearDown() {
        dbHandler = null;
        dbFileHandler = null;
        table = null;
    }

    @Test
    public void testCreateDatabase() throws SQLException {

        dbHandler.createDataBase();

        String exceptedResult = "### Database created successfully ###\n";

        assertEquals(exceptedResult, dbHandler.createDataBase());
    }


    @Test
    public void testGetQueryForCreateTableIfFileContentIsRight() {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        assertEquals(dbHandler.getQueryCreateTable(table), "CREATE TABLE Employee (\n" +
                "id INT(11) AUTO_INCREMENT,\n" +
                "firstName VARCHAR(255) NOT NULL,\n" +
                "lastName VARCHAR(255) NOT NULL,\n" +
                "email VARCHAR(255) NOT NULL UNIQUE,\n" +
                "PRIMARY KEY (id)\n" +
                ");");

    }

    @Test
    public void testCreateTable() throws SQLException {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        dbHandler.createTable(table);
    }

    @Test
    public void testGetQueryForInsertData() throws SQLException {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        assertEquals(dbHandler.getInsertDataQuery(table), "INSERT INTO Employee (firstName, lastName, email)\n" +
                "VALUES\n" +
                "(?, ?, ?)");
    }

    @Test
    public void testInsertDataIntoTable() throws SQLException {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        dbHandler.insertData(table);
    }


    @Test
    public void testDropTable() throws SQLException {

        dbHandler.dropTable("Employee");
    }

}