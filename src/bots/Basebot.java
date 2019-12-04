package bots;

import processing.PCObject;
import processing.core.PVector;
import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

import java.util.List;

import static main.Constants.KEY;

// TODO: This should be an actual bot, not just a wrapped value & applet
public class Basebot extends PCObject implements PythonInteractable {
    private IBasebot implementation;

    public Basebot() {
        width = 15;
        height = 15;
        pos = new PVector(10, 10);
        implementation = new IBasebot();
    }

    @Override
    public void render() {
        applet.fill(255, 0, 0);
        applet.ellipse(pos.x, pos.y, width, height);
    }

    @Override
    public boolean update() {
        ActionQueue queue = implementation.getQueue(KEY);
        while(queue.peek() != null) {
            ActionString as = queue.remove();
            switch (as.action) {
                case MOVE:
                    String array[] = as.message.split(",");
                    pos.x = Integer.parseInt(array[0]);
                    //pos.y = Integer.parseInt(array[1]);
                    break;
            }
        } implementation.move((int) pos.x, KEY);
        return super.update();
    }

    @Override
    public void interactOthers(List<PCObject> others) {
        /* no content */
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
