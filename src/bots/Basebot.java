package bots;

import processing.PCObject;
import processing.core.PVector;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

import java.util.List;

// TODO: This should be an actual bot, not just a wrapped value & applet
public class Basebot extends PCObject implements PythonInteractable {
    private IBasebot implementation;

    public Basebot() {
        width = 15;
        height = 15;
        pos = new PVector(10, 10);
        implementation = new IBasebot(this);
    }

    @Override
    public void render() {
        applet.fill(255, 0, 0);
        applet.ellipse(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + 2 * Math.cos(ori));
        int newy = (int) (pos.y + 2 * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / 2f, width / 2f);
    }

    @Override
    public boolean update() {
        // Deal with queue
        /*ActionQueue queue = implementation.getQueue(KEY);
        while(queue.peek() != null) {
            ActionString as = queue.remove();
            switch (as.action) {
                case MOVE: // move x units in the current facing direction
                    int x = Integer.parseInt(as.message);
                    move(x);
                    break;
            }
        } implementation.move(pos, KEY);*/
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
