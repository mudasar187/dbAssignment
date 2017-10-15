package Program;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBFileHandlerTest {

    private DBFileHandler dbFileHandler;

    @Before
    public void setUp() {
        dbFileHandler = new DBFileHandler();
    }


    @Test
    public void testFillTableWithRightFile() {

        // Arrange
        DBTable table = new DBTable();

        // Act
        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileIsRight.txt", table);

        // Assert
        assertEquals(table.getTableName(), "Employee");
        assertEquals(table.getColumnsName()[0], "id");
        assertEquals(table.getColumnsName()[1], "firstName");
        assertEquals(table.getColumnsName()[2], "lastName");
        assertEquals(table.getColumnsName()[3], "email");
        assertEquals(table.getDataTypes()[0],"INT(11) AUTO_INCREMENT");
        assertEquals(table.getDataTypes()[1],"VARCHAR(255) NOT NULL");
        assertEquals(table.getDataTypes()[2],"VARCHAR(255) NOT NULL");
        assertEquals(table.getDataTypes()[3],"VARCHAR(255) NOT NULL UNIQUE");
        assertEquals(table.getPrimaryKey(), "id");
    }


    @Test(expected = NullPointerException.class)
    public void testFillTableWithNoFileExistsInParameter() {

        DBTable table = new DBTable();

        dbFileHandler.makeTable("fsdfsdf", table);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testFillTableWithFileHasNoContent() {
        DBTable table = new DBTable();

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileWithNoContent.txt", table);
    }


    @Test
    public void testFillTableWithWrongLayOut() {
        DBTable table = new DBTable();

        dbFileHandler.makeTable("src/test/resources/inputFilesTest/fileWithWrongLayout.txt", table);
    }

}