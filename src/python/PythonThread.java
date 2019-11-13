package python;

import org.python.util.PythonInterpreter;

public class PythonThread extends Thread {
    private Python python;
    private PythonInterpreter py;
    private String code;

    public PythonThread(Python python, PythonInterpreter py, String code) {
        this.python = python;
        this.py = py;
        this.code = code;

    }

    @Override
    public void run() {
        try {
            py.exec(code);
            System.out.println("Thread " + this.getId() + " has ran!!");
            python.running = false;
        } catch (Exception e) {
            System.out.println("AN ERROR HAS OCCURED IN THREAD " + this.getId());
            e.printStackTrace();
        }
    }
}
