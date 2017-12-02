package Application;

import Application.Program.Program;

/**
 * <p>RunProgram class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Main class to run whole the application
 * <p>
 * Last modified 18 november 2017
 */
public class RunProgram {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.InterruptedException if any.
     */
    public static void main(String[] args) throws InterruptedException {

        Program program = new Program();


        program.runProgram();
    }
}
