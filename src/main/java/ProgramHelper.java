import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * This is a helper to Application.class, i created this so it becomes more readable in the Application class
 *
 * Last modified 11 october 2017
 *
 */

public class ProgramHelper {

    private String [] listOfFiles = new String[6];

    public ProgramHelper() {
        setListOfFiles();
    }

    public String[] setListOfFiles() {
        listOfFiles[0] = "lecturer";
        listOfFiles[1] = "room";
        listOfFiles[2] = "subject";
        listOfFiles[3] = "semester";
        listOfFiles[4] = "program";
        listOfFiles[5] = "availability";
        return listOfFiles;
    }

    public String[] getListOfFiles() {

        return listOfFiles;
    }

    // Scanner for getting the input from user
    Scanner userInput = new Scanner(System.in);

    // These are options you can make in first while loop, options3 is showed up first because its recommended to run these first
    // When you are finished with options3 when you will jump to options3, second while loop to make queries
    String options1menu =
            "--------------------------------------------------------------------------------------\n" +
                    "### Choose your option by enter these numbers ###\n" +
                    "1      Create database \n" +
                    "2      Create tables \n" +
                    "3      Drop tables \n" +
                    "4      Insert data into tables \n" +
                    "5      Refresh tables (Empty data in tables) \n" +
                    "### Type 'queries' to make queries or type 'exit' to exit program ###\n" +
                    "\nYour choice: ";


    String options2menu =
            "--------------------------------------------------------------------------------------\n" +
                    "### Choose your query option by enter these numbers ###\n" +
                    "1      Show all tables \n" +
                    "2      Get metadata from table \n" +
                    "3      Get data information from table \n" +
                    "4      Find number of rows in table \n" +
                    "5      Find any result based on any name, any column from any table \n" +
                    "### Type 'exit' to exit the program ###\n" +
                    "\nYour choice: ";


    // Holding the variable for options 1
    String options1;
    // Holding the variable for options 2
    String options2;

    // Creating a boolean for the first while loop
    boolean stopWhileLoopOne = false;
    // Creating a boolean for the second while loop
    boolean stopWhileLoopTwo = false;


    // Just for fancy loading
    public void connectionLoader() throws InterruptedException
    {
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.print(".");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.print(".");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.print(".");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.print(".");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println("\n");
    }
}
