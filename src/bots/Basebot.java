package bots;

import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

import static main.Constants.KEY;

// TODO: This should be an actual bot, not just a wrapped value & applet
public class Basebot implements PythonInteractable {
    private IBasebot implementation;
    private int value;

    public Basebot(int value) {
        this.value = value;
        implementation = new IBasebot(value);
    }

    @Override
    public void update() {
        ActionQueue queue = implementation.getQueue(KEY);
        while(queue.peek() != null) {
            ActionString as = queue.remove();
            switch (as.action) {
                case PUT:
                    value = Integer.parseInt(as.message);
                    break;
            }
        } implementation.setValue(value, KEY);
    }

    public int getValue() {
        return value;
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
