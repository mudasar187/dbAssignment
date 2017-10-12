package Program;

public class DBHandlerTest {

    DBHandler dbHandler = new DBHandler(
            new DBConnection("src/test/resources/test-DB-right.properties"),
            new DBFileHandler());


    // TODO: Testing with real database


}