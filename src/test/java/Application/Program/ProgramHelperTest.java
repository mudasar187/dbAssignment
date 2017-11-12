package Application.Program;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ProgramHelperTest {

    private ProgramHelper programHelper;

    @Before
    public void setUp() {
        programHelper = new ProgramHelper("src/test/resources/testFiles.txt");
    }

    @Test
    public void getFiles() throws Exception {

        ArrayList<String> list = programHelper.getFiles();

        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
        assertEquals("test3", list.get(2));
    }

    @Test
    public void options1Menu() throws Exception {

        String returnString = programHelper.options1Menu();

        assertEquals("------------------------------------------------------------------------------------------------------------\n" +
                "### Choose your option by enter these numbers ###\n" +
                "1      Create database \n" +
                "2      Create tables \n" +
                "3      Insert data into tables \n" +
                "4      Connect tables (Required if you want to connect tables)\n" +
                "### Type 'queries' to make queries or type 'exit' to exit program ###\n" +
                "\n" +
                "Your choice: ", returnString);
    }

    @Test
    public void options2Menu() throws Exception {

        String returnString = programHelper.options2Menu();

        assertEquals("-----------------------------------------------------------------------------------------------------------\n" +
                "### Choose your query option by enter these numbers ###\n" +
                "1      Show all tables \n" +
                "2      Get metadata from table \n" +
                "3      Get all content in table \n" +
                "4      Find any result based on any name, any column from any table \n" +
                "5      Find all teachers availabilities on day (?) at week(?)\n" +
                "6      Find which subjects a lecturer have\n" +
                "### Type 'exit' to exit the program ###\n" +
                "\n" +
                "Your choice: ", returnString);
    }

}