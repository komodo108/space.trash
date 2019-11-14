package python;

import org.python.util.PythonInterpreter;

public class PythonThread extends Thread {
    private Python python;
    private PythonInterpreter py;
    private String code;

    PythonThread(Python python, String code) {
        this.python = python;
        try {
            this.py = new PythonInterpreter();
            this.py.exec("from org.python.core import Py");
            this.py.exec("import python.PythonTraceFunction");
            this.py.exec("Py.getThreadState().tracefunc = python.PythonTraceFunction()");
        } catch (Exception e) { e.printStackTrace(); }
        this.code = code;
    }

    @Override
    public void run() {
        try {
            py.exec(code);
            System.out.println("Thread " + this.getId() + " has ran!!");
            python.running = false;
        } catch (Exception e) {
            System.out.println("AN ERROR HAS OCCURRED IN THREAD " + this.getId());
            e.printStackTrace();
        }
    }
}
