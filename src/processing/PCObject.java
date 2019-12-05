package processing;

import ai.Steer;
import level.Map;
import processing.core.PVector;

import java.util.List;

public abstract class PCObject extends PObject {
    public PVector vel;
    public float ori;
    public Steer steer;
    public Map map;

    /**
     * Make a new Processing object
     */
    public PCObject(Map map, Shape shape) {
        super(shape);
        this.map = map;

        vel = new PVector(0, 0);
        steer = new Steer();
        ori = 0;
    }

    public void integrate() {
        pos.add(vel);
    }

    // TODO: Add map interaction

    /**
     * Interact with other objects.
     * Implementation can be on any side providing this is called correctly
     * @param others the other objects
     */
    public abstract void interactOthers(List<PObject> others);
}
