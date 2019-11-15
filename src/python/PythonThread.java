package python;

import org.python.util.PythonInterpreter;

public class PythonThread extends Thread {
    private Python python;
    private String code;
    private PythonTraceFunction ptf;

    PythonThread(Python python, String code) {
        this.python = python;
        this.code = code;
        this.ptf = new PythonTraceFunction();
    }

    @Override
    public void run() {
        try {
            PythonInterpreter py = new PythonInterpreter();
            System.out.println("Thread " + this.getId() + " has started!!");
            py.exec("from org.python.core import Py");
            py.exec("import python.PythonTraceFunction");
            py.set("ptf", ptf);
            py.exec("Py.getThreadState().tracefunc = ptf");
            py.set("console", python.console);

            py.exec(code);
            System.out.println("Thread " + this.getId() + " has ran!!");
            python.running = false;
        } catch (Exception e) {
            python.running = false;
            System.err.println("AN ERROR HAS OCCURRED IN THREAD " + this.getId());
            e.printStackTrace();
            python.console.error(e.toString());
        }
    }
}
