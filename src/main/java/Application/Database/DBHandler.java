package Application.Database;

import java.sql.*;

/**
 * <p>DBHandler class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Class for handling tables and queries
 * <p>
 * Last modified 19 october 2017
 */
public class DBHandler {

    private DBConnection dbConnection;
    private DBOutPutHandler dbOutPutHandler;

    // Creating this variable to keep where i want to start if i have an AUTO_INCREMENT in my first index in dataTypes[0]
    private int startFrom = 0;


    /**
     * Constructor
     * All dependency injections in constructor
     *
     * @param dbConnection a {@link Application.Database.DBConnection} object.
     * @param dbOutPutHandler a {@link Application.Database.DBOutPutHandler} object.
     */
    public DBHandler(DBConnection dbConnection, DBOutPutHandler dbOutPutHandler)
    {

        this.dbConnection = dbConnection;
        this.dbOutPutHandler = dbOutPutHandler;
    }


    /**
     * Status for the connection from DBConnection, using that in Program.class
     *
     * @return boolean of status for getConnection() in DBConnection.class
     */
    public boolean getDBStatus() {

        return dbConnection.isConnected();
    }


    /**
     * Drop the exists database name, and create a database name based on properties file
     *
     * @return String
     * @throws java.sql.SQLException if any.
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
     * This method creates a table based on the information retrieved from getQueryCreateTable() method call
     *
     * @return String output
     * @throws java.sql.SQLException if any.
     * @param dbTableObject a {@link Application.Database.DBTableObject} object.
     */
    public String createTable(DBTableObject dbTableObject) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String createTable = getQueryCreateTable(dbTableObject);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement createQ = connection.prepareStatement(createTable))
        {
            preparedStatement.executeUpdate();
            createQ.executeUpdate();

            return "### Table " + dbTableObject.getTableName() + " created succsessfully ###";
        }
    }


    /**
     * This method extracts the information from table object needed to create table and sends query to createTable()
     *
     * @return query
     * @param dbTableObject a {@link Application.Database.DBTableObject} object.
     */
    public String getQueryCreateTable(DBTableObject dbTableObject)
    {

        StringBuilder createTableQuery = new StringBuilder(
                "CREATE TABLE " + dbTableObject.getTableName() + " (\n");
        for (int i = 0; i < dbTableObject.getColumnsName().length; i++)
        {
            createTableQuery.append(
                    dbTableObject.getColumnsName()[i] + " " + dbTableObject.getDataTypes()[i] + ",\n");
        }
        createTableQuery.append("PRIMARY KEY " + "(" + dbTableObject.getPrimaryKey() + ")))");

        return createTableQuery.toString().substring(0, createTableQuery.length() - 2) + "\n);";
    }


    /**
     * This method insert data based on the information retrieved from getInsertDataQuery() method call
     *
     * @return String output
     * @throws java.sql.SQLException if any.
     * @param dbTableObject a {@link Application.Database.DBTableObject} object.
     */
    public String insertData(DBTableObject dbTableObject) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String insertData = getInsertDataQuery(dbTableObject);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(insertData))
        {
            preparedStatement.executeUpdate();
            int i = 1;
            for (int k = 0; k < dbTableObject.getJustDataWithoutMetaData().size(); k++)
            {
                for (int j = 0; j < dbTableObject.getJustDataWithoutMetaData().get(k).length; )
                {
                    preparedStatement1.setString(i++, dbTableObject.getJustDataWithoutMetaData().get(k)[j++]);
                }
            }
            int rows = preparedStatement1.executeUpdate();

            return "### Inserted " + rows + " rows in table " + dbTableObject.getTableName() + " ###";
        }
    }


    /**
     * This method extracts the information from table object needed and sends query to insertData()
     *
     * @return query
     * @param dbTableObject a {@link Application.Database.DBTableObject} object.
     */
    public String getInsertDataQuery(DBTableObject dbTableObject)
    {

        checkIfDataTypeIndex0HaveTheWordAutoIncrement(dbTableObject);
        StringBuilder insertDataQuery = new StringBuilder("INSERT INTO " + dbTableObject.getTableName() + " (");
        for (int i = startFrom; i < dbTableObject.getColumnsName().length - 1; i++)
        {
            insertDataQuery.append(dbTableObject.getColumnsName()[i] + ", ");
        }
        insertDataQuery.append(
                dbTableObject.getColumnsName()[dbTableObject.getColumnsName().length - 1] + ")\nVALUES\n(");

        for (int i = 0; i < dbTableObject.getJustDataWithoutMetaData().size(); i++)
        {
            String[] raw = dbTableObject.getJustDataWithoutMetaData().get(i);
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
    private void checkIfDataTypeIndex0HaveTheWordAutoIncrement(DBTableObject table)
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
        }
        catch (NullPointerException ne)
        {
            // ne.printStackTrace();
        }
    }


    /**
     * This method drop the table
     *
     * @return String output
     * @throws java.sql.SQLException if any.
     * @param tableName a {@link java.lang.String} object.
     */
    public String dropTable(String tableName) throws SQLException {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String dropTable = "DROP TABLE " + tableName;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(dropTable))
        {
            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();

            return "### Table " + tableName + " is dropped ###";
        }
    }


    /**
     * This method extracts meta data about the table, column names, data types and size
     *
     * @return printMetadata() in DBOutPutHandler
     * @throws java.sql.SQLException if any.
     * @param tableName a {@link java.lang.String} object.
     */
    public String getMetaDataFromTable(String tableName) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = "SELECT * FROM " + tableName;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printMetaData(resultSet);
        }
    }


    /**
     * This method extract data from table
     *
     * @return printResult() in DbOutPutHandler
     * @throws java.sql.SQLException if any.
     * @param tableName a {@link java.lang.String} object.
     */
    public String getDataFromTable(String tableName) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = "SELECT * FROM " + tableName;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);

        }
    }


    /**
     * This method retrive number of rows in table
     *
     * @return printResult() method in DBOutPutHandler
     * @throws java.sql.SQLException if any.
     * @param tableName a {@link java.lang.String} object.
     */
    public String getCountRowsFromTable(String tableName) throws SQLException
    {

        String chooseDbName = "USE " + dbConnection.getDbName();
        String selectCountRowFromTable = "SELECT COUNT(*) as 'Number of rows' FROM " + tableName;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDbName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectCountRowFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);
        }
    }


    /**
     * This method get the data based on tablename, column name and value of the search
     *
     * @return printResult() method in DbOutPutHandler
     * @throws java.sql.SQLException if any.
     * @param tableName a {@link java.lang.String} object.
     * @param column a {@link java.lang.String} object.
     * @param search a {@link java.lang.String} object.
     */
    public String getAnyValueFromAnyTable(String tableName, String column, String search) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = "SELECT * FROM " + tableName + " WHERE " + column + " LIKE " + "'" + search + "'";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);
        }
    }


    /**
     * This method get all the tables exists in the database
     *
     * @return printResult() method in DbOutPutHandler
     * @throws java.sql.SQLException if any.
     */
    public String showAllTables() throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String showTables = "SHOW tables";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(showTables))

        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);
        }
    }

}


//    /**
//     * 'lecturerandsubject' ,
//     * 'subjectandprogram',
//     * 'subjectandroom'
//     */
//     public void connectTables()
//     {
//          TODO: Method for connecting the tables
//     }

