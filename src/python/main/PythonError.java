package python.main;

/**
 * An error occurred within the Java part of Python execution
 */
public class PythonError extends RuntimeException {

    /**
     * Make a new exception
     * @param message the message
     */
    public PythonError(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.getMessage();
    }
}
