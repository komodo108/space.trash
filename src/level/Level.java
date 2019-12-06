package level;

import bots.Basebot;
import level.enemy.Enemy;
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

        // Load the enemies
        List<Enemy> enemies = loader.getEnemies();
        objects.addAll(enemies);

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
