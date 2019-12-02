package python.middleware;

/**
 * The implementation of an object which interacts with Python<br>
 * <b>NOTE: EVERY METHOD HERE IS CALLABLE BY PYTHON!</b>
 * Use the <code>key</code> variable and polymorphism to mask methods which the user cannot use
 * @see PythonInteractable
 * @see bots.IBasebot
 * @see main.Constants
 */
public interface PythonImplementation {
    /**
     * Get the queue of actions waiting to be done
     * @param key the special key to keep users from using this directly
     * @return the queue of actions
     */
    ActionQueue getQueue(String key);
}
