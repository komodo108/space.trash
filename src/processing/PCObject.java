package processing;

import processing.core.PVector;

import java.util.List;

public abstract class PCObject extends PObject {
    public PVector pos, vel;
    public float width, height;
    public float ori, rot;
    protected boolean dead = false;

    /**
     * Make a new Processing object
     */
    public PCObject() {
        super();

        pos = new PVector(0, 0);
        vel = new PVector(0, 0);
    }

    @Override
    public boolean update() {
        return dead;
    }

    public void integrate() {
        pos.add(vel);
    }

    /**
     * Interact with other objects.
     * Implementation can be on any side providing this is called correctly
     * @param others the other objects
     */
    public abstract void interactOthers(List<PCObject> others);

    public boolean isDead() {
        return dead;
    }

    public void setDead() {
        this.dead = true;
    }

}
