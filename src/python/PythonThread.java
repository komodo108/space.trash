package python;

import org.python.util.PythonInterpreter;

public class PythonThread extends Thread {
    private PythonInterpreter py;
    private String code;

    public PythonThread(PythonInterpreter py, String code) {
        this.py = py;
        this.code = code;
    }

    @Override
    public void run() {
        try {
            while(true) {
                // TODO: Fix this
                py.exec(code);
                System.out.println("Thread " + this.getId() + " is running!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
