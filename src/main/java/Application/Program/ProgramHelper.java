package Application.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * <p>ProgramHelper class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * This is a helper to Program.class,
 * and also adding the files that needed to be added (the files to read from 'inputFiles' folder
 * <p>
 * Last modified 10 november 2017
 */
public class ProgramHelper {

    private ArrayList<String> files = new ArrayList<>();
    private Scanner read;


    /**
     * Constructor
     * Set the file that needed to be read
     *
     * @param fileName a {@link java.lang.String} object.
     */
    public ProgramHelper(String fileName) throws FileNotFoundException {

        readFiles(fileName);
    }


    /**
     * Read the files.txt in 'helpMaterials', this file contain name of the all files that need to be read to create tables and add data
     *
     * @param fileName, send the file path containing all the names of the files to be read
     *
     * @return file list
     */
    private void readFiles(String fileName) throws FileNotFoundException
    {
            read = new Scanner(new File(fileName));

            while (read.hasNextLine())
            {
                String file = read.nextLine();
                files.add(file);
            }
    }

    /**
     * Method to i can access this in Program.class to get the file that contain all files needed to be read
     *
     * @return files
     */
    public ArrayList<String> getFiles() {

        return files;
    }


    // Menu for while loop one
    /**
     * <p>options1Menu.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String options1Menu() {
        String options1menu =
                "------------------------------------------------------------------------------------------------------------\n" +
                        "### Choose your option by enter these numbers ###\n" +
                        "1      Create database \n" +
                        "2      Create tables \n" +
                        "3      Insert data into tables \n" +
                        "4      Connect tables (Required if you want to connect tables)\n" +
                        "### Type 'queries' to make queries or type 'exit' to exit program ###\n" +
                        "\nYour choice: ";

        return options1menu;
    }

    // Menu for while loop two
    /**
     * <p>options2Menu.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String options2Menu() {
        String options2menu =
                "-----------------------------------------------------------------------------------------------------------\n" +
                        "### Choose your query option by enter these numbers ###\n" +
                        "1      Show all tables \n" +
                        "2      Get metadata from table \n" +
                        "3      Get all content in table \n" +
                        "4      Find any result based on any name, any column from any table \n" +
                        "5      Find all teachers availabilities on day (?) at week(?)\n" +
                        "6      Find which subjects a lecturer have\n" +
                        "7      Find data about specific teacher with use of PreparedStatement in right way\n" +
                        "8      Find data about specific subject with use of PreparedStatement in right way\n" +
                        "### Type 'exit' to exit the program ###\n" +
                        "\nYour choice: ";
        return options2menu;
    }


    // Just for fancy loading
    /**
     * <p>connectionLoader.</p>
     *
     * @throws java.lang.InterruptedException if any.
     */
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
