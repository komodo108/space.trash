package python.main;

import org.python.util.PythonInterpreter;

public class PythonThread extends Thread {
    private Python python;
    private String code;
    private PythonTraceFunction ptf;
    private PythonInterpreter py;
    private PythonAbortSingleton abort = PythonAbortSingleton.getInstance();

    /**
     * A thread running some Python
     */
    PythonThread(Python python, String code) {
        this.python = python;
        this.code = code;
        this.ptf = new PythonTraceFunction();
        this.py = new PythonInterpreter();
    }

    /**
     * Setup before we run
     */
    private void setup() {
        py.exec("from org.python.core import Py");
        py.exec("import python.main.PythonTraceFunction");
        py.set("ptf", ptf);
        py.exec("Py.getThreadState().tracefunc = ptf");
        py.set("console", python.panel.getImplementation());
        py.set("__impl", python.bot.getImplementation());
        py.set("__bot", python.bot);
    }

    /**
     * Abort execution
     */
    private void abort() {
        python.running = false;
        abort.setAbort(false);
    }

    @Override
    public void run() {
        try {
            setup();
            py.exec(code);
            abort();
        } catch (Exception e) {
            abort();
            if(!(e instanceof PythonStopException)) {
                python.panel.error(e.toString());
            }
        }
    }
}
