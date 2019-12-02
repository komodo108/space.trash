package bots;

import main.AppletSingleton;
import processing.core.PApplet;

// TODO: This should be an actual bot, not just a wrapped value & applet
// TODO: The bot is discoupled from the actual python, so have a queue of actions like in Console
public class Basebot {
    private int value;
    private PApplet applet;

    public Basebot(int value) {
        this.value = value;
        this.applet = AppletSingleton.getInstance().getApplet();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public PApplet getApplet() {
        return applet;
    }

    public void draw() {
        applet.fill(255, 0, 0);
        applet.rect(90, 90, 100, 100);
    }
}
