import Program.DBConnection;
import Program.DBFileHandler;
import Program.DBHandler;
import Program.DBTable;
import Program.ExceptionHandling;

import java.sql.SQLException;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Main class to run application with help from ProgramHelper.class
 *
 * Last modified 11 october 2017
 *
 */

public class Program {

    public static void main(String[] args) throws InterruptedException {

        // Creating new instance of DBHandler and DBFileReader to run the application
        // DBHandler is injected with constructor from DBConnection
        DBHandler dbHandler = new DBHandler(
                new DBConnection("src/main/resources/database.properties"));
        DBFileHandler dbFileHandler = new DBFileHandler();
        ExceptionHandling exceptionHandling = new ExceptionHandling();
        ProgramHelper programHelper = new ProgramHelper("src/main/java/helpMaterials/files.txt");


        // Checks if connection is connected, if yes run while loop, if not, throw message that connection is not connected
        if (dbHandler.getDBStatus() == true)
        {
            System.out.print("Loading");
            programHelper.connectionLoader();

            System.out.println(
                    "### Welcome to Database application, please enter the number to make your choice ###\n");

            while (!programHelper.stopWhileLoopOne)
            {
                System.out.print(programHelper.options1menu);
                programHelper.options1 = programHelper.userInput.nextLine().toLowerCase();
                System.out.println(
                        "--------------------------------------------------------------------------------------\n");
                switch (programHelper.options1)
                {
                    case "1":
                        try
                        {
                            System.out.println(dbHandler.createDataBase());
                        }
                        catch (SQLException se)
                        {
                            System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                        }
                        break;

                    case "2":
                        try
                        {
                            for (int i = 0; i < programHelper.getFiles().size(); i++)
                            {
                                DBTable table = new DBTable();
                                dbFileHandler.makeTable(
                                        "src/main/java/inputFiles/" + programHelper.getFiles().get(i) + ".txt",
                                        table);
                                if (dbFileHandler.isCheckStatusOfValidationOfFile())
                                {
                                    try
                                    {
                                        System.out.println(dbHandler.createTable(table));
                                        programHelper.createdTables = 1;
                                    }
                                    catch (SQLException se)
                                    {

                                        System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                                    }
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            System.out.println("### Please check the input file where all file name are located ###");
                        }
                        System.out.println();
                        break;

                    case "3":
                        for (int i = 0; i < programHelper.getFiles().size(); i++)
                        {
                            try
                            {
                                System.out.println(dbHandler.dropTable(programHelper.getFiles().get(i)));
                            }
                            catch (SQLException se)
                            {
                                System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                            }
                        }
                        System.out.println();
                        break;

                    case "4":
                        for (int i = 0; i < programHelper.getFiles().size(); i++)
                        {
                            DBTable table = new DBTable();
                            dbFileHandler.makeTable(
                                    "src/main/java/inputFiles/" + programHelper.getFiles().get(i) + ".txt", table);
                            try
                            {
                                System.out.println(dbHandler.insertData(table));
                            }
                            catch (SQLException se)
                            {

                                System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                            }
                        }
                        System.out.println();
                        break;

                    case "queries":
                        if(programHelper.createdTables == 1)
                        {
                            programHelper.stopWhileLoopOne = true;
                        }
                        else
                        {
                            System.out.println("### Please create database and tables first ###\n");
                        }
                        break;

                    case "exit":
                        System.out.println("\n\nDisconnecting");
                        programHelper.connectionLoader();
                        System.out.println("Good bye !");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("### Invalid command, try again\n");
                        break;
                }
            }
                while (!programHelper.stopWhileLoopTwo)
                {
                    System.out.print(programHelper.options2menu);
                    programHelper.options2 = programHelper.userInput.nextLine().toLowerCase();
                    System.out.println(
                            "--------------------------------------------------------------------------------------\n");
                    switch (programHelper.options2)
                    {
                        case "1":
                            try
                            {
                                System.out.println("All tables: ");
                                System.out.println("---------------");
                                System.out.println(dbHandler.showAllTables());
                            }
                            catch (SQLException se)
                            {
                                System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                            }
                            System.out.println();
                            break;

                        case "2":
                            try
                            {
                                System.out.println(dbHandler.showAllTables());
                                System.out.println("---------------");
                                System.out.print("Tablename: ");
                                String userOptionMetaData = programHelper.userInput.nextLine().toLowerCase();
                                dbHandler.getMetaDataFromTable(userOptionMetaData);
                            }
                            catch (SQLException se)
                            {

                                System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                            }
                            System.out.println();
                            break;

                        case "3":
                            try
                            {
                                System.out.println(dbHandler.showAllTables());
                                System.out.println("---------------");
                                System.out.print("Tablename: ");
                                String userOptionGetInformationFromTable = programHelper.userInput.nextLine().toLowerCase();
                                System.out.println();
                                System.out.println(dbHandler.getDataFromTable(userOptionGetInformationFromTable));
                            }
                            catch (SQLException se)
                            {
                                System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                            }
                            System.out.println();
                            break;

                        case "4":
                            try
                            {
                                System.out.println(dbHandler.showAllTables());
                                System.out.println("---------------");
                                System.out.print("Tablename: ");
                                String userOptionGetNumberOfRows = programHelper.userInput.nextLine().toLowerCase();
                                System.out.print("\nRows in table: ");
                                System.out.println(dbHandler.getCountRowsFromTable(userOptionGetNumberOfRows));

                            }
                            catch (SQLException se)
                            {
                                System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                            }
                            System.out.println();
                            break;

                        case "5":
                            try
                            {
                                System.out.println(dbHandler.showAllTables());
                                System.out.println("---------------");
                                System.out.print("Tablename: ");
                                String userOptionChooseTable = programHelper.userInput.nextLine().toLowerCase();
                                dbHandler.getColumnNamesInTable(userOptionChooseTable);
                                System.out.print("Column name: ");
                                String userOptionChooseColumn = programHelper.userInput.nextLine();
                                System.out.print("Search value: ");
                                String userOptionChooseAnyWord = programHelper.userInput.nextLine().toLowerCase();
                                System.out.println(
                                        dbHandler.getAnyValueFromAnyTable(userOptionChooseTable, userOptionChooseColumn,
                                                userOptionChooseAnyWord));
                            }
                            catch (SQLException se)
                            {
                                System.out.println(exceptionHandling.SQLException(se.getErrorCode()));
                            }
                            System.out.println();
                            break;

                        case "exit":
                            System.out.print("\n\nDisconnecting");
                            programHelper.connectionLoader();
                            System.out.println("Good bye !");
                            System.exit(0);
                            break;

                        default:
                            System.out.println("### Invalid command, try again\n");
                            break;
                    }
                }
            }
        }

    }