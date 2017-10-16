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
                return "### Column count doesn't match row count, please check file ###";
            case 1146:
                return "### Table not exists ###";
            case 1046:
                return "### No database exist ###";
            case 1049:
                return "### No database exists ###";
            case 1050:
                return "### Table exists ###";
            case 1051:
                return "### Table not exists ###";
            case 1062:
                return "### Duplicates entry , refresh table before insert data ###";
            case 1054:
                return "### No column match, try again ###";
            default:
                return "### Uknown error ###";
        }
    }
}
