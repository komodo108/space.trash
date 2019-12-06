package level;

import bots.Basebot;
import level.container.TestContainer;
import level.item.TestItem;
import level.map.Map;
import processing.PCObject;
import processing.PObject;

import java.util.ArrayList;
import java.util.List;

import static main.Constants.TILE_SIZE;

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
        // Setup the level from the pathname
        // TODO: Generate the map & the bot
        map = new Map();
        map.add(new TestItem(300, 300));
        map.add(new TestContainer(400, 400));
        map.addWall(20, 20, 20, 2);

        bot = new Basebot(map, TILE_SIZE, TILE_SIZE);
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
