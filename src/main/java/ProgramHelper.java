import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * This is a helper to Application.class, i created this so it becomes more readable in the Application class
 * and also adding the files that needed to be added to run the program
 *
 * Last modified 11 october 2017
 *
 */

public class ProgramHelper {

    private ArrayList<String> files = new ArrayList<>();
    private Scanner read;


    /**
     * Constructor
     * Set the file that needed to be read
     */
    public ProgramHelper(String fileName) {
        readFiles(fileName);
    }

    /**
     * Read the files.txt in 'helpMaterials', this file contain name of the all files that need to be read to create tables and add data
     * @param fileName
     * @return files
     */
    private ArrayList<String> readFiles(String fileName)
    {
        try
        {
            read = new Scanner(new File(fileName));

            while (read.hasNextLine()) {
                String file = read.nextLine();
                files.add(file);
            }
        }
        catch (FileNotFoundException f)
        {
            System.out.println("### Check if there is a files.txt file in the folder 'helpMaterials' ###");
            System.out.println("This file is needed to add files to run the program ###");
        }
        return files;
    }

    /**
     * Method to i can access this in Program.class to get the file that contain all files needed to be read
     * @return files
     */
    public ArrayList<String> getFiles() {
        return files;
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
