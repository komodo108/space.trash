package python;

import gui.Console;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import python.parsers.IParser;

/**
 * Safe access to a sandboxed python interpreter
 */
public class Python {
    private PythonInterpreter py;
    private Console console;
    private PythonThread thread;
    private int step;
    private boolean running;

    /**
     * Multi-threaded python
     */
    public Python(Console console) {
        this.console = console;
        try {
            py = new PythonInterpreter();
        } catch (Exception e) { error(e.toString()); }
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

    public boolean isRunning() {
        return running;
    }

    /**
     * Set if we should run or stop
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Runs the script
     */
    public void run(String code, IParser parser) {
        String run = parser.change(code);
        String error = parser.parse(run);
        if(error == null) {
            thread = new PythonThread(py, run);
            thread.start();
        } else {
            error(error);
        }
    }

    /**
     * Step through a single line of the code
     */
    public void step() {
        // TODO: This
    }

    /**
     * Output an error to the user
     * @param error an error
     */
    private void error(String error) {
        running = false;
        step = 0;
        console.error(error);
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

    /**
     * Sets a python object to a value
     * @param variable the variable name of the python object
     * @param value the value of this object
     */
    public void set(String variable, Object value) {
        try {
            py.set(variable, value);
        } catch (Exception e) { error(e.toString()); }
    }

}