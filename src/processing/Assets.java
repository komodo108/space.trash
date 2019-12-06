package processing;

import main.AppletSingleton;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static Assets ourInstance = new Assets();
    public static Assets getInstance() {
        return ourInstance;
    }

    private PApplet applet = AppletSingleton.getInstance().getApplet();
    private Map<String, PImage> images;
    private static final String IMAGE_DIR = "images/";

    private Assets() {
        images = new HashMap<>();
        loadImage("TestMap.png");
    }

    private void loadImage(String path) {
        String name = path.split("[.]")[0];
        images.put(name, applet.loadImage(IMAGE_DIR + path));
    }

    public PImage getImage(String name) {
        return images.getOrDefault(name, null);
    }
}
