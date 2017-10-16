package Program;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Class for handling tables and queries
 *
 * Last modified 16 october 2017
 *
 */

public class DBHandler {

    private DBConnection dbConnection;

    // Creating this variable to keep where i want to start if i have an AUTO_INCREMENT in my first index in dataTypes[0]
    private int startFrom = 0;


    /**
     * Constructor
     * All dependency injections in constructor
     */
    public DBHandler(DBConnection dbConnection)
    {
        this.dbConnection = dbConnection;
    }


    /**
     * Get status for the connection from DBConnection, using that in Application.class
     */
    public boolean getDBStatus() {
        return dbConnection.isConnected();
    }


    /**
     * Creating the database name based on properties file, but first drop the database if it exists
     */
    public String createDataBase() throws SQLException
    {
        String dropDatabase = "DROP DATABASE IF EXISTS " + dbConnection.getDbName();
        String createDBName = "CREATE DATABASE " + dbConnection.getDbName();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropDatabase);
             PreparedStatement preparedStatement1 = connection.prepareStatement(createDBName))
        {
            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();

            return "### Database created successfully ###\n";
        }
    }


    /**
     * This method creates a table based on the information retrieved from  getQueryCreateTable() method call
     * @param table, Table object to extract data from
     */
    public String createTable(DBTable table) throws SQLException
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String createTable = getQueryCreateTable(table);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement createQ = connection.prepareStatement(createTable))
        {
            preparedStatement.executeUpdate();
            createQ.executeUpdate();

            return "### Table " + table.getTableName() + " created succsessfully ###";
        }
    }


    /**
     * This method extracts the information from table object needed to create table and sends query to createTable()
     * @param table, The table object sent into createTable () its parameter
     * @return the create table query
     */
    public String getQueryCreateTable(DBTable table)
    {
            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + table.getTableName() + " (\n");
            for (int i = 0; i < table.getColumnsName().length; i++)
            {
                createTableQuery.append(table.getColumnsName()[i] + " " + table.getDataTypes()[i] + ",\n");
            }
            createTableQuery.append("PRIMARY KEY " + "(" + table.getPrimaryKey() + ")))");

            return createTableQuery.toString().substring(0, createTableQuery.length() - 2) + "\n);";
    }


    /**
     * This method insert data based on the information retrieved from the object via the getInsertDataQuery() method call
     * @param table, Table object to extract data from
     */
    public String insertData(DBTable table) throws SQLException
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String insertData = getInsertDataQuery(table);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(insertData))
        {
            preparedStatement.executeUpdate();
            int i = 1;
            for (int k = 0; k < table.getJustDataWithoutMetaData().size(); k++)
            {
                for (int j = 0; j < table.getJustDataWithoutMetaData().get(k).length; )
                    preparedStatement1.setString(i++, table.getJustDataWithoutMetaData().get(k)[j++]);
            }
            int rows = preparedStatement1.executeUpdate();

            return "### Inserted " + rows + " rows in table " + table.getTableName()+" ###";
        }
    }


    /**
     * This method extracts the information from arraylist = justDataWithoutMetaData needed to put  and sends query to createTable()
     * @param table, The table object sent into createTable () its parameter
     * @return the create table query
     */
    public String getInsertDataQuery(DBTable table)
    {
        checkIfDataTypeIndex0HaveTheWordAutoIncrement(table);
        StringBuilder insertDataQuery = new StringBuilder("INSERT INTO " + table.getTableName() + " (");
        for (int i = startFrom; i < table.getColumnsName().length - 1; i++)
        {
            insertDataQuery.append(table.getColumnsName()[i] + ", ");
        }
        insertDataQuery.append(table.getColumnsName()[table.getColumnsName().length - 1] + ")\nVALUES\n(");

        for (int i = 0; i < table.getJustDataWithoutMetaData().size(); i++)
        {
            String[] raw = table.getJustDataWithoutMetaData().get(i);
            for (int j = 0; j < raw.length - 1; j++)
            {
                insertDataQuery.append("?" + ", ");
            }
            insertDataQuery.append("?" + "),\n(");
        }
        return insertDataQuery.toString().substring(0, insertDataQuery.length() - 3);
    }


    /**
     * Check for auto_increment
     * This method ensures that if you have chosen to set the first column as auto increment,
     * this method will handle the input of the insertDataIntoTable () method.
     * Then, this jump over the first column that is set to auto_increment and make sure it does not make any trouble to enter the data
     */
    private void checkIfDataTypeIndex0HaveTheWordAutoIncrement(DBTable table)
    {
        try
        {
            String dataTypeIndex0 = table.getDataTypes()[0];
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


    /**
     * Drop table
     * This method deletes the tables, if you want to add other tables or update table meta data,
     * you can do that by dropping the tables and creating others as long as you follow the instructions in the Readme file
     * for how the setup should be for the program to run correctly.
     */
    public String dropTable(String tableName) throws SQLException {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String dropTable = "DROP TABLE " + tableName;

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(dropTable))
        {
            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();

            return "### Table " + tableName + " is dropped ###";
        }
    }


    /**
     * Metadata from tables
     * This method extracts meta data about the tables, column names, data types and size
     */
    public void getMetaDataFromTable(String tableName) throws SQLException
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
    }


    /**
     * Data from tables
     * This method extracts all the data in the table and calls the printResult () method to print the result
     */
    public String getDataFromTable(String tableName) throws SQLException
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
    }


    /**
     * Number of rows in table
     * This method retrieves the number of rows in a table and calls the printResult () method to print the result
     */
    public String getCountRowsFromTable(String tableName) throws SQLException
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
    }


    /**
     * Give the value of the word you search for based on tableName, column
     * This method allows you to search whatever you want, no matter what table,
     * you must define the table name you want to search in, column and the word you are searching for
     */
    public String getAnyValueFromAnyTable(String tableName, String column, String search) throws SQLException
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
    }


    /**
     * Show all table names in database
     *
     */
    public String showAllTables() throws SQLException
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
    }


    /**
     * Printing out result from these methods:
     * getDataFromTables()
     * getCountRowsFromTable()
     * getAnyValueFromAnyTable()
     * showAllTables()
     */
    public String printResult(ResultSet resultSet)
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
            if (output.length() == 0) return "### No matching value ###";
            return output;
        }
        catch (SQLException se)
        {
            return "### Error creating print ###";
        }
    }


//    /**
//     * 'lecturerandsubject' ,
//     * 'subjectandprogram',
//     * 'subjectandroom'
//     */
//     public void connectTables()
//     {
         // TODO: Method for connecting the tables
//     }
}
