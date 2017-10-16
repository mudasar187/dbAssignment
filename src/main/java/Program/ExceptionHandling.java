package Program;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Class for handling exceptions
 *
 * Last modified 16 october 2017
 *
 */

public class ExceptionHandling {

    public String SQLException(int se)
    {
        switch (se) {
            case 1136:
                return "### 1136 Column count doesn't match row count, please check file ###";
            case 1146:
                return "### 1146 Table not exists ###";
            case 1046:
                return "### 1046 No database exist ###";
            case 1049:
                return "### 1049 No database exists ###";
            case 1050:
                return "### 1050 Table exists ###";
            case 1051:
                return "### 1051 Table not exists ###";
            case 1062:
                return "### 1062 Duplicates entry , refresh table before insert data ###";
            case 1054:
                return "### No column exists, try again ###";
            default:
                return "### Uknown error ###";
        }
    }
}
