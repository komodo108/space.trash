package level;

import bots.Basebot;
import level.map.LevelLoader;
import level.map.Map;
import level.win.Win;
import processing.PCObject;
import processing.PObject;

import java.util.ArrayList;
import java.util.List;

public class Level {
    /* TODO: A Level is a map & enemies with a win condition which uses a specific bot.
        The player must meet this win condition for the next level to load.
    */
    private List<PObject> objects;
    private Basebot bot;
    private Map map;
    private Win win;

    public Level(String pathname) {
        objects = new ArrayList<>();

        // Get objects from the map loader
        LevelLoader loader = new LevelLoader(pathname);
        map = loader.getMap();

        // TODO: Load the enemies


        // TODO: Load the default code, tutorial message, etc.
        win = loader.getWin();

        // Load the bot
        bot = loader.getBot();
        objects.add(bot);
        win.setObjects(objects);
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
        return win.isWin(); // win condition
    }
}
