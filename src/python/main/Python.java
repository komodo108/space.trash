package python.main;

import bots.Basebot;
import gui.ConsolePanel;
import python.parsers.IParser;

/**
 * Safe access to a sandboxed python interpreter
 */
public class Python {
    ConsolePanel panel;
    Basebot bot;
    private PythonThread thread;
    boolean running, abort;

    /**
     * Multi-threaded python
     */
    public Python(Basebot bot, ConsolePanel panel) {
        this.bot = bot;
        this.panel = panel;
        //this.thread = new PythonThread(this, ""); // Remove to load thread upon Go clicked
    }

    /**
     * Returns if we are running
     * @return if we are running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Setup a new thread to run user-code
     * @param code user generated code
     * @param parser a parser, which we will parse the user code with
     */
    public void setup(String code, IParser parser) {
        String run = parser.change(code);
        String error = parser.parse(run);
        if(error == null) {
            thread = new PythonThread(this, run);
        } else {
            error(error);
        }
    }

    /**
     * Start running the code
     */
    public void start() {
        try {
            thread.start();
            running = true;
        } catch (Exception e) { /* No content */ }
    }

    /**
     * Stop running the code
     */
    public void stop() {
        try {
            abort = true;
            running = false;
            // TODO: May want to reset things here?
        } catch (Exception e) { /* No content */ }
    }

    /**
     * Output an error to the user
     * @param error an error
     */
    private void error(String error) {
        running = false;
        abort = false;
        panel.error(error);
    }

}