package bots;

import ai.Direction;
import processing.core.PVector;
import python.main.PythonAbortSingleton;
import python.main.PythonError;
import python.middleware.ActionQueue;
import python.middleware.PythonImplementation;
import python.middleware.Pythond;

import static common.Constants.KEY;
import static common.Constants.TARGET_RADIUS;

public class IBasebot implements PythonImplementation {
    private Basebot parent;
    private ActionQueue queue;
    private PythonAbortSingleton abort = PythonAbortSingleton.getInstance();

    public IBasebot(Basebot parent) {
        this.parent = parent;
        this.queue = new ActionQueue();
    }

    @Pythond
    public void move(int x) {
        // Get a vector representing where we are facing & find the targeted location from it
        PVector facing = new PVector((float) Math.cos(parent.ori), (float) Math.sin(parent.ori)).normalize();
        PVector target = parent.pos.copy().add(facing.mult(x));

        // Block Python until we get to the destination
        while(parent.pos.dist(target) > TARGET_RADIUS) {
            // Move towards the target
            PVector vTarget = target.copy().sub(parent.pos);
            parent.steer.smoothFace(parent);
            parent.pos.add(parent.vel);
            parent.steer.seek(parent, vTarget);

            // We are threading, so ensure the user can come out
            threading();
        }
    }

    @Pythond
    public void face(char direction) {
        Direction d = Direction.fromChar(direction);
        if(d != null) {
            float tori = (float) Math.atan2(d.getMove().y, d.getMove().x);
            parent.ori = tori;
        } else throw new PythonError("Cannot go in direction '" + direction + "'.");
    }

    public ActionQueue getQueue(String key) {
        if(key.equals(KEY)) {
            return queue;
        } else return null;
    }
}