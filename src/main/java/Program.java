import Program.DBConnection;
import Program.DBFileHandler;
import Program.DBHandler;
import Program.DBTable;


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

        // Remember to set the right file to read to get the files that needed to create tables and add data
        ProgramHelper programHelper = new ProgramHelper("src/main/java/helpMaterials/files.txt");


        // Checks if connection is connected, if yes run while loop, if not, throw message that connection is not connected
        if (dbHandler.getDBStatus() == true)
        {
            System.out.print("Loading");
            programHelper.connectionLoader();

            System.out.println("### Welcome to Database application, please enter the number to make your choice ###\n");

            while(!programHelper.stopWhileLoopOne)
            {
                System.out.print(programHelper.options1menu);
                programHelper.options1 = programHelper.userInput.nextLine().toLowerCase();
                System.out.println(
                        "--------------------------------------------------------------------------------------\n");
                switch (programHelper.options1)
                {

                    case "1":
                        dbHandler.createDataBase();
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
                                if (dbFileHandler.isCheckStatusOfValidationOfFile() != false)
                                {
                                    dbHandler.createTable(table);
                                }
                            }
                        } catch (Exception e)
                        {
                            System.out.println("### Cannot create tables, please check files and try again ###");
                        }
                        System.out.println();
                        break;

                    case "3":
                        for (int i = 0; i < programHelper.getFiles().size(); i++)
                        {
                            dbHandler.dropTable(programHelper.getFiles().get(i));
                        }
                        break;

                    case "4":
                        for (int i = 0; i < programHelper.getFiles().size(); i++)
                        {
                            DBTable table = new DBTable();
                            dbFileHandler.makeTable("src/main/java/inputFiles/"+programHelper.getFiles().get(i)+".txt", table);
                            dbHandler.insertData(table);
                        }
                        System.out.println();
                        break;

                    case "5":
                        for (int i = 0; i < programHelper.getFiles().size(); i++)
                        {
                            dbHandler.truncateTable(programHelper.getFiles().get(i));
                        }
                        System.out.println();
                        break;

                    case "queries":
                        programHelper.stopWhileLoopOne = true;
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
            while (!programHelper.stopWhileLoopTwo) {

                System.out.print(programHelper.options2menu);
                programHelper.options2 = programHelper.userInput.nextLine().toLowerCase();
                System.out.println(
                        "--------------------------------------------------------------------------------------\n");
                switch (programHelper.options2) {
                    case "1":
                        System.out.println("All tables: ");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        break;

                    case "2":
                        System.out.println("Which table do you want to get metadata of ?");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionMetaData = programHelper.userInput.nextLine().toLowerCase();
                        dbHandler.getMetaDataFromTable(userOptionMetaData);
                        break;

                    case "3":
                        System.out.println("Which table do you want to show ?");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionGetInformationFromTable = programHelper.userInput.nextLine().toLowerCase();
                        System.out.println("\n--------------------------------------");
                        System.out.println("Table name: " + "'" + userOptionGetInformationFromTable + "'" + "\n");
                        System.out.println(dbHandler.getDataFromTable(userOptionGetInformationFromTable));
                        break;

                    case "4":
                        System.out.println("Which table do you want to count rows in ?");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionGetNumberOfRows = programHelper.userInput.nextLine().toLowerCase();
                        System.out.print("\nRows in table " + "'" + userOptionGetNumberOfRows + "'" + ": ");
                        System.out.println(dbHandler.getCountRowsFromTable(userOptionGetNumberOfRows));
                        break;

                    case "5":
                        System.out.println("Which table do you want to search in ? ");
                        System.out.println("---------------");
                        System.out.println(dbHandler.showAllTables());
                        System.out.print("Tablename: ");
                        String userOptionChooseTable = programHelper.userInput.nextLine().toLowerCase();
                        System.out.println("Which column you want to search in ?");
                        dbHandler.getColumnNamesInTable(userOptionChooseTable);
                        System.out.print("Column name: ");
                        String userOptionChooseColumn = programHelper.userInput.nextLine();
                        System.out.println("Type the word you want to search for ?");
                        System.out.print("Search for word: ");
                        String userOptionChooseAnyWord = programHelper.userInput.nextLine().toLowerCase();
                        System.out.println();
                        System.out.println(
                                dbHandler.getAnyValueFromAnyTable(userOptionChooseTable, userOptionChooseColumn,
                                        userOptionChooseAnyWord));
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