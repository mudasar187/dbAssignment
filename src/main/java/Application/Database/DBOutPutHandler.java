package Application.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
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
     * @param resultSet
     *
     * @return output string
     *
     * @throws SQLException
     */
    public String printResult(ResultSet resultSet) throws SQLException
    {

        try
        {
            String output = "";
            int columnCount = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < columnCount; i++)
            {
                String temp = resultSet.getMetaData().getColumnName(i + 1).toLowerCase().replace("_", " ");
                temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
                output += String.format("%-20s", temp);
            }
            output += "\n";
            for (int i = 0; i < columnCount; i++)
            {
                output += "----------------------";
            }
            output += "\n";
            while (resultSet.next())
            {
                for (int i = 0; i < columnCount; i++)
                {
                    String columnValue = resultSet.getString(i + 1);
                    output += String.format("%-20s", columnValue);
                }

                output += "\n";

            }
            if (output.length() == 0)
            {
                return "### No matching value ###";
            }
            System.out.println(output);
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
     * @param resultSet
     *
     * @return output string
     *
     * @throws SQLException
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
            System.out.println(output);
            return output;
        }
        catch (SQLException se)
        {
            return "### Error creating print ###";
        }
    }
}
