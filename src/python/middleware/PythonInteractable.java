package python.middleware;

/**
 * An object which interacts with Python
 * @see PythonImplementation
 */
public interface PythonInteractable {
    /**
     * Take items from the implementation queue and processes them
     */
    boolean update();

    /**
     * Gets the implementation
     * @return t=he implementation
     */
    PythonImplementation getImplementation();
}
