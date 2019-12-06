package bots;

import ai.Direction;
import level.container.Container;
import level.item.Item;
import processing.CollisionDetect;
import processing.core.PVector;
import python.main.PythonError;
import python.middleware.ActionQueue;
import python.middleware.PythonImplementation;
import python.middleware.Pythond;

import java.util.ArrayList;
import java.util.List;

import static main.Constants.KEY;
import static main.Constants.TARGET_RADIUS;

public class IBasebot implements PythonImplementation {
    private Basebot parent;
    private ActionQueue queue;

    public IBasebot(Basebot parent) {
        this.parent = parent;
        this.queue = new ActionQueue();
    }

    @Pythond
    public void move(int x) {
        // Get a vector representing where we are facing & find the targeted location from it
        PVector facing = new PVector((float) Math.cos(parent.ori), (float) Math.sin(parent.ori)).normalize();
        PVector target = parent.pos.copy().add(facing.mult(x)); // TODO: * 10?

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

    @Pythond
    public void hold() {
        List<Item> items = parent.map.getItems();
        List<Item> removed = new ArrayList<>();
        for(Item item : items) {
            if(CollisionDetect.isInside(item, parent) && !item.isDead() && parent.getHeld() == null) {
                removed.add(item);
                parent.setHeld(item);
            }
        } items.removeAll(removed);
    }

    @Pythond
    public void interact() {
        List<Container> containers = parent.map.getContainers();
        for(Container container : containers) {
            if(CollisionDetect.isInside(container, parent) && parent.getHeld() != null) {
                if(container.interact(parent.getHeld())) parent.setHeld(container.getItem());
                else parent.setHeld(null);
            }
        }
    }

    @Pythond
    public int getX() {
        return (int) parent.pos.x;
    }

    @Pythond
    public int getY() {
        return (int) parent.pos.y;
    }

    public ActionQueue getQueue(String key) {
        if(key.equals(KEY)) {
            return queue;
        } else return null;
    }
}