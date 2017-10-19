package Application.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * This is a helper to Program.class, i created this so it becomes more readable in the Program.class
 * and also adding the files that needed to be added (the files to read from 'inputFiles' folder
 * <p>
 * Last modified 19 october 2017
 */

public class ProgramHelper {

    private ArrayList<String> files = new ArrayList<>();
    private Scanner read;


    /**
     * Constructor
     * Set the file that needed to be read
     *
     * @param fileName , send the file path containing all the names of the files to be read
     */
    public ProgramHelper(String fileName) {

        readFiles(fileName);
    }


    /**
     * Read the files.txt in 'helpMaterials', this file contain name of the all files that need to be read to create tables and add data
     *
     * @param fileName, send the file path containing all the names of the files to be read
     *
     * @return file list
     */
    private ArrayList<String> readFiles(String fileName)
    {

        try
        {
            read = new Scanner(new File(fileName));

            while (read.hasNextLine())
            {
                String file = read.nextLine();
                files.add(file);
            }
        }
        catch (FileNotFoundException f)
        {
            System.out.println("### Check if there is a files.txt file in the folder 'helpMaterials' ###");
            System.out.println("### This file is needed to add content of files to run the program ###");
        }
        return files;
    }

    /**
     * Method to i can access this in Program.class to get the file that contain all files needed to be read
     *
     * @return files
     */
    public ArrayList<String> getFiles() {

        return files;
    }


    // Scanner for getting the input from user
    public Scanner userInput = new Scanner(System.in);


    // These are options you can make in Program.class, options1menu is showed up first because its recommended to run these first
    // When you are finished with options1menu, you will jump to options2menu, second while loop to make queries
    public String options1menu =
            "--------------------------------------------------------------------------------------\n" +
                    "### Choose your option by enter these numbers ###\n" +
                    "1      Create database \n" +
                    "2      Create tables \n" +
                    "3      Drop tables \n" +
                    "4      Insert data into tables \n" +
                    "### Type 'queries' to make queries or type 'exit' to exit program ###\n" +
                    "\nYour choice: ";


    public String options2menu =
            "--------------------------------------------------------------------------------------\n" +
                    "### Choose your query option by enter these numbers ###\n" +
                    "1      Show all tables \n" +
                    "2      Get metadata from table \n" +
                    "3      Get content in table \n" +
                    "4      Find number of rows in table \n" +
                    "5      Find any result based on any name, any column from any table \n" +
                    "### Type 'exit' to exit the program ###\n" +
                    "\nYour choice: ";


    // Holding the variable for options 1
    public String options1;
    // Holding the variable for options 2
    public String options2;


    // Creating a boolean for the first while loop
    public boolean stopWhileLoopOne = false;
    // Creating a boolean for the second while loop
    public boolean stopWhileLoopTwo = false;


    // Check if you have created database and tables before entry options2menu
    public int createdTables = 0;


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
