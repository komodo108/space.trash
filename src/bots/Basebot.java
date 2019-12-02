package bots;

import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

// TODO: This should be an actual bot, not just a wrapped value & applet
public class Basebot implements PythonInteractable {
    private IBasebot implementation;
    private int value;

    public Basebot(int value) {
        implementation = new IBasebot(value);
    }

    @Override
    public void update() {
        ActionQueue queue = implementation.getQueue();
        while(queue.peek() != null) {
            ActionString as = queue.remove();
            switch (as.action) {
                case PUT:
                    value = Integer.parseInt(as.message);
            }
        } implementation.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
