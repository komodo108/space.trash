package python;

import gui.Console;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import python.parsers.IParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Safe access to a sandboxed python interpreter
 */
public class Python {
    private PythonInterpreter py;
    private Console console;
    private String setup;
    private InputStream is;
    private boolean running;
    private int stepper;

    public Python(Console console) {
        this.console = console;
        try {
            py = new PythonInterpreter();
        } catch (Exception e) {
            // TODO: This should output to a console
            e.printStackTrace();
        }
    }

    /**
     * Runs a single line
     * @param line the line
     * @param parser a parser
     * @implNote DO NOT ALLOW USERS TO USE THIS
     */
    public void runLine(String line, IParser parser) {
        try {
            String error = parser.parse(line);
            if(error == null) py.exec(line);
            else error(error);
        } catch (Exception e) { error(e.getMessage()); }
    }

    /* TODO: We also want to know what line we are executing
         * Get line numbers by using TraceFunction - http://python.6.x6.nabble.com/Progress-within-a-call-to-the-PythonInterpreter-td1777520.html
         * Use pdb for stepping through
     */

    /**
     * Setup a user generated code piece to be ran
     * @param code the code piece
     * @param parser the parser
     * @return whether or not the code can be run
     */
    public boolean setup(String code, IParser parser) {
        String run = parser.change(code);
        String error = parser.parse(run);
        if(error == null) {
            setup = "pdb.run(\"" + run + "\")";
            stepper = 0;
            return true;
        } else {
            error(error);
            return false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Start the current script running
     */
    public void start() {
        if(setup != null) {
            running = true;
        }
    }

    /**
     * Runs stepwise execution of the current script
     */
    public void step() {
        if(setup != null) {
            try {
                // Possible FIXME: May be too slow?? We'll see
                InputStream is = new ByteArrayInputStream((String.join("", Collections.nCopies(++stepper, "n\n")) + "q\n").getBytes(StandardCharsets.UTF_8));
                py.setIn(is);
                py.exec(setup);
            } catch (Exception e) { error(e.toString()); }
        }
    }

    /**
     * Stop execution of the current script
     */
    public void stop() {
        if(setup != null) {
            running = false;
        }
    }

    /**
     * Output an error to the user
     * @param error an error
     */
    public void error(String error) {
        // TODO: This should be a console!!
        setup = null;
        running = false;
        stepper = 0;
        System.err.println("ERROR: " + error);
    }

    /**
     * Get a python object
     * @param variable the variable name of the object
     * @return a PyObject representing the object
     */
    public PyObject get(String variable) {
        try {
            return py.get(variable);
        } catch (Exception e) { error(e.toString()); }
        return null;
    }

}
