package level.map;

import bots.Basebot;
import level.container.Container;
import level.enemy.Enemy;
import level.item.Item;
import level.win.Win;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static level.map.CellTypes.GOAL;
import static main.Constants.TILE_SIZE;

public class LevelLoader {
    private static final String LEVEL_DIR = "data/level/";
    private Map map;
    private Basebot bot;
    private Win win;
    private List<Enemy> enemies;

    public LevelLoader(String pathname) {
        InputStream is = null;
        try {
            is = new FileInputStream(LEVEL_DIR + pathname);
        } catch (IOException e) {
            e.printStackTrace();
        } JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        enemies = new ArrayList<>();

        // Load the map
        JSONObject jsonMap = object.getJSONObject("map");
        String settingName = jsonMap.getString("setting").toUpperCase();
        map = new Map(Settings.valueOf(settingName));

        // Load the walls
        try {
            JSONArray walls = jsonMap.getJSONArray("walls");
            for (int i = 0; i < walls.length(); i++) {
                JSONObject wall = walls.getJSONObject(i).getJSONObject("wall");
                int x = wall.getInt("x");
                int y = wall.getInt("y");
                int width = wall.getInt("width");
                int height = wall.getInt("height");
                map.addWall(x, y, width, height);
            }
        } catch (JSONException e) { /* no content */ }

        // Load goals
        try {
            JSONObject goal = jsonMap.getJSONObject("goal");
            int x = goal.getInt("x");
            int y = goal.getInt("y");
            map.setCell(x, y, GOAL);
        } catch (JSONException e) { /* no content */ }

        // Load items using reflection
        try {
            JSONArray items = jsonMap.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i).getJSONObject("item");
                String type = item.getString("type");
                int x = item.getInt("x");
                int y = item.getInt("y");

                Class<?> itemc = Class.forName("level.item." + toProper(type.toLowerCase()) + "Item");
                Constructor<?> cons = itemc.getConstructor(int.class, int.class);
                map.add((Item) cons.newInstance(x * TILE_SIZE, y * TILE_SIZE));
            }
        } catch (Exception e) {
            if(!(e instanceof JSONException)) e.printStackTrace();
        }

        // Load containers using reflection
        try {
            JSONArray containers = jsonMap.getJSONArray("containers");
            for (int i = 0; i < containers.length(); i++) {
                JSONObject container = containers.getJSONObject(i).getJSONObject("container");
                String type = container.getString("type");
                int x = container.getInt("x");
                int y = container.getInt("y");

                Class<?> containerc = Class.forName("level.container." + toProper(type.toLowerCase()) + "Container");
                Constructor<?> cons = containerc.getConstructor(int.class, int.class);
                map.add((Container) cons.newInstance(x * TILE_SIZE, y * TILE_SIZE));
            }
        } catch (Exception e) {
            if(!(e instanceof JSONException)) e.printStackTrace();
        }

        // Load the bot
        JSONObject botobject = object.getJSONObject("bot");
        int x = botobject.getInt("x");
        int y = botobject.getInt("y");
        bot = new Basebot(map, x * TILE_SIZE, y * TILE_SIZE);

        // Load enemies using reflection
        try {
            JSONArray enemiesJson = object.getJSONArray("enemies");
            for (int i = 0; i < enemiesJson.length(); i++) {
                JSONObject enemy = enemiesJson.getJSONObject(i).getJSONObject("enemy");
                String type = enemy.getString("type");
                x = enemy.getInt("x");
                y = enemy.getInt("y");

                Class<?> emenyc = Class.forName("level.enemy." + toProper(type.toLowerCase()) + "Enemy");
                Constructor<?> cons = emenyc.getConstructor(Map.class, Basebot.class, int.class, int.class);
                enemies.add((Enemy) cons.newInstance(map, bot, x * TILE_SIZE, y * TILE_SIZE));
            }
        } catch (Exception e) {
            if(!(e instanceof JSONException)) e.printStackTrace();
        }

        // Load the win condition using reflection
        try {
            String type = object.getString("win");

            Class<?> winc = Class.forName("level.win." + toProper(type.toLowerCase()) + "Win");
            Constructor<?> cons = winc.getConstructor(Map.class, Basebot.class);
            win = (Win) cons.newInstance(map, bot);
        } catch (Exception e) {
            if(!(e instanceof JSONException)) e.printStackTrace();
        }

        // Close the file
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toProper(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public Map getMap() {
        return map;
    }

    public Basebot getBot() {
        return bot;
    }

    public Win getWin() {
        return win;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
