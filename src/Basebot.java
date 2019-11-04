import processing.core.PApplet;

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

    public void draw() {
        applet.fill(255, 0, 0);
        applet.rect(90, 90, 100, 100);
    }
}
