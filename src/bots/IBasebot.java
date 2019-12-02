package bots;

import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.Actions;
import python.middleware.PythonImplementation;

import static main.Constants.KEY;

public class IBasebot implements PythonImplementation {
    private int value;
    private ActionQueue queue;

    public IBasebot(int value) {
        this.value = value;
        this.queue = new ActionQueue();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        queue.add(new ActionString(value + "", Actions.PUT));
    }

    public void setValue(int value, String key) {
        if(key.equals(KEY)) {
            this.value = value;
        }
    }

    public ActionQueue getQueue(String key) {
        if(key.equals(KEY)) {
            return queue;
        } else return null;
    }
}