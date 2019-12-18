package python.main;

import bots.RealBasebot;
import gui.panel.ConsolePanel;
import python.parsers.IParser;

/**
 * Safe access to a sandboxed python interpreter
 */
public class Python {
    ConsolePanel panel;
    RealBasebot bot;
    private PythonThread thread;
    private PythonAbortSingleton abort = PythonAbortSingleton.getInstance();
    boolean running;

    /**
     * Multi-threaded python
     */
    public Python(RealBasebot bot, ConsolePanel panel) {
        this.bot = bot;
        this.panel = panel;
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
     * @param defaultCode the level generated code
     * @param parser a parser, which we will parse the user code with
     * @param defaultParser the parser which the default code will be parsed with
     */
    public void setup(String code, String defaultCode, IParser parser, IParser defaultParser) {
        String run = parser.change(code);
        String rundefault = defaultParser.change(defaultCode);
        String error = parser.parse(run);
        String errorDefault = defaultParser.parse(rundefault);
        if(error == null && errorDefault == null) {
            thread = new PythonThread(this, rundefault + run);
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
            abort.setAbort(true);
            running = false;
        } catch (Exception e) { /* No content */ }
    }

    /**
     * Output an error to the user
     * @param error an error
     */
    private void error(String error) {
        running = false;
        abort.setAbort(false);
        panel.error(error);
    }

}