package Application.Program;

import Application.Database.Connection.DBConnection;
import Application.Database.DBHandler.DBHandler;
import Application.Database.Filereader.DBFileHandler;
import Application.Database.OutPutHandler.DBOutPutHandler;
import Application.Database.TableObject.DBTableObject;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * <p>Program class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Class for setting up the application
 * <p>
 * Last modified 18 november 2017
 */
public class Program {

    DBHandler dbHandler;
    DBFileHandler dbFileHandler;
    ExceptionHandling exceptionHandling;
    ProgramHelper programHelper;
    DBTableObject dbTableObject;
    Scanner userInput;

    /**
     * <p>Constructor for Program.</p>
     */
    public Program() {

        // Creating instance of all dependencies
        dbHandler = new DBHandler(
                new DBConnection("src/main/resources/database.properties"),
                new DBOutPutHandler());
        dbFileHandler = new DBFileHandler();
        exceptionHandling = new ExceptionHandling();
        dbTableObject = new DBTableObject();
        userInput = new Scanner(System.in);

        try
        {
            programHelper = new ProgramHelper("src/main/java/Application/files.txt");
        }
        catch (FileNotFoundException f)
        {
            System.out.println(
                    "### 'files.txt' not found, this file is required to run all the files with metadata/data ###" +
                            "### This file should be located in 'Application' package ###");
            System.exit(1);
        }
    }

    /**
     * <p>runProgram.</p>
     *
     * @throws java.lang.InterruptedException if any.
     */
    public void runProgram() throws InterruptedException {

        boolean stopWhileLoopOne = false, stopWhileLoopTwo = false;
        int createdDatabase = 0, createdTables = 0, createdConnectingBetweenTables = 0, insertedData = 0;

        // Checks if connection is connected, if yes run while loop, if not, throw message that connection is not connected
        if (dbHandler.getDBStatus() == true)
        {
            System.out.print("Loading");
            programHelper.connectionLoader();

            System.out.println(
                    "### Welcome to Database application, please enter the number to make your choice ###\n");

            while (!stopWhileLoopOne)
            {
                System.out.print(programHelper.options1Menu());
                String options1 = userInput.nextLine().toLowerCase();
                System.out.println();
                try
                {
                    switch (options1)
                    {
                        case "1":
                            System.out.println(dbHandler.createDataBase());
                            // If database is created, set to 1
                            createdDatabase = 1;
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
                            // If tables is created, set to 1
                            createdTables = 1;
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
                            insertedData = 1;
                            System.out.println();
                            break;

                        case "4":

                            System.out.println(
                                    dbHandler.addConstraintTables("availability", "lecturer", "lecturerId", "id"));
                            System.out.println(
                                    dbHandler.addConstraintTables("subject", "lecturer", "lecturerId", "id"));
                            // If tables are connected, set to 1
                            createdConnectingBetweenTables = 1;
                            System.out.println();
                            break;

                        case "queries":
                            // If you have created database, tables and connected tables, then you are allowed to go to queries
                            if (createdTables == 1 && createdDatabase == 1 && createdConnectingBetweenTables == 1 && insertedData == 1)
                            {
                                stopWhileLoopOne = true;
                            } else
                            {
                                System.out.println("### Please run option 1,2 and 4 before make queries ###\n");
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
                    se.printStackTrace();
                    se.getErrorCode();
                    System.out.println(exceptionHandling.SQLException(se.getErrorCode()) + "\n");
                }
                catch (NullPointerException e)
                {
                    System.out.println("### Please check the file list where all file name are located ###" + "\n");
                }
            }
            while (!stopWhileLoopTwo)
            {
                System.out.print(programHelper.options2Menu());
                String options2 = userInput.nextLine().toLowerCase();
                System.out.println();
                try
                {
                    switch (options2)
                    {
                        case "1":
                            System.out.println(dbHandler.showAllTables());
                            System.out.println();
                            break;

                        case "2":
                            System.out.println(dbHandler.showAllTables());
                            System.out.print("Tablename: ");
                            String userOptionMetaData = userInput.nextLine().toLowerCase();
                            System.out.println(dbHandler.getMetaDataFromTable(userOptionMetaData));
                            System.out.println();
                            break;

                        case "3":
                            System.out.println(dbHandler.showAllTables());
                            System.out.print("Tablename: ");
                            String userOptionGetInformationFromTable = userInput.nextLine().toLowerCase();
                            System.out.println();
                            System.out.println(dbHandler.getDataFromTable(userOptionGetInformationFromTable));
                            System.out.println();
                            break;

                        case "4":
                            System.out.println(dbHandler.showAllTables());
                            System.out.print("Tablename: ");
                            String userOptionChooseTable = userInput.nextLine().toLowerCase();
                            System.out.println("\n" + dbHandler.getColumnNames(userOptionChooseTable));
                            System.out.print("Column name: ");
                            String userOptionChooseColumn = userInput.nextLine().toLowerCase();
                            System.out.print("Search value: ");
                            String userOptionChooseAnyWord = userInput.nextLine().toLowerCase();
                            System.out.println();
                            System.out.print(
                                    dbHandler.getAnyValueFromAnyTable(userOptionChooseTable, userOptionChooseColumn,
                                            userOptionChooseAnyWord));
                            System.out.println();
                            break;

                        case "5":
                            System.out.print("Day: ");
                            String day = userInput.nextLine().toLowerCase();
                            System.out.print("Week: ");
                            String weekNumber = userInput.nextLine().toLowerCase();
                            int weekNumber1 = Integer.parseInt(weekNumber);
                            System.out.println(dbHandler.getAllTeachersAvailabilitiesAtDayXAndWeekX(weekNumber1, day));
                            System.out.println();
                            break;

                        case "6":
                            System.out.print("Lecturer name: ");
                            String name = userInput.nextLine().toLowerCase();
                            System.out.println(dbHandler.getWichTeacherHaveWichSubject(name));
                            System.out.println();
                            break;

                        case "7":
                            System.out.print("Lecturer name: ");
                            String lecturName = userInput.nextLine().toLowerCase();
                            System.out.println("\n" + dbHandler.getDataAboutSpecificLecturerWithCorrectUseOfPreparedStatement(lecturName));
                            System.out.println();
                            break;

                        case "8":
                            System.out.print("Subject code: ");
                            String subjectCode = userInput.nextLine().toLowerCase();
                            System.out.println("\n" + dbHandler.getDataAboutSpecificSubjectWithCorrectUseOfPreparedStatement(subjectCode));
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
                    se.printStackTrace();
                    System.out.println(exceptionHandling.SQLException(se.getErrorCode()) + "\n");
                }
            }
        }
    }
}
