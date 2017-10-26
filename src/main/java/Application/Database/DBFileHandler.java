package Application.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
 * <p>
 * Last modified 18 october 2017
 */
public class DBFileHandler {

    private Scanner read;
    private boolean checkStatusOfValidationOfFile;


    /**
     * The method opens file, read file and puts everything in an arraylist named 'allFromFile'
     * Validating that data
     * Then extract the metadata from arraylist(allFromFile), and send to DBTableObject.class, so i can create an object which is used to create table in database
     * Then extracts only data to new arraylist named 'justDataWithoutMetadata'
     *
     * @return table object
     * @param fileName a {@link java.lang.String} object.
     * @param table a {@link Application.Database.DBTableObject} object.
     */
    public DBTableObject makeObject(String fileName, DBTableObject table)
    {

        setRead(fileName);
        ArrayList<String> allFromFile = getAllDataFromFile();
        validateData(allFromFile);
        table.setTableName(allFromFile.get(0));
        table.setColumnsName(allFromFile.get(1).split("/"));
        table.setDataTypes(allFromFile.get(2).split("/"));
        table.setPrimaryKey(allFromFile.get(3));
        table.setSeperatorMetaDataAndData(allFromFile.get(4));
        table.setJustDataWithoutMetaData(getOnlyDataWithoutMetaData(allFromFile));
        setReadToNull();
        return table;
    }


    /**
     * The method opens and reads a file called in the makeObject() method
     *
     * @param fileName, the file to be read
     */
    private void setRead(String fileName)
    {

        try
        {
            read = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException f)
        {
            System.out.println("### File not found ###");
        }
    }


    /**
     * Set read to null
     */
    private void setReadToNull() {

        this.read = null;
    }


    /**
     * This method is called in the makeObject() method that reads everything in the file to an arraylist named 'allFromFile'
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<String> getAllDataFromFile()
    {

        ArrayList<String> allFromFile = new ArrayList<>();

        while (read.hasNext())
        {
            allFromFile.add(read.nextLine());
        }
        return allFromFile;
    }


    /**
     * This method create an arraylist named 'justDataWithoutMetaData' and extract it from the arraylist(allFromFile) from 5'th index
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
    private void validateData(ArrayList<String> list)
    {

        if (!isDataValid(list))
        {
            System.out.println("### File is empty or not valid ###");
        }
    }


    /**
     * Here the validation checks that the file is not empty or the layout of the file is correct.
     * Checks from the first line to the 5 line, if it is the way it should, otherwise the program can not create a table object
     *
     * @param list, the arraylist(allFromFile) where all content is stored
     *
     * @return if file is correct or not by true/false
     */
    private boolean isDataValid(ArrayList<String> list)
    {

        if (list.isEmpty())
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        String firstLineTableName = list.get(0);
        String[] secondLineColumname = list.get(1).split("/");
        String[] thirdLineDatatypes = list.get(2).split("/");
        String fourthLinePrimaryKey = list.get(3);
        String fifthLineSperator = list.get(4);

        if ((firstLineTableName.length() == 0) && fourthLinePrimaryKey.length() == 0)
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        if ((secondLineColumname.length != list.get(2).split("/").length) && (thirdLineDatatypes.length != list.get(
                1).split("/").length))
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        if (fifthLineSperator.length() == 0)
        {
            checkStatusOfValidationOfFile = false;
            return false;
        }

        checkStatusOfValidationOfFile = true;
        return true;
    }


    /**
     * Here I get the value if the validation went well or not, as used in Program.class where I create tables
     *
     * @return status of validation of the file
     */
    public boolean isCheckStatusOfValidationOfFile() {

        return checkStatusOfValidationOfFile;
    }
}
