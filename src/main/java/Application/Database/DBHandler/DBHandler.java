package Application.Database.DBHandler;

import Application.Database.Connection.DBConnection;
import Application.Database.OutPutHandler.DBOutPutHandler;
import Application.Database.TableObject.DBTableObject;

import java.sql.*;
import com.mysql.jdbc.ResultSetMetaData;


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
     * @param dbConnection a {@link Application.Database.Connection.DBConnection} object.
     * @param dbOutPutHandler a {@link Application.Database.OutPutHandler.DBOutPutHandler} object.
     */
    public DBHandler(DBConnection dbConnection, DBOutPutHandler dbOutPutHandler)
    {

        this.dbConnection = dbConnection;
        this.dbOutPutHandler = dbOutPutHandler;
    }


    /**
     * Status for the connection from Connection, using that in Program.class
     *
     * @return boolean of status for getConnection() in Connection.class
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
     * @param dbTableObject a {@link Application.Database.TableObject.DBTableObject} object.
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
     * @param dbTableObject a {@link DBTableObject} object.
     */
    private String getQueryCreateTable(DBTableObject dbTableObject)
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
     * @param dbTableObject a {@link Application.Database.TableObject.DBTableObject} object.
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
     * @param dbTableObject a {@link DBTableObject} object.
     */
    private String getInsertDataQuery(DBTableObject dbTableObject)
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
    private void checkIfDataTypeIndex0HaveTheWordAutoIncrement(DBTableObject table) throws NullPointerException
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


    /**
     * This method extracts meta data about the table, column names, data types and size
     * Using getQueryForSelectColumnNames() method to get Select (columnNames) query
     *
     * @return printMetadata() in DBOutPutHandler
     * @throws java.sql.SQLException if any.
     * @param tableName a {@link java.lang.String} object.
     */
    public String getMetaDataFromTable(String tableName) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = getQueryForSelectColumnNames(tableName);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printMetaData(resultSet);
        }
    }


    /**
     * This method extract data from table
     * Using getQueryForSelectColumnNames() method to get Select (columnNames) query
     *
     * @return printResult() in DbOutPutHandler
     * @throws java.sql.SQLException if any.
     * @param tableName a {@link java.lang.String} object.
     */
    public String getDataFromTable(String tableName) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectFromTable = getQueryForSelectColumnNames(tableName);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);

        }
    }


    /**
     * This method get the data based on tablename, column name and value of the search
     * Using getQueryForSelectColumnNames() method to get Select (columnNames) query
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
        String selectFromTable = getQueryForSelectColumnNames(tableName) + " WHERE " + column + " LIKE " + "'" + search + "'";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectFromTable, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);
        }
    }


    /**
     * This method get query for Select (columnNames)
     *
     * @param tableName a {@link java.lang.String} object.
     * @return select (columnNames) query
     * @throws java.sql.SQLException if any
     */
    public String getQueryForSelectColumnNames(String tableName) throws SQLException
    {

        String chooseDBName = "USE " + dbConnection.getDbName();
        StringBuilder buildString = new StringBuilder("SELECT ");

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement selectAll = connection.prepareStatement(
                     "SELECT * FROM " + tableName, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = selectAll.executeQuery();

            ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();

            for (int i = 1; i < resultSetMetaData.getColumnCount(); i++)
            {
                buildString.append(resultSetMetaData.getColumnName(i) + ", ");
            }

            buildString.append(resultSetMetaData.getColumnName(resultSetMetaData.getColumnCount()) + " from " + tableName);
        }

        return buildString.toString();
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
        String showTables = "SELECT table_name FROM information_schema.tables where table_schema = " + "'"+dbConnection.getDbName()+"'";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
             PreparedStatement preparedStatement1 = connection.prepareStatement(showTables, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))

        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);
        }
    }

    public String getAllTeachersAvailabilitiesInWeekX(int weekX) throws SQLException
    {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String selectQuery = "SELECT lecturer.firstName, lecturer.lastName, availability.monday, availability.thursday, availability.wednesday, availability.thursday, availability.friday FROM lecturer LEFT JOIN availability ON lecturer.id = availability.lecturerId  WHERE weekId = "+ weekX;

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
            PreparedStatement preparedStatement1 = connection.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement1.executeQuery();

            return dbOutPutHandler.printResult(resultSet);
        }
    }


    /**
     * 'lecturerandsubject' ,
     * 'subjectandprogram',
     * 'subjectandroom'
     *
     * @param tableName1 a {@link java.lang.String} object.
     * @param tableName2 a {@link java.lang.String} object.
     * @param column1 a {@link java.lang.String} object.
     * @param column2 a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.sql.SQLException if any.
     */
    public String addConstraintTables(String tableName1, String tableName2, String column1, String column2) throws SQLException {
        String chooseDBName = "USE " + dbConnection.getDbName();
        String text = "fk_" + tableName1 + "" + column1 + "" + tableName2;
        String connectTablesQuery = "ALTER TABLE " + tableName1 + "" +
                "  ADD CONSTRAINT " + text + "" +
                " FOREIGN KEY (" + column1 +")" +
                " REFERENCES " + tableName2 +" (" + column2 +");";

        try(Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(chooseDBName);
        PreparedStatement preparedStatement1 = connection.prepareStatement(connectTablesQuery))
        {
            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();

            return "### Tables " + tableName1 + " and " + tableName2 + " are connected ###";
        }
    }
}


