package python;

import bots.Basebot;
import gui.Console;
import python.parsers.IParser;

/**
 * Safe access to a sandboxed python interpreter
 */
public class Python {
    Console console;
    Basebot bot;
    private PythonThread thread;
    private int step;
    boolean running, abort;

    /**
     * Multi-threaded python
     */
    public Python(Basebot bot, Console console) {
        this.bot = bot;
        this.console = console;
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
            thread.setBot(bot);
        } else {
            error(error);
        }
    }

    /**
     * Start running the code
     */
    public void start() {
        if(thread != null) {
            thread.start();
            running = true;
        }
    }

    /**
     * Stop running the code
     */
    public void stop() {
        if(thread != null) {
            abort = true;
            running = false;
        }
    }

    /**
     * Step through a single line of the code
     */
    public void step() {
        // TODO: Remove stepping
    }

    /**
     * Output an error to the user
     * @param error an error
     */
    private void error(String error) {
        running = false;
        abort = false;
        step = 0;
        console.error(error);
    }

}