package Application.Program;

import Application.Database.Connection.DBConnection;
import Application.Database.DBHandler.DBHandler;
import Application.Database.Filereader.DBFileHandler;
import Application.Database.OutPutHandler.DBOutPutHandler;
import Application.Database.TableObject.DBTableObject;

import java.sql.SQLException;

/**
 * <p>Program class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Class for setting up the application
 * <p>
 * Last modified 19 october 2017
 */
public class Program {

    DBHandler dbHandler;
    DBFileHandler dbFileHandler;
    ExceptionHandling exceptionHandling;
    ProgramHelper programHelper;
    DBTableObject dbTableObject;

    /**
     * <p>Constructor for Program.</p>
     */
    public Program() {

        // Creating instance of all dependencies
        dbHandler = new DBHandler(new DBConnection("src/main/resources/database.properties"),
                new DBOutPutHandler());
        dbFileHandler = new DBFileHandler();
        exceptionHandling = new ExceptionHandling();
        programHelper = new ProgramHelper("src/main/java/Application/files.txt");
        dbTableObject = new DBTableObject();
    }

    /**
     * <p>runProgram.</p>
     *
     * @throws java.lang.InterruptedException if any.
     */
    public void runProgram() throws InterruptedException {

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
                System.out.println();
                try
                {
                    switch (programHelper.options1)
                    {
                        case "1":
                            System.out.println(dbHandler.createDataBase());
                            break;

                        case "2":
                            for (int i = 0; i < programHelper.getFiles().size(); i++)
                            {
                                dbTableObject = new DBTableObject();
                                dbFileHandler.makeObject(
                                        "src/main/java/Application/inputFiles/" + programHelper.getFiles().get(
                                                i) + ".txt",
                                        dbTableObject);
                                if (dbFileHandler.isCheckStatusOfValidationOfFile())
                                {

                                    System.out.println(dbHandler.createTable(dbTableObject));
                                }
                            }
                            programHelper.createdTables = 1;
                            System.out.println();
                            break;

                        case "3":
                            for (int i = 0; i < programHelper.getFiles().size(); i++)
                            {
                                dbTableObject = new DBTableObject();
                                dbFileHandler.makeObject(
                                        "src/main/java/Application/inputFiles/" + programHelper.getFiles().get(
                                                i) + ".txt",
                                        dbTableObject);

                                System.out.println(dbHandler.insertData(dbTableObject));
                            }
                            System.out.println();
                            break;

                        case "queries":
                            if (programHelper.createdTables == 1)
                            {
                                programHelper.stopWhileLoopOne = true;
                            } else
                            {
                                System.out.println("### Please create database and tables first ###\n");
                            }
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
                catch (SQLException se)
                {
                    System.out.println(exceptionHandling.SQLException(se.getErrorCode()) + "\n");
                }
                catch (Exception e)
                {
                    System.out.println("### Please check the file list where all file name are located ###" + "\n");
                }
            }
            while (!programHelper.stopWhileLoopTwo)
            {
                System.out.print(programHelper.options2menu);
                programHelper.options2 = programHelper.userInput.nextLine().toLowerCase();
                System.out.println();
                try
                {
                    switch (programHelper.options2)
                    {
                        case "1":
                            System.out.println(dbHandler.showAllTables());
                            System.out.println();
                            break;

                        case "2":
                            System.out.println(dbHandler.showAllTables());
                            System.out.print("Tablename: ");
                            String userOptionMetaData = programHelper.userInput.nextLine().toLowerCase();
                            System.out.println(dbHandler.getMetaDataFromTable(userOptionMetaData));
                            System.out.println();
                            break;

                        case "3":
                            System.out.println(dbHandler.showAllTables());
                            System.out.print("Tablename: ");
                            String userOptionGetInformationFromTable = programHelper.userInput.nextLine().toLowerCase();
                            System.out.println();
                            System.out.println(dbHandler.getDataFromTable(userOptionGetInformationFromTable));
                            System.out.println();
                            break;

                        case "4":
                            System.out.println(dbHandler.showAllTables());
                            System.out.print("Tablename: ");
                            String userOptionGetNumberOfRows = programHelper.userInput.nextLine().toLowerCase();
                            System.out.println();
                            System.out.print(dbHandler.getCountRowsFromTable(userOptionGetNumberOfRows));
                            System.out.println();
                            break;

                        case "5":
                            System.out.println(dbHandler.showAllTables());
                            System.out.print("Tablename: ");
                            String userOptionChooseTable = programHelper.userInput.nextLine().toLowerCase();
                            System.out.print("Column name: ");
                            System.out.println(dbHandler.getColumnNamesFromTable(userOptionChooseTable));
                            String userOptionChooseColumn = programHelper.userInput.nextLine().toLowerCase();
                            System.out.print("Search value: ");
                            String userOptionChooseAnyWord = programHelper.userInput.nextLine().toLowerCase();
                            System.out.println();
                            System.out.print(
                                    dbHandler.getAnyValueFromAnyTable(userOptionChooseTable, userOptionChooseColumn,
                                            userOptionChooseAnyWord));
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
                catch (SQLException se)
                {
                    System.out.println(exceptionHandling.SQLException(se.getErrorCode()) + "\n");
                }
            }
        }
    }
}
