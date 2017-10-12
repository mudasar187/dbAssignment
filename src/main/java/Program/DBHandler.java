package Program;

import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Class for handling tables and queries
 *
 * Last modified 04 october 2017
 *
 */

public class DBHandler {

    private DBConnection dbConnection;
    private DBFileHandler dbFileHandler;

    // Creating this variable to keep where i want to start if i have an AUTO_INCREMENT in my first index in dataTypes[0]
    private int startFrom = 0;

    // Creating scanner so user can send in input to terminal
    private Scanner userInput = new Scanner(System.in);

    // Return value from checkIfDataTypeIndex0HaveTheWordAutoIncrement() method
    private int getStartFrom() { return startFrom; }



    /**
     * Constructor
     * All dependency injections in constructor
     */
    public DBHandler(DBConnection dbConnection, DBFileHandler dbFileHandler)
    {
        this.dbConnection = dbConnection;
        this.dbFileHandler = dbFileHandler;
    }


    /**
     * Get the method of readFile() in DBFileHandler
     */
    public boolean getDbFileHandler(String fileName)
    {
        if(dbFileHandler.readFile(fileName) == true) {
            return true;
        }
        return false;
    }


    /**
     * Checks if connection is valid or not
     * Have this method so I can launch the application in the Application class if connection is valid,
     * if it is not valid then the program does not run
     * @return true if connection is valid, return false if connection is not valid
     */
    public boolean isConnected() {
        if(dbConnection.getConnection() != null)
        {
            return true;
        }
        return false;
    }


    /**
     * Creating the database name based on properties file
     * Here, the database name is created based on the user selected in the properties file,
     * but also handles if the name exists from the previous database
     */
    public void createDataBase()
    {

        String createDBName = "CREATE DATABASE " + dbConnection.getDbName();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement createDbName = connection.prepareStatement(createDBName))
        {
            createDbName.executeUpdate();
            System.out.println("### Database created successfully ###\n");

        }
        catch (SQLException se)
        {
            SQLException(se.getErrorCode());
        }
    }


    /**
     * Create new database or overwrite exists database
     * The user enters this method if the database name exists,
     * then the user gets 2 choices either to create a new name or overwrite the existing database
     */
    private void createNewDatabaseOrOverwriteIfExists()
    {
        System.out.print(
                "### Enter 'new' for creating new name or 'overwrite' to overwrite the exist database: ");
        String userOption = userInput.next().toLowerCase();

        if(userOption.equals("new"))
        {
            System.out.print("### Enter new name for the database: ");
            String newDbName = userInput.next();
            dbConnection.setDbName(newDbName);
            createDataBase();
        }
        else if(userOption.equals("overwrite"))
        {
            String dropDatabase = "DROP DATABASE " + dbConnection.getDbName();
            String createDatabase = "CREATE DATABASE " + dbConnection.getDbName();

            try(Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(dropDatabase);
                PreparedStatement preparedStatement1 = connection.prepareStatement(createDatabase))
            {
                preparedStatement.executeUpdate();
                preparedStatement1.executeUpdate();
                System.out.println("\n### Overwriting your exists database ###\n");
            }
            catch (SQLException se)
            {
                System.out.println("### Please check your database name, only letters are approved ###");
                createNewDatabaseOrOverwriteIfExists();
            }
        }
        else
        {
            System.out.print("### Not valid option, enter 'new' or 'overwrite' only: ");
            createNewDatabaseOrOverwriteIfExists();
        }
    }


    /**
     * Create table
     * This method creates the tables based on the meta data in the file,
     * the first 3 lines are meta data needed to create the tables as long as the user follows the instructions for how the file should be set up,
     * this method will create your tables anyway
     */
    public void createTable()
    {
        try
        {
            {
                String chooseDBName = "USE " + dbConnection.getDbName();
                String createTable = "CREATE TABLE " + dbFileHandler.getTableName() + " (";

                for (int i = 0; i < dbFileHandler.getLengthOfColumns(); i++)
                {
                    createTable += dbFileHandler.getColumns(i) + " " + dbFileHandler.getDataTypes(i) + ",";
                }
                createTable += "PRIMARY KEY(" + dbFileHandler.getPrimaryKey() + "));";

                try (Connection connection = dbConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
                     PreparedStatement preparedStatement2 = connection.prepareStatement(createTable))
                {
                    preparedStatement.executeUpdate();
                    preparedStatement2.executeUpdate();

                    System.out.println(
                            "### Table " + "'" + dbFileHandler.getTableName() + "'" + " created succsessfully ###");
                }
                catch (SQLException se)
                {
                    SQLException(se.getErrorCode());
                }
            }
        } catch (Exception e) {
            System.out.println("### Cannot create table ###");
        }
    }


    /**
     * Insert data
     * This method enters data from the file, here is a danger to SQL Injection, referring to this link, how to actually do it ->
     * https://www.owasp.org/index.php/SQL_Injection_Prevention_Cheat_Sheet
     */
    public void insertDataIntoTable()
    {
        String chooseDBName = "USE " + dbConnection.getDbName();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(""))
        {
            // Check if dataType[0] is auto_increment or not
            checkIfDataTypeIndex0HaveTheWordAutoIncrement();

            String insertData = "INSERT INTO " + dbFileHandler.getTableName() + " (";

            // Here i call for the getStartFrom(), if its auto_increment in index 0, then start from 1, and if its not, start from 0
            for (int i = getStartFrom(); i < dbFileHandler.getLengthOfColumns(); i++)
            {
                insertData += dbFileHandler.getColumns(i);
                if (i < dbFileHandler.getLengthOfColumns() -1) insertData += ", ";
            }
            insertData += ") VALUES ";

            String[][] lines = dbFileHandler.splitLinesInFile();

            for (int i = 0; i < lines.length; i++)
            {
                insertData += "(";
                for (int j = 0; j < lines[0].length; j++)
                {
                    insertData += "'" + lines[i][j] + "'";
                    if (j < lines[0].length - 1) insertData += ", ";
                }
                if (i < lines.length - 1) insertData += "), ";
            }

            insertData += ");";

            preparedStatement.executeUpdate();

            int result = preparedStatement1.executeUpdate(insertData);

            System.out.println("### " + result + " rows are inserted into table " +"'"+ dbFileHandler.getTableName() +"'");
        }
        catch (SQLException se)
        {
            SQLException(se.getErrorCode());
        } catch (NullPointerException ne)
        {
            // ne.printStackTrace();
        }
    }


    /**
     * Empty table
     * This method is designed to delete all content in the table without deleting the table,
     * this allows users to update only in the file, whether the user wishes to enter more rows,
     * or delete any rows. User can also update the rows and when running the program,
     * you can choose to delete everything earlier and update with new content.
     */
    public void truncateTable(String tableName) {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String truncateTable = "TRUNCATE " + tableName;

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(truncateTable))
        {
            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();
            System.out.println("### Table "+tableName+" refreshed, you can now insert data ###");

        } catch (SQLException se) {
            System.out.println("### Table not exists ###");
        }
    }


    /**
     * Drop table
     * This method deletes the tables, if you want to add other tables or update table meta data,
     * you can do that by dropping the tables and creating others as long as you follow the instructions in the Readme file
     * for how the setup should be for the program to run correctly.
     */
    public void dropTable(String tableName) {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String dropTable = "DROP TABLE " + tableName;

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(dropTable))
        {
            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();

            System.out.println("### Table " + tableName + " is dropped ###");

        } catch (SQLException se)
        {
            SQLException(se.getErrorCode());
        }
    }


    /**
     * Check for auto_increment
     * This method ensures that if you have chosen to set the first column as auto increment,
     * this method will handle the input of the insertDataIntoTable () method.
     * Then, this jump over the first column that is set to auto_increment and make sure it does not make any trouble to enter the data
     */
    private void checkIfDataTypeIndex0HaveTheWordAutoIncrement()
    {
        try
        {
            String dataTypeIndex0 = dbFileHandler.getDataTypes(0);
            String auto_increment = "AUTO_INCREMENT";

            if (dataTypeIndex0.toLowerCase().indexOf(auto_increment.toLowerCase()) != -1)
            {
                startFrom = 1;
            } else
            {
                startFrom = 0;
            }
        } catch (NullPointerException ne)
        {
            // ne.printStackTrace();
        }
    }


//    /**
//     * Split the lines after reading from the file
//     * This method splits the lines from the input to be inserted into insertDataIntoTable () in DBHandler class,
//     * so if you want to distinguish with other characters,
//     * you must change "/" in this method and in the readFile ()
//     */
//    private String[][] splitLinesInFile()
//    {
//        String[][] objectsFromFile = new String[dbFileHandler.getListSize().size()][dbFileHandler.getLengthOfColumns()];
//        for (int i = 0; i < dbFileHandler.getListSize().size(); i++)
//        {
//            objectsFromFile[i] = dbFileHandler.getListIndex(i).split("/");
//        }
//        return objectsFromFile;
//    }


    /**
     * Metadata from tables
     * This method extracts meta data about the tables, column names, data types and size
     */
    public void getMetaDataFromTable(String tableName)
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = "SELECT * FROM " + tableName;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int rowCount = resultSetMetaData.getColumnCount();

            System.out.printf("\n"+"%-15s %-10s %-10s","Field", "Size", "DataType\n");

            for (int i = 0; i < rowCount; i++)
            {
                System.out.printf("\n%-15s %-10s %-20s",
                        resultSetMetaData.getColumnName(i+1),
                        resultSetMetaData.getColumnDisplaySize(i+1),
                        resultSetMetaData.getColumnTypeName(i+1));
            }
            System.out.println("\n------------------------------------");
        }
        catch (SQLException se)
        {
            // se.printStackTrace();
            SQLException(se.getErrorCode());
        }
    }

    /**
     * Data from tables
     * This method extracts all the data in the table and calls the printResult () method to print the result
     */
    public String getDataFromTable(String tableName)
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = "SELECT * FROM " + tableName;

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return printResult(resultSet);

        }
        catch (SQLException se)
        {
            // se.printStackTrace();
            return "### Table not exists ###";
        }
    }


    /**
     * Number of rows in table
     * This method retrieves the number of rows in a table and calls the printResult () method to print the result
     */
    public String getCountRowsFromTable(String tableName)
    {
        String chooseDbName = "USE " + dbConnection.getDbName();
        String selectCountRowFromTable = "SELECT COUNT(*) as 'Number of rows' FROM " + tableName;

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDbName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(selectCountRowFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return printResult(resultSet);

        }
        catch (SQLException se)
        {
            // se.printStackTrace();
            return "### Table not exists ###";
        }
    }


    /**
     * Give the value of the word you search for based on tableName, column
     * This method allows you to search whatever you want, no matter what table,
     * you must define the table name you want to search in, column and the word you are searching for
     */
    public String getAnyValueFromAnyTable(String tableName, String column, String search)
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = "SELECT * FROM " + tableName + " WHERE " + column + " LIKE " + "'"+search+"'";

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return printResult(resultSet);

        }
        catch (SQLException se)
        {
            // se.printStackTrace();
            return "### Error creating query ###";
        }
    }


    /**
     * This method extracts the column name in the tables
     */
    public void getColumnNamesInTable(String tableName)
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = "SELECT * FROM " + tableName;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int rowCount = resultSetMetaData.getColumnCount();

            System.out.println("\n------------------------------------------------------------------");
            for (int i = 0; i < rowCount; i++)
            {
                System.out.printf("%-15s", resultSetMetaData.getColumnName(i+1));
            }
            System.out.println("\n------------------------------------------------------------------");
        }
        catch (SQLException se)
        {
            // se.printStackTrace();
            SQLException(se.getErrorCode());
        }
    }


    /**
     * Show all table names in database
     *
     */
    public String showAllTables()
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String showTables = "SHOW tables";

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(showTables))

        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return printResult(resultSet);
        }
        catch (SQLException se)
        {
            // se.printStackTrace();
            return "### Error showing tables ###\n";
        }
    }


    /**
     * Printing out result from these methods:
     * getDataFromTables()
     * getCountRowsFromTable()
     * getAnyValueFromAnyTable()
     * showAllTables()
     */
    private String printResult(ResultSet resultSet)
    {
        try
        {
            String output = "";
            int rowCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 0; i < rowCount; i++)
                {
                    String columnValue = resultSet.getString(i + 1);
                    output += String.format("%-20s", columnValue);
                }

                output += "\n";

            }
            if (output.length() == 0) return "### No data in table or no matching value ###";
            return output;
        }
        catch (SQLException se)
        {
            return "### Error creating print ###";
            // se.printStackTrace();
        }
    }


//    /**
//     * TODO: Method for connecting the tables, simple query, can make it out dynamic
//     * 'lecturerandsubject' ,
//     * 'subjectandprogram',
//     * 'subjectandroom'
//     */
//     public void connectTables()
//     {
//         String chooseDBName = "USE " + dbConnection.getDbName();
//
//         String lecturerandsubject = "ALTER TABLE `lecturerandsubject`\n" +
//                 "  ADD CONSTRAINT `fk_lecturerId_id` FOREIGN KEY (`lecturerId`) REFERENCES `lecturer` (`id`),\n" +
//                 "  ADD CONSTRAINT `fk_subjectId_id` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`id`)";
//
//         String subjectandprogram = "ALTER TABLE `subjectandprogram`\n" +
//                 "  ADD CONSTRAINT `subjectandprogram___fk_subjectAndProg_program` FOREIGN KEY (`programId`) REFERENCES `program` (`id`),\n" +
//                 "  ADD CONSTRAINT `subjectandprogram___fk_subjectAndProg_subject` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`id`)";
//
//         String subjectandroom = "ALTER TABLE `subjectandroom`\n" +
//                 "  ADD CONSTRAINT `subjectandroom___fk_rook` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`),\n" +
//                 "  ADD CONSTRAINT `subjectandroom___fk_subject` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`id`)";
//
//         try(Connection connection = dbConnection.getConnection();
//         PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
//         PreparedStatement preparedStatement1 = connection.prepareStatement(lecturerandsubject);
//         PreparedStatement preparedStatement2 = connection.prepareStatement(subjectandprogram);
//         PreparedStatement preparedStatement3 = connection.prepareStatement(subjectandroom))
//         {
//
//             preparedStatement.executeUpdate();
//             preparedStatement1.executeUpdate();
//             preparedStatement2.executeUpdate();
//             preparedStatement3.executeUpdate();
//
//             System.out.println("### Tables "+lecturerandsubject+", "+subjectandprogram+", "+subjectandprogram+" are also created ### ");
//         } catch (SQLException se)
//         {
//             se.printStackTrace();
//         }
//     }

    /**
     * Handling SQLExceptions
     */
    public void SQLException(int se)
    {

        switch (se) {
            case 1064:
                System.out.println("### Please check your database name, only letters are approved ###");
                createNewDatabaseOrOverwriteIfExists();
                break;
            case 1007:
                System.out.println("### Database name exist ###");
                createNewDatabaseOrOverwriteIfExists();
                break;
            case 1136:
                System.out.println("### Column count doesn't match row count, please check file ###");
                break;
            case 1146:
                System.out.println("### Table not exists ###");
                break;
            case 1046:
                System.out.println("### No database exist ###");
                break;
            case 1049:
                System.out.println("### No database exists ###");
                break;
            case 1050:
                System.out.println("### Table exists ###");
                break;
            case 1051:
                System.out.println("Table not exists ###");
                break;
            case 1062:
                System.out.println("### Duplicates entry ###");
                System.out.println("### Refresh your table before inserting data into table");
                break;

        }
    }



    // Just for fancy loading
    public void connectionLoader() throws InterruptedException {
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
