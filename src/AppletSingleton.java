import processing.core.PApplet;

public class AppletSingleton {
    private static AppletSingleton instance;
    private PApplet applet;

    public static AppletSingleton getInstance() {
        if(instance == null) instance = new AppletSingleton();
        return instance;
    }

    private AppletSingleton() { }

    public PApplet getApplet() {
        return applet;
    }

    public void setApplet(PApplet applet) {
        this.applet = applet;
    }
}
