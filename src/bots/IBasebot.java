package bots;

import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.Actions;
import python.middleware.PythonImplementation;

public class IBasebot implements PythonImplementation {
    int value;

    IBasebot(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        queue.add(new ActionString(value + "", Actions.PUT));
    }

    ActionQueue getQueue() {
        return queue;
    }
}