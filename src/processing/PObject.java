package processing;

import main.AppletSingleton;
import processing.core.PApplet;
import processing.core.PVector;

import static main.Constants.TILE_SIZE;

public abstract class PObject {
    public PApplet applet;
    public PVector pos;
    public Shape shape;
    public float width, height;
    protected boolean dead = false;

    /**
     * Make a new Processing object
     */
    public PObject(Shape shape) {
        this.applet = AppletSingleton.getInstance().getApplet();
        this.shape = shape;

        pos = new PVector(0, 0);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    /**
     * Called to draw the object to the screen
     */
    public abstract void render();

    /**
     * Called to update the object
     * @return true if the object is to be removed
     */
    public boolean update() {
        return dead;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead() {
        this.dead = true;
    }
}
