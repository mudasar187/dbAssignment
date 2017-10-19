package Application.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
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
     * The method opens a file, reads the file and puts everything in an arraylist = allFromFile, and validating that data
     * Then extract the metadata from arraylist = allFromFile, and send to DBTableObject.class, so i can create an object which is used to create table in database
     * Then he extracts only data to his own arraylist = justDataWithoutMetadata(),
     * where i put arraylist = allFromFile into the parameter
     * which is used to input content in table
     *
     * @param fileName, the file to be read
     * @param table, object to be created
     *
     * @return table object
     */
    public DBTableObject makeTable(String fileName, DBTableObject table)
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
     * The method opens and reads a file called in the makeTable() method
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
     * The method is called in the makeTable() method that reads everything in the file to an arraylist = allFromFile
     *
     * @return allFromFile, the arraylist with content of everything in the file
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
     * Here I submit the arraylist = allFromFile into parameter so that this arraylist is used to retrieve ONLY data
     * to be added to the tables, I create a new arraylist = justDataWithoutMetaData and extract all the data in
     * arraylist = allFromFile from index 5 and so on into arraylist = justDataWithoutMetaData
     *
     * @param allFromFile, the arraylist
     *
     * @return the arraylist with only metadata
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
     * @param list, sending the arraylist = allFromFile to validate the data
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
     * See the readme file to how the set up should be
     *
     * @param list, the arraylist = allFromFile where all content is stored
     *
     * @return boolean status
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
     * Here I get the value if the validation went well or not, as used in Application.class where I create tables
     *
     * @return status
     */
    public boolean isCheckStatusOfValidationOfFile() {

        return checkStatusOfValidationOfFile;
    }
}