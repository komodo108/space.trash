package level;

import bots.Basebot;
import level.map.LevelLoader;
import level.map.Map;
import processing.PCObject;
import processing.PObject;

import java.util.ArrayList;
import java.util.List;

public class Level {
    /* TODO: A Level is a map & enemies with a win condition which uses a specific bot.
        The player must meet this win condition for the next level to load.
    */

    // TODO: We will also need to add default code & a tutorial message to this
    private List<PObject> objects;
    private Basebot bot;
    private Map map;

    public Level(String pathname) {
        objects = new ArrayList<>();

        // Get objects from the map loader
        LevelLoader loader = new LevelLoader(pathname);
        map = loader.getMap();

        // TODO: Load the enemies


        // TODO: Load the win condition, default code, tutorial message

        // Load the bot
        bot = loader.getBot();
        objects.add(bot);
    }

    public Basebot getBot() {
        return bot;
    }

    public Map getMap() {
        return map;
    }

    // Return true when the level is done
    public boolean update() {
        List<PObject> removed = new ArrayList<>();
        for(PObject object : objects) {
            if(object.update()) removed.add(object);
            if(!object.isDead()) {
                if(object instanceof PCObject) ((PCObject) object).interactOthers(objects);
                object.render();
            }
        } objects.removeAll(removed);
        return map.getContainers().get(0).getHeld().size() == 1; // win condition
    }
}
