package Application.Program;

/**
 * <p>ExceptionHandling class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Class for handling SQLExceptions
 * <p>
 * Last modified 10 november 2017
 */
public class ExceptionHandling {

    /**
     * <p>SQLException.</p>
     *
     * @param se a int.
     * @return a {@link java.lang.String} object.
     */
    public String SQLException(int se)
    {

        switch (se)
        {
            case 1136:
                return "### Column count doesn't match row count, please check file ###";
            case 1146:
                return "### Table(s) not exists ###";
            case 1046:
                return "### No database exist ###";
            case 1049:
                return "### No database exists ###";
            case 1050:
                return "### Table(s) exists ###";
            case 1051:
                return "### Table(s) not exists ###";
            case 1062:
                return "### Duplicates entry , check content of data in files before insert data ###";
            case 1054:
                return "### No column match, try again ###";
            default:
                return "### SQL syntax error ###";
        }
    }
}
