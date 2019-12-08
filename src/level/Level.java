package level;

import bots.RealBasebot;
import gui.DefaultCode;
import gui.Tutorial;
import level.enemy.Enemy;
import level.map.Map;
import level.win.Win;
import processing.PCObject;
import processing.PObject;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<PObject> objects;
    private RealBasebot bot;
    private Map map;
    private Win win;
    private Tutorial tutorial;
    private DefaultCode code;

    public Level(String pathname) {
        objects = new ArrayList<>();

        // Get objects from the map loader
        LevelLoader loader = new LevelLoader(pathname);
        map = loader.getMap();

        // Load the enemies
        List<Enemy> enemies = loader.getEnemies();
        objects.addAll(enemies);

        // Load the default code, tutorial message, etc.
        win = loader.getWin();
        tutorial = loader.getTutorial();
        code = loader.getCode();

        // Load the bot
        bot = loader.getBot();
        objects.add(bot);
        win.setObjects(objects);
    }

    public RealBasebot getBot() {
        return bot;
    }

    public Map getMap() {
        return map;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public DefaultCode getCode() {
        return code;
    }

    /**
     * Update the level
     * @param update true if an update is to be done
     * @return if the level is over
     */
    public boolean update(boolean update) {
        List<PObject> removed = new ArrayList<>();
        for(PObject object : objects) {
            if(update && object.update()) removed.add(object);
            if(!object.isDead()) {
                if(update && object instanceof PCObject) ((PCObject) object).interactOthers(objects);
                object.render();
            }
        } objects.removeAll(removed);
        return win.isWin();
    }
}
