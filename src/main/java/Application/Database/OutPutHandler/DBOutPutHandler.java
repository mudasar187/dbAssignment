package Application.Database.OutPutHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <p>DBOutPutHandler class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Class creating result string for the methods in DBHandler
 * <p>
 * Last modified 19 october 2017
 */
public class DBOutPutHandler {

    /**
     * Creating a outprint for these methods in DBHandler
     * <p>
     * getDataFromTables()
     * getCountRowsFromTable()
     * getAnyValueFromAnyTable()
     * showAllTables()
     *
     * @return output string
     * @param resultSet a java.sql.ResultSet object.
     */
    public String printResult(ResultSet resultSet)
    {

        try
        {
            String output = "";
            int columnCount = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < columnCount; i++)
            {
                String temp = resultSet.getMetaData().getColumnName(i + 1).toLowerCase().replace("_", " ");
                temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
                output += String.format("%-32s", temp);
            }
            output += "\n";
            for (int i = 0; i < columnCount; i++)
            {
                output += "---------------------------";
            }
            output += "\n";
            while (resultSet.next())
            {
                for (int i = 0; i < columnCount; i++)
                {
                    String columnValue = resultSet.getString(i + 1);
                    output += String.format("%-32s", columnValue);
                }

                output += "\n";

            }
            if (output.length() == 0)
            {
                return "### No matching value ###";
            }
            return output;
        }
        catch (SQLException se)
        {
            return "### Error creating print ###";
        }
    }


    /**
     * Creating a output string for the getMetaData() method in DBHandler
     *
     * @return output string
     * @param resultSet a java.sql.ResultSet object.
     * @throws java.sql.SQLException if any.
     */
    public String printMetaData(ResultSet resultSet) throws SQLException {

        try
        {
            String output = "";
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            output += String.format("\n%-15s%-10s%-10s\n", "Field", "Size", "DataType");
            output += "----------------------------------\n";

            for (int i = 0; i < columnCount; i++)
            {
                output += String.format("%-15s", resultSet.getMetaData().getColumnName(i + 1));
                output += String.format("%-10s", resultSet.getMetaData().getColumnDisplaySize(i + 1));
                output += String.format("%-10s\n", resultSet.getMetaData().getColumnTypeName(i + 1));
            }

            if (output.length() == 0)
            {
                return "### No matching value ###";
            }
            return output;
        }
        catch (SQLException se)
        {
            return "### Error creating print ###";
        }
    }
}
