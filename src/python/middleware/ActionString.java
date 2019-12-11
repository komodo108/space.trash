package python.middleware;

public class ActionString {
    public String message;
    public Actions action;

    /**
     * Make a new action string
     * @param message serialised form of parameters for the action
     * @param action the action
     */
    public ActionString(String message, Actions action) {
        this.message = message;
        this.action = action;
    }
}
