package level;

import bots.RealBasebot;
import gui.implementation.DefaultCode;
import gui.implementation.Tutorial;
import level.container.Container;
import level.enemy.Enemy;
import level.item.Item;
import level.map.Map;
import level.map.Settings;
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

import static level.map.CellTypes.*;
import static main.Constants.TILE_SIZE;

public class LevelLoader {
    private static final String LEVEL_DIR = "data/level/";
    private Map map;
    private RealBasebot bot;
    private Win win;
    private List<Enemy> enemies;
    private Tutorial tutorial;
    private DefaultCode code;

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
                map.setType(x, y, width, height, WALL);
            }
        } catch (JSONException e) { /* no content */ }

        // Load conditional walls
        try {
            JSONArray cwalls = jsonMap.getJSONArray("conditionals");
            for (int i = 0; i < cwalls.length(); i++) {
                JSONObject wall = cwalls.getJSONObject(i).getJSONObject("wall");
                int x = wall.getInt("x");
                int y = wall.getInt("y");
                int width = wall.getInt("width");
                int height = wall.getInt("height");
                map.setType(x, y, width, height, CCWALL);
            }
        } catch (JSONException e) { /* no content */ }

        // Load goal
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
                float x = item.getFloat("x");
                float y = item.getFloat("y");

                Class<?> itemc = Class.forName("level.item." + toProper(type.toLowerCase()) + "Item");
                Constructor<?> cons = itemc.getConstructor(float.class, float.class);
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
                float x = container.getFloat("x");
                float y = container.getFloat("y");

                Class<?> containerc = Class.forName("level.container." + toProper(type.toLowerCase()) + "Container");
                Constructor<?> cons = containerc.getConstructor(Map.class, float.class, float.class);
                map.add((Container) cons.newInstance(map, x * TILE_SIZE, y * TILE_SIZE));
            }
        } catch (Exception e) {
            if(!(e instanceof JSONException)) e.printStackTrace();
        }

        // Load the bot
        JSONObject botobject = object.getJSONObject("bot");
        int x = botobject.getInt("x");
        int y = botobject.getInt("y");
        boolean special;
        try {
            special = botobject.getBoolean("special");
        } catch (JSONException e) {
            special = false;
        } bot = new RealBasebot(map, x * TILE_SIZE, y * TILE_SIZE, special);

        // Load enemies using reflection
        try {
            JSONArray enemiesJson = object.getJSONArray("enemies");
            for (int i = 0; i < enemiesJson.length(); i++) {
                JSONObject enemy = enemiesJson.getJSONObject(i).getJSONObject("enemy");
                String type = enemy.getString("type");
                float xf = enemy.getFloat("x");
                float yf = enemy.getFloat("y");

                Class<?> emenyc = Class.forName("level.enemy." + toProper(type.toLowerCase()) + "Enemy");
                Constructor<?> cons = emenyc.getConstructor(Map.class, RealBasebot.class, float.class, float.class);
                enemies.add((Enemy) cons.newInstance(map, bot, xf * TILE_SIZE, yf * TILE_SIZE));
            }
        } catch (Exception e) {
            if(!(e instanceof JSONException)) e.printStackTrace();
        }

        // Load the win condition using reflection
        try {
            String type = object.getString("win");

            Class<?> winc = Class.forName("level.win." + toProper(type.toLowerCase()) + "Win");
            Constructor<?> cons = winc.getConstructor(Map.class, RealBasebot.class);
            win = (Win) cons.newInstance(map, bot);
        } catch (Exception e) {
            if(!(e instanceof JSONException)) e.printStackTrace();
        }

        // Load the tutorial messages
        tutorial = new Tutorial();
        JSONArray messages = object.getJSONArray("messages");
        for (int i = 0; i < messages.length(); i++) {
            JSONArray message = messages.getJSONObject(i).getJSONArray("message");
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < message.length(); j++) {
                String content = message.getString(j);
                sb.append(content).append("\n");
            } tutorial.addText(sb.toString());
        }

        // Load the default code
        JSONObject defaultCode = object.getJSONObject("default");
        JSONArray stringsjson = defaultCode.getJSONArray("code");
        StringBuilder sb = new StringBuilder();
        sb.append("# Level " + (Integer.parseInt(pathname.split("level")[1].split("[.]json")[0]) + 1) + "\n");
        sb.append("# Code here is not editable\n");
        for (int i = 0; i < stringsjson.length(); i++) {
            String codestring = stringsjson.getString(i);
            sb.append(codestring).append("\n");
        } code = new DefaultCode(sb.toString());

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

    public RealBasebot getBot() {
        return bot;
    }

    public Win getWin() {
        return win;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public DefaultCode getCode() {
        return code;
    }
}
