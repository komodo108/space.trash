package processing;

import main.AppletSingleton;
import processing.core.PApplet;

public abstract class PObject {
    public PApplet applet;

    /**
     * Make a new Processing object
     */
    public PObject() {
        this.applet = AppletSingleton.getInstance().getApplet();
    }

    /**
     * Called to draw the object to the screen
     */
    public abstract void render();

    /**
     * Called to update the object
     */
    public abstract boolean update();
}
