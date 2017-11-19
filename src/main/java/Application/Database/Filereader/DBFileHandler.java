package Application.Database.Filereader;

import Application.Database.TableObject.DBTableObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * <p>DBFileHandler class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * DBFileReader
 * Class for reading files and retrieving data from files as well as validating data
 * And also send data to DBTableObjectFromFile.class to create an object of information
 *
 * source ->  https://github.com/NikitaZhevnitskiy/dbService/blob/master/src/main/java/program/InputManager.java
 *
 * <p>
 * Last modified 18 november 2017
 */
public class DBFileHandler {

    private Scanner readFile;
    private boolean checkStatusOfValidationOfFile;


    /**
     * The method opens file, read file and puts everything in an arraylist named 'allFromFile'
     * Validating that data
     * Then extract the metadata from arraylist(allFromFile), and send to DBTableObject.class, so i can create an object which is used to create table in database
     * Then extracts only data to new arraylist named 'justDataWithoutMetadata'
     *
     * Set read = null when finish read the file
     *
     * @return table object
     * @param fileName a {@link java.lang.String} object.
     * @param table a {@link Application.Database.TableObject.DBTableObject} object.
     */
    public DBTableObject makeObject(String fileName, DBTableObject table)
    {
        readFile(fileName);
        ArrayList<String> allFromFile = getAllDataFromFile();
        // Validate before send information to DbTableObject
        validateMetadata(allFromFile);
        table.setTableName(allFromFile.get(0));
        table.setColumnsName(allFromFile.get(1).split("/"));
        table.setDataTypes(allFromFile.get(2).split("/"));
        table.setPrimaryKey(allFromFile.get(3).split("/"));
        table.setSeperatorMetaDataAndData(allFromFile.get(4));
        table.setJustDataWithoutMetaData(getOnlyDataWithoutMetaData(allFromFile));
        this.readFile = null;
        return table;
    }


    /**
     * The method opens and reads a file called in the makeObject() method
     *
     * @param fileName, the file to be read
     */
    private void readFile(String fileName)
    {
        try
        {
            readFile = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException f)
        {
            System.out.println("### Some file(s) not found ###");
        }
    }


    /**
     * This method is called in the makeObject() method that reads everything in the file to an arraylist named 'allFromFile'
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<String> getAllDataFromFile()
    {

        ArrayList<String> allFromFile = new ArrayList<>();

        while (readFile.hasNext())
        {
            allFromFile.add(readFile.nextLine());
        }
        return allFromFile;
    }


    /**
     * This method create an arraylist with array of strings named 'justDataWithoutMetaData' and extract it from the arraylist(allFromFile) from 5'th index
     *
     * @param allFromFile arraylist
     *
     * @return the arraylist 'justDataWithoutMetaData'
     */
    private ArrayList<String[]> getOnlyDataWithoutMetaData(ArrayList<String> allFromFile)
    {
        ArrayList<String[]> justDataWithoutMetaData = new ArrayList<>();
        for (int i = 5; i < allFromFile.size(); i++)
        {
            String[] lines = allFromFile.get(i).split("/");

            justDataWithoutMetaData.add(lines);

        }
        return justDataWithoutMetaData;
    }


    /**
     * If validation went wrong, send a message to the user that the file is empty or incorrect
     *
     * @param list, sending the arraylist(allFromFile) to validate the data
     */
    private void validateMetadata(ArrayList<String> list)
    {
        if (!isMetadataValid(list))
        {
            System.out.println("### File is empty or not valid ###");
        }
    }


    /**
     * Here the validation checks that the file is not empty or the layout of the file is correct.
     * Checks from the first line to the 5 line, if file is right then create object, otherwise cant not create a table object
     *
     * @param list, the arraylist(allFromFile) where all content is stored
     *
     * @return if file is correct or not by true/false
     */
    private boolean isMetadataValid(ArrayList<String> list)
    {
        // List should not be empty or null
        if (list.isEmpty() || list == null)
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        String firstLineTableName = list.get(0);
        String[] secondLineColumname = list.get(1).split("/");
        String[] thirdLineDatatypes = list.get(2).split("/");
        String[] fourthLinePrimaryKey = list.get(3).split("/");
        String fifthLineSperator = list.get(4);

        // Table name should not be empty
        if (firstLineTableName.length() == 0 || firstLineTableName.isEmpty() || firstLineTableName.equals("") || firstLineTableName == null)
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        // Check if column length is same as data type length, and data type length is same as column name length
        if ((secondLineColumname.length != list.get(2).split("/").length) && (thirdLineDatatypes.length != list.get(
                1).split("/").length))
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        // Secondline and thirdline should not be empty
        if(secondLineColumname.length == 0 && thirdLineDatatypes.length == 0) {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        // Check if primary key is not empty, every table should have declared a primary key
        if(fourthLinePrimaryKey.length == 0) {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        // Fifthline should not be empty, this is because it should be a seperator between data and metadata
        if (fifthLineSperator.length() == 0 || fifthLineSperator.isEmpty() || fifthLineSperator.equals("") || fifthLineSperator == null)
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        // Check if primarykey is equals to one of columnName
        List columnnames = Arrays.asList(secondLineColumname);
        List primaryKeys = Arrays.asList(fourthLinePrimaryKey);
        if(columnnames.contains(primaryKeys)) {
            checkStatusOfValidationOfFile = true;
            return true;
        }

        // If everything is fine then true for validation of file
        checkStatusOfValidationOfFile = true;
        return true;
    }

    // TODO: Validate all rows length with column name length, also handle if there is auto increment


    /**
     * Here I get the value if the validation went well or not, as used in Program.class where I create tables
     *
     * @return status of validation of the file
     */
    public boolean isCheckStatusOfValidationOfFile() {

        return checkStatusOfValidationOfFile;
    }
}
