package Program;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DBFileHandlerTest {


    /**
     * Test if file is valid
     */
    @Test
    public void testFileReaderIfFileIsValid() {
        DBFileHandler dbFileHandler = new DBFileHandler();

        boolean check = dbFileHandler.readFile("src/test/resources/inputFilesTest/lecturerTestRight.txt");

        assertTrue(check);
    }


    /**
     * Test with null parameter
     */
    @Test
    public void testFileReaderIfParameterNull() {
        DBFileHandler dbFileHandler = new DBFileHandler();

        boolean check = dbFileHandler.readFile(null);

        assertFalse(check);
    }


    /**
     * Test if file not exists
     */
    @Test
    public void testFileReaderIfFileNotExists() {
        DBFileHandler dbFileHandler = new DBFileHandler();

        boolean check = dbFileHandler.readFile("sdsggs");

        assertFalse(check);
    }


    /**
     * Test if file exists but there is no content in the file
     */
    @Test
    public void testFileReaderIfThereIsNoContentInFile() {
        DBFileHandler dbFileHandler = new DBFileHandler();

        boolean check = dbFileHandler.readFile("src/test/resources/inputFilesTest/lecturerTestWithNoContent.txt");

        assertFalse(check);

    }
}