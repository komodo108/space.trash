package bots;

import processing.core.PVector;
import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.PythonImplementation;

import static main.Constants.KEY;
import static python.middleware.Actions.MOVE;

public class IBasebot implements PythonImplementation {
    private PVector pos;
    private ActionQueue queue;

    public IBasebot() {
        this.pos = new PVector(0, 0);
        this.queue = new ActionQueue();
    }

    public void move(int x) {
        queue.add(new ActionString("" + x + ",0", MOVE));
    }

    public void move(int x, String key) {
        if(key.equals(KEY)) {
            this.pos.x = x;
        }
    }

    public ActionQueue getQueue(String key) {
        if(key.equals(KEY)) {
            return queue;
        } else return null;
    }
}