package python.middleware;

import main.Constants;
import python.main.PythonAbortSingleton;
import python.main.PythonStopException;

import static main.Constants.SHORT_SLEEP_TIME;

/**
 * The implementation of an object which interacts with Python<br>
 * <b>NOTE: EVERY METHOD HERE IS CALLABLE BY PYTHON!</b>
 * Use the <code>key</code> variable and polymorphism to mask methods which the user cannot use
 * @see PythonInteractable
 * @see bots.IBasebot
 * @see Constants
 */
public interface PythonImplementation {
    /**
     * Get the queue of actions waiting to be done
     * @param key the special key to keep users from using this directly
     * @return the queue of actions
     */
    ActionQueue getQueue(String key);

    /**
     * Ensure this is called within any implementations with loops which will be implemented on the Python side.
     * This is to ensure that the user can still abort execution of the loop & to slow down the loop so the user can see updates in real time.
     */
    default void threading() {
        if(PythonAbortSingleton.getInstance().isAbort()) throw new PythonStopException();
        try {
            Thread.sleep(SHORT_SLEEP_TIME);
        } catch (Exception e) { /* no content */ }
    }
}
