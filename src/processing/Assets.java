package processing;

import main.AppletSingleton;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.HashMap;
import java.util.Map;

import static main.Constants.IMAGE_DIR;

public class Assets {
    private static Assets ourInstance = new Assets();
    public static Assets getInstance() {
        return ourInstance;
    }

    private PApplet applet = AppletSingleton.getInstance().getApplet();
    private Map<String, PImage> images;
    private Map<String, PFont> fonts;

    private static final String FONT_NAME = "Arial";

    private Assets() {
        images = new HashMap<>();
        fonts = new HashMap<>();

        // Cut-scenes
        loadImage("Title.png");
        loadImage("Fired.png");
        loadImage("Idea.png");

        // Maps
        loadImage("Factory.png");
        loadImage("Grass.png");
        loadImage("Mars.png");

        // Items
        loadImage("Key.png");

        // Fonts
        loadFont("small", FONT_NAME, 12);
        loadFont("large", FONT_NAME, 70);
    }

    private void loadImage(String path) {
        String name = path.split("[.]")[0];
        images.put(name, applet.loadImage(IMAGE_DIR + path));
    }

    private void loadFont(String name, String font, int size) {
        fonts.put(name, applet.createFont(font, size));
    }

    public PImage getImage(String name) {
        return images.getOrDefault(name, null);
    }

    public PFont getFont(String name) {
        return fonts.getOrDefault(name, null);
    }

    public int getFontSize(String name) {
        PFont font = fonts.getOrDefault(name, null);
        if(font != null) return font.getSize();
        else return 1;
    }
}
