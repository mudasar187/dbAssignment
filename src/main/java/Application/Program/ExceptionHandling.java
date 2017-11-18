package Application.Program;

/**
 * <p>ExceptionHandling class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Class for handling SQLExceptions
 * <p>
 * Last modified 15 november 2017
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
            case 1005:
                return "\n### Tables are connected already ###";
            case 1136:
                return "\n### Column count doesn't match row count, please check file ###";
            case 1146:
                return "\n### Table(s) not exists ###";
            case 1046:
                return "\n### No database exist ###";
            case 1049:
                return "\n### No database exists ###";
            case 1050:
                return "\n### Table(s) exists ###";
            case 1051:
                return "\n### Table(s) not exists ###";
            case 1062:
                return "\n### Duplicates entry , check content of data in files before insert data ###";
            case 1054:
                return "\n### No column match, try again ###";
            case 1072:
                return "\n### Key column doesn't exists in table";
            default:
                return "\n### SQL syntax error ###";
        }
    }
}
