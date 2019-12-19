package bots;

import level.container.Container;
import level.item.Item;
import level.map.Cell;
import processing.CollisionDetect;
import processing.core.PVector;
import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.PythonImplementation;
import python.middleware.Pythond;

import java.util.ArrayList;
import java.util.List;

import static main.Constants.*;
import static python.middleware.Actions.ATTACK;

public class Basebot implements PythonImplementation {
    private RealBasebot parent;
    private ActionQueue queue;
    private int timer;

    public Basebot() {
        this.parent = BasebotSingleton.getInstance().getBot();
        this.queue = new ActionQueue();
        timer = 0;
    }

    /**
     * Attempts to move to the position x units away in the current direction
     * Note this method does not check if the location is sensible to go to
     * @param x the number of tiles to move
     */
    @Pythond
    public void move(int x) {
        // Get a vector representing where we are facing & find the targeted location from it
        PVector facing = new PVector((float) Math.cos(parent.ori), (float) Math.sin(parent.ori)).normalize();
        PVector target = parent.pos.copy().add(facing.mult(x * TILE_SIZE));

        // Block Python until we get to the destination or we can't move anymore
        while (parent.pos.dist(target) > TARGET_RADIUS) {
            // Move towards the target
            PVector vTarget = target.copy().sub(parent.pos);
            parent.integrate();
            parent.steer.seek(parent, vTarget);

            // We are threading, so ensure the user can come out
            threading();
        }
    }

    /**
     * Moves the player left <code>degrees</code> degrees
     * @param degrees the amount of degrees to move
     */
    @Pythond
    public void left(int degrees) {
        parent.ori -= (float) Math.toRadians(degrees);
    }

    /**
     * Moves the player right <code>degrees</code> degrees
     * @param degrees the amount of degrees to move
     */
    @Pythond
    public void right(int degrees) {
        parent.ori += (float) Math.toRadians(degrees);
    }

    /**
     * Attempts to hold an item the player is on top of
     */
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

    /**
     * Attempts to interact with a container that a player is on top of
     */
    @Pythond
    public void interact() {
        List<Container> containers = parent.map.getContainers();
        for(Container container : containers) {
            if(CollisionDetect.isInside(container, parent) && parent.getHeld() != null) {
                if(container.interact(parent.getHeld())) parent.setHeld(container.getItem());
                else parent.setHeld(null);
            } threading();
        }
    }

    /**
     * Attempts to attack enemies around itself
     */
    @Pythond
    public void attack() {
        queue.add(new ActionString("", ATTACK));
        while(timer < ATTACK_TIME * 100) {
            threading();
            timer++;
        }
    }

    /**
     * Attempts to align the bot to a direction
     */
    @Pythond
    public void align() {
        if(parent.ori >= -Math.PI-(Math.PI/4) && parent.ori < -(Math.PI/2)-(Math.PI/4)) parent.ori = (float) -Math.PI;
        else if(parent.ori >= -(Math.PI/2)-(Math.PI/4) && parent.ori < -(Math.PI/4)) parent.ori = (float) -Math.PI / 2;
        else if(parent.ori >= -(Math.PI/4) && parent.ori < (Math.PI/2)-(Math.PI/4)) parent.ori = 0;
        else if(parent.ori >= (Math.PI/2)-(Math.PI/4) && parent.ori < Math.PI-(Math.PI/4)) parent.ori = (float) Math.PI / 2;
        else parent.ori = (float) Math.PI;
    }

    /**
     * Naively gets if a player can move x units in the current direction
     * @param x the amount of units to move
     * @return if the target position is in the map and not a wall
     */
    @Pythond
    public boolean canMove(int x) {
        PVector facing = new PVector((float) Math.cos(parent.ori), (float) Math.sin(parent.ori)).normalize();
        PVector target = parent.pos.copy().add(facing.mult(x * TILE_SIZE));

        List<Cell> cells = parent.map.getCells(parent.shape, target, parent.width, parent.height);
        return parent.canMove(cells, target);
    }

    /**
     * Get the players X position
     * @return the players X position
     */
    @Pythond
    public int getX() {
        return (int) (parent.pos.x / TILE_SIZE);
    }

    /**
     * Get the players Y position
     * @return the players Y position
     */
    @Pythond
    public int getY() {
        return (int) (parent.pos.y / TILE_SIZE);
    }

    /**
     * Returns the direction the player is facing in degrees
     * @return the direction the player is facing in degrees
     */
    @Pythond
    public double getDirection() {
        return Math.toDegrees(parent.ori + Math.PI);
    }

    public ActionQueue getQueue(String key) {
        if(key.equals(KEY)) {
            return queue;
        } else return null;
    }
}