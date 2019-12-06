package level.map;

import level.container.Container;
import level.item.Item;
import main.AppletSingleton;
import processing.Assets;
import processing.PCObject;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

import static level.map.CellTypes.GRASS;
import static level.map.CellTypes.WALL;
import static main.Constants.*;
import static processing.Shape.RECTANGLE;

public class Map {
    private PApplet applet = AppletSingleton.getInstance().getApplet();
    private List<Item> items;
    private List<Container> containers;
    private Settings setting;
    private Cell[][] map;

    public Map(Settings setting) {
        items = new ArrayList<>();
        containers = new ArrayList<>();
        map = new Cell[MAP_WIDTH][MAP_HEIGHT];
        this.setting = setting;

        for(int x = 0; x < MAP_WIDTH; x++) {
            for(int y = 0; y < MAP_HEIGHT; y++) {
                setCell(x, y, GRASS);
            }
        }
    }

    public void add(Item item) {
        items.add(item);
    }

    public void add(Container container) {
        containers.add(container);
    }

    public void addWall(int x, int y, int width, int height) {
        for(int i = x; i < x + width; i++) {
            for(int j = y; j < y + height; j++) {
                setCell(i, j, WALL);
            }
        }
    }

    public void setCell(int x, int y, CellTypes type) {
        map[x][y] = new Cell(x * TILE_SIZE, y * TILE_SIZE, type);
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public List<Cell> getCells(PCObject body) {
        /* Return back the list of cells this body is within, use the vertexes of the rectangle to determine this
            0         1
             |------|
             |  :)  |
             |------|
            2         3
         */

        List<Cell> cells = new ArrayList<>();
        if(body.shape == RECTANGLE) {
            cells.add(getCell(body.pos.x, body.pos.y));
            cells.add(getCell(body.pos.x + body.width, body.pos.y));
            cells.add(getCell(body.pos.x, body.pos.y + body.height));
            cells.add(getCell(body.pos.x + body.width, body.pos.y + body.height));
        } else {
            cells.add(getCell(body.pos.x - body.width / 2f, body.pos.y - body.width / 2f));
            cells.add(getCell(body.pos.x + body.width / 2f, body.pos.y - body.width / 2f));
            cells.add(getCell(body.pos.x - body.width / 2f, body.pos.y + body.height / 2f));
            cells.add(getCell(body.pos.x + body.width / 2f, body.pos.y + body.height / 2f));
        } return cells;
    }

    public Cell getCell(float x, float y) {
        if(x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) return map[(int) x / TILE_SIZE][(int) y / TILE_SIZE];
        else return null;
    }

    /**
     * Render the map and elements in it
     */
    public void render() {
        // Render the map background
        applet.image(Assets.getInstance().getImage(setting.getMapName()), 0, 0);

        // Render the map
        for(int x = 0; x < MAP_WIDTH; x++) {
            for(int y = 0; y < MAP_HEIGHT; y++) {
                map[x][y].render();
            }
        }

        // Render all items
        List<Item> removedItems = new ArrayList<>();
        for(Item item : items) {
            if(item.update()) removedItems.add(item);
            if(!item.isDead()) item.render();
        } items.removeAll(removedItems);

        // Render all containers
        List<Container> removedContainers = new ArrayList<>();
        for(Container container : containers) {
            if(container.update()) removedContainers.add(container);
            if(!container.isDead()) container.render();
        } containers.removeAll(removedContainers);
    }
}
