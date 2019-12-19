package processing;

import main.AppletSingleton;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.sound.SoundFile;

import java.util.HashMap;
import java.util.Map;

import static main.Constants.*;

public class Assets {
    private static Assets ourInstance = new Assets();
    public static Assets getInstance() {
        return ourInstance;
    }

    private PApplet applet = AppletSingleton.getInstance().getApplet();
    private Map<String, PImage> images;
    private Map<String, PFont> fonts;
    private Map<String, SoundFile> sounds;

    private static final String FONT_NAME = "Arial";

    private Assets() {
        images = new HashMap<>();
        fonts = new HashMap<>();
        sounds = new HashMap<>();

        // Cut-scenes
        loadImage("Title.png");
        loadImage("Fired1.png");
        loadImage("Fired2.png");
        loadImage("Fired3.png");
        loadImage("Fired4.png");
        loadImage("Fired5.png");
        loadImage("Fired6.png");
        loadImage("ThankYou.png");

        // Maps
        loadImage("Factory.png");
        loadImage("Grass.png");
        loadImage("Mars.png");
        loadImage("Creepy.png");
        loadImage("SciFi.png");

        // Audio
        loadSound("music.mp3");

        // Items
        loadImage("Key.png");

        // Fonts
        loadFont("small", FONT_NAME, 20);
        loadFont("medium", FONT_NAME, 48);
        loadFont("large", FONT_NAME, 70);
        loadFont("slarge", "Chiller", 100);
    }

    private void loadImage(String path) {
        String name = path.split("[.]")[0];
        images.put(name, applet.loadImage(IMAGE_DIR + path));
    }

    private void loadFont(String name, String font, int size) {
        fonts.put(name, applet.createFont(font, size));
    }

    private void loadSound(String path) {
        String name = path.split("[.]")[0];
        sounds.put(name, new SoundFile(applet, AUDIO_DIR + path));
    }

    public PImage getImage(String name) {
        return images.getOrDefault(name, null);
    }

    public PFont getFont(String name) {
        return fonts.getOrDefault(name, null);
    }

    public void play(String name) {
        if(sounds.containsKey(name)) {
            SoundFile file = sounds.get(name);
            file.amp(VOLUME);
            new Thread(file::play).start();
        }
    }

    public void loop(String name) {
        if(sounds.containsKey(name)) {
            SoundFile file = sounds.get(name);
            file.amp(VOLUME);
            new Thread(file::loop).start();
        }
    }

    public void stop() {
        for(SoundFile file : sounds.values()) {
            if(file.isPlaying()) file.stop();
        }
    }

    public int getFontSize(String name) {
        PFont font = fonts.getOrDefault(name, null);
        if(font != null) return font.getSize();
        else return 1;
    }
}
