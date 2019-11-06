package python;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import python.parsers.IParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Safe access to a sandboxed python interpreter
 */
public class Python {
    private PythonInterpreter py;
    private String setup;
    private InputStream is;
    private boolean running;
    //private CodeConsole console;

    public Python() {
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
     */
    public void setup(String code, IParser parser) {
        String run = parser.change(code);
        String error = parser.parse(run);
        if(error == null) {
            setup = "pdb.run(\"" + run + "\")";
        } else error(error);
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Start the current script running
     */
    public void start() {
        running = true;
    }

    /**
     * Runs stepwise execution of the current script
     */
    public void step() {
        if(setup != null) {
            try {
                /* TODO: This seems to be better in terms of interactivity than the other approach but still has issues:
                        * PDB runs within python, which we can't do anything with while it is running, so we must prepare the input stream before we run the python
                        * Can't just input go to the next line, as it may not exist or be more lines
                        * We need to change the number of ns as we go
                 */
                InputStream is = new ByteArrayInputStream("n\n".getBytes(StandardCharsets.UTF_8));
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
            try {
                InputStream is = new ByteArrayInputStream("q\n".getBytes(StandardCharsets.UTF_8));
                py.setIn(is);
                py.exec(setup);
                running = false;
            } catch (Exception e) { error(e.toString()); }
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
