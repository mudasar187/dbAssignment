import Program.DBConnection;
import Program.DBFileHandler;
import Program.DBHandler;
import java.sql.SQLException;
import java.util.Scanner;


/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Main class to run application
 *
 * Last modified 04 october 2017
 *
 */

public class Application {

    public static void main(String[] args) throws InterruptedException, SQLException {

        // Creating scanner for user so user can send an input to application
        Scanner userInput = new Scanner(System.in);


        // Holding the variables for options 1
        String options1;
        //Holding the variable for options 2
        String options2;


        //Ensure that database have been created
        int databaseCreated = 0;


        // Creating a boolean for the first while loop
        boolean stopWhileLoopOne = false;
        // Creating a boolean for the second while loop
        boolean stopWhileLoopTwo = false;


        // Creating new instance of DBHandler to run the application
        // DBHandler is injected with constructor from DBConnection and DBFileHandler
        DBHandler dbHandler = new DBHandler(
                new DBConnection("src/main/resources/database.properties"),
                new DBFileHandler()
        );

        // These are options you can make in first while loop, options3 is showed up first because its recommended to run these first
        // When you are finished with options3 when you will jump to options3, second while loop to make queries
        String options1menu =
                "--------------------------------------------------------------------------------------\n" +
                        "### Choose your option by enter these numbers \n" +
                        "1      Create database \n" +
                        "2      Create tables \n" +
                        "3      Drop tables \n" +
                        "4      Insert data into tables \n" +
                        "5      Refresh tables (Empty data in tables) \n" +
                        "### Type 'queries' to make queries or type 'exit' to exit program \n" +
                        "\nYour choice: ";


        String options2menu =
                "--------------------------------------------------------------------------------------\n" +
                        "### Choose your query option by enter these numbers \n" +
                        "1      Show all tables \n" +
                        "2      Get metadata from table \n" +
                        "3      Get data information from table \n" +
                        "4      Find number of rows in table \n" +
                        "5      Find any result based on any name, any column from any table \n" +
                        "### Type 'exit' to exit the program \n" +
                        "\nYour choice: ";


        // Checks if connection is connected, if yes run while loop, if not, throw message that connection is not connected
        if (dbHandler.isConnected() == true) {

            System.out.print("Loading");
            dbHandler.connectionLoader();

            System.out.println("### Welcome to Database application, please enter the number to make your choice \n\n");


            while (!stopWhileLoopOne)
            {
                System.out.println(options1menu);
                options1 = userInput.nextLine();
                System.out.println(
                        "--------------------------------------------------------------------------------------\n");

                if (options1.equals("1"))
                {

                    dbHandler.createDataBase();

                } else if (options1.equals("2"))
                {

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/lecturer.txt");
                    dbHandler.createTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/room.txt");
                    dbHandler.createTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/subject.txt");
                    dbHandler.createTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/semester.txt");
                    dbHandler.createTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/program.txt");
                    dbHandler.createTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/availability.txt");
                    dbHandler.createTable();

//                    dbHandler.getDbFileHandler("src/main/java/inputFiles/lecturerandsubject.txt");
//                    dbHandler.createTable();
//
//                    dbHandler.getDbFileHandler("src/main/java/inputFiles/subjectandroom.txt");
//                    dbHandler.createTable();
//
//                    dbHandler.getDbFileHandler("src/main/java/inputFiles/subjectandprogram.txt");
//                    dbHandler.createTable();

                    // TODO: START -> These files are entered in order to create an optimal timetable
//                    dbHandler.connectTables();
                    // TODO: <- END

                    System.out.println();

                }
                else if(options1.equals("3"))
                {
                    dbHandler.dropTable("lecturer");
                    dbHandler.dropTable("room");
                    dbHandler.dropTable("subject");
                    dbHandler.dropTable("semester");
                    dbHandler.dropTable("program");
                    dbHandler.dropTable("availability");
//                    dbHandler.dropTable("lecturerandsubject");
//                    dbHandler.dropTable("subjectandroom");
//                    dbHandler.dropTable("subjectandprogram");
                    System.out.println();
                }
                else if (options1.equals("4"))
                {
                    dbHandler.getDbFileHandler("src/main/java/inputFiles/lecturer.txt");
                    dbHandler.insertDataIntoTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/room.txt");
                    dbHandler.insertDataIntoTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/subject.txt");
                    dbHandler.insertDataIntoTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/semester.txt");
                    dbHandler.insertDataIntoTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/program.txt");
                    dbHandler.insertDataIntoTable();

                    dbHandler.getDbFileHandler("src/main/java/inputFiles/availability.txt");
                    dbHandler.insertDataIntoTable();

//                    dbHandler.getDbFileHandler("src/main/java/inputFiles/lecturerandsubject");
//                    dbHandler.insertDataIntoTable();
//
//                    dbHandler.getDbFileHandler("src/main/java/inputFiles/subjectandprogram");
//                    dbHandler.insertDataIntoTable();
//
//                    dbHandler.getDbFileHandler("src/main/java/inputFiles/subjectandroom");
//                    dbHandler.insertDataIntoTable();


                    System.out.println();

                }
                else if(options1.equals("5"))
                {
                    dbHandler.truncateTable("lecturer");
                    dbHandler.truncateTable("room");
                    dbHandler.truncateTable("subject");
                    dbHandler.truncateTable("semester");
                    dbHandler.truncateTable("program");
                    dbHandler.truncateTable("availability");

                    System.out.println();
                }
                else if(options1.equals("queries"))
                {
                    stopWhileLoopOne = true;
                }
                else if(options1.equals("exit"))
                {

                    System.out.print("\n\nDisconnecting");
                    dbHandler.connectionLoader();
                    System.out.println("Good bye !");
                    System.exit(0);
                }
                else
                {
                    System.out.println("### Invalid command, try again\n");
                }
            }

            while (!stopWhileLoopTwo)
            {
                System.out.println(options2menu);
                options2 = userInput.nextLine();
                System.out.println("--------------------------------------------------------------------------------------\n");


                {
                    if (options2.equals("1"))
                    {
                        System.out.println("All tables: ");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                    }
                    else if (options2.equals("2"))
                    {
                        System.out.println("Which table do you want to get metadata of ?");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionMetaData = userInput.nextLine().toLowerCase();
                        dbHandler.getMetaDataFromTable(userOptionMetaData);
                    }
                    else if (options2.equals("3"))
                    {
                        System.out.println("Which table do you want to show ?");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionGetInformationFromTable = userInput.nextLine().toLowerCase();
                        System.out.println("\n--------------------------------------");
                        System.out.println("Table name: " + "'" + userOptionGetInformationFromTable + "'" + "\n");
                        System.out.println(dbHandler.getDataFromTable(userOptionGetInformationFromTable));
                    }
                    else if (options2.equals("4"))
                    {
                        System.out.println("Which table do you want to count rows in ?");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionGetNumberOfRows = userInput.nextLine().toLowerCase();
                        System.out.print("\nRows in table " + "'" + userOptionGetNumberOfRows + "'" + ":");
                        System.out.println();
                        System.out.println(dbHandler.getCountRowsFromTable(userOptionGetNumberOfRows));
                    }
                    else if (options2.equals("5"))
                    {
                        System.out.println("Which table do you want to search in ? ");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionChooseTable = userInput.nextLine().toLowerCase();
                        System.out.println("Which column you want to search in ?");
                        dbHandler.getColumnNamesInTable(userOptionChooseTable);
                        System.out.print("Column name: ");
                        String userOptionChooseColumn = userInput.nextLine();
                        System.out.println("Type the word you want to search for ?");
                        System.out.print("Search for word: ");
                        String userOptionChooseAnyWord = userInput.nextLine().toLowerCase();
                        System.out.println();
                        System.out.println(dbHandler.getAnyValueFromAnyTable(userOptionChooseTable, userOptionChooseColumn, userOptionChooseAnyWord));
                    }
                    else if (options2.equals("exit"))
                    {
                        System.out.print("\n\nDisconnecting");
                        dbHandler.connectionLoader();
                        System.out.println("Good bye !");
                        System.exit(0);
                    }
                    else
                    {
                        System.out.println("### Invalid command, try again\n");
                    }
                }
            }
        }
    }
}