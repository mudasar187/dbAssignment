package Program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Class for reading of the files from inPutFiles package
 * and handle metadata and data from files
 *
 * Last modified 04 october 2017
 *
 */

public class DBFileHandler {

    private String tableName;
    private String[] columnNames;
    private String[] dataTypes;
    private String primaryKey;

    // ArrayList for adding the data into arraylist and send them to insertDataIntoTable() method in DBHandler
    private ArrayList<String> list;


    /**
     * Getter for the fields above
     *
     * @return tableName, length of columnsNames, each columnsName[i], each dataTypes[i], primaryKey, length of list, each element in list(i)
     */

    public String getTableName() { return tableName; }

    public int getLengthOfColumns() { return columnNames.length; }

    public String getColumns(int i) {
        return columnNames[i];
    }

    public String getDataTypes(int i) {
        return dataTypes[i];
    }

    public int getLengthOfDatatypes() { return dataTypes.length; }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public ArrayList<String> getListSize() {
        return list;
    }

    public String getListIndex(int i) {
        return list.get(i);
    }


    /**
     * Read files
     *
     * @param fileName
     */
    public boolean readFile(String fileName) {

        Scanner read = null;

        try {
            list = new ArrayList<>();

            read = new Scanner(new FileReader(fileName));

            // Reading the first 4 lines as metadata to create the tables
            tableName = read.nextLine().toLowerCase();
            columnNames = read.nextLine().split("/");
            dataTypes = read.nextLine().split("/");
            primaryKey = read.nextLine();

            // Jump over the the 5'th line
            read.nextLine();

            // Start from the 6'th line
            while (read.hasNext()) {
                // Read the data and add them into arraylist then split lines in own method named splitLinesInFile() in DBHandler
                list.add(read.nextLine());
            }

            read.close();

        }
        catch (FileNotFoundException f)
        {
            // f.printStackTrace();
            System.out.println("\n### File not found ###");
            return false;
        }
        catch (NullPointerException np)
        {
            System.out.println("\n### File not found ###");
            return false;
        }
        catch (NoSuchElementException n)
        {
            System.out.println("\n### Nothing to read in file ###");
            return false;
        }
        return true;
    }

    /**
     * Split the lines after reading from the file
     * This method splits the lines from the input to be inserted into insertDataIntoTable () in DBHandler class,
     * so if you want to distinguish with other characters,
     * you must change "/" in this method and in the readFile ()
     */
    public String[][] splitLinesInFile()
    {
        String[][] objectsFromFile = new String[getListSize().size()][getLengthOfColumns()];
        for (int i = 0; i < getListSize().size(); i++)
        {
            objectsFromFile[i] = getListIndex(i).split("/");
        }
        return objectsFromFile;
    }

}