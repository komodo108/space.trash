package python.main;

public class PythonAbortSingleton {
    private static PythonAbortSingleton ourInstance = new PythonAbortSingleton();
    public static PythonAbortSingleton getInstance() {
        return ourInstance;
    }

    private boolean abort;

    private PythonAbortSingleton() {
        abort = false;
    }

    public void setAbort(boolean abort) {
        this.abort = abort;
    }

    public boolean isAbort() {
        return abort;
    }
}
