package Application;

import Application.Program.Program;

/**
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Main class to run whole the application
 * <p>
 * Last modified 19 october 2017
 */

public class RunProgram {

    public static void main(String[] args) throws InterruptedException {

        Program program = new Program();
        program.runProgram();
    }
}
