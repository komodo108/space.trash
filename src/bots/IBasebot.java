package bots;

import ai.Direction;
import processing.core.PVector;
import python.main.PythonAbortSingleton;
import python.main.PythonStopException;
import python.middleware.ActionQueue;
import python.middleware.PythonImplementation;

import static main.Constants.KEY;
import static main.Constants.TARGET_RADIUS;

public class IBasebot implements PythonImplementation {
    private Basebot parent;
    private ActionQueue queue;
    private PythonAbortSingleton abort = PythonAbortSingleton.getInstance();

    public IBasebot(Basebot parent) {
        this.parent = parent;
        this.queue = new ActionQueue();
    }

    public void move(int x) {
        PVector facing = new PVector((float) Math.cos(parent.ori), (float) Math.sin(parent.ori)).normalize();
        PVector target = parent.pos.copy().add(facing.mult(x));
        while(parent.pos.dist(target) > TARGET_RADIUS) {
            PVector vTarget = target.copy().sub(parent.pos);
            parent.steer.smoothFace(parent);
            parent.pos.add(parent.vel);
            parent.steer.seek(parent, vTarget);
            System.out.println(parent.pos + " " + vTarget + " " + parent.pos.dist(target));
            if(abort.isAbort()) throw new PythonStopException();

            try {
                Thread.sleep(10);
            } catch (Exception e) { /* no content */ }
        }
    }

    public void face(char direction) {
        Direction d = Direction.fromChar(direction);
        if(d != null) {
            float tori = (float) Math.atan2(d.getMove().y, d.getMove().x);
            parent.ori = tori;
        }
    }

    public ActionQueue getQueue(String key) {
        if(key.equals(KEY)) {
            return queue;
        } else return null;
    }
}