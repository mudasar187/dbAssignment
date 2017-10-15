package Program;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DBHandlerTest {

    private DBHandler dbHandler;
    private DBFileHandler dbFileHandler;
    private DBTable table;

    @Before
    public void setUp() {
        dbHandler = new DBHandler(new DBConnection("src/test/resources/test-DB-right.properties"));
        dbFileHandler = new DBFileHandler();
        table = new DBTable();
    }

    @After
    public void tearDown() {
        dbHandler = null;
        dbFileHandler = null;
        table = null;
    }


    @Test
    public void testGetQueryForCreateTable() {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        assertEquals(dbHandler.getQueryCreateTable(table), "CREATE TABLE Employee (\n" +
                "id INT(11) AUTO_INCREMENT,\n" +
                "firstName VARCHAR(255) NOT NULL,\n" +
                "lastName VARCHAR(255) NOT NULL,\n" +
                "email VARCHAR(255) NOT NULL UNIQUE,\n" +
                "PRIMARY KEY (id)\n" +
                ");");

        System.out.println(dbHandler.getQueryCreateTable(table));
    }

    @Test
    public void testCreateTable() {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        dbHandler.createTable(table);
    }

    @Test
    public void testGetQueryForInsertData() {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        assertEquals(dbHandler.getInsertDataQuery(table), "INSERT INTO Employee (firstName, lastName, email)\n" +
                "VALUES\n" +
                "(?, ?, ?)");

        System.out.println(dbHandler.getInsertDataQuery(table));
    }

    @Test
    public void testInsertDataIntoTable() {

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        dbHandler.insertData(table);
    }

    @Test
    public void testTruncateTable() {

        dbHandler.truncateTable("Employee");

    }

    @Test
    public void testDropTable() {

        dbHandler.dropTable("Employee");
    }

}