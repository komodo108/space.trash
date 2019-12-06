package bots;

import level.item.Item;
import level.map.Map;
import processing.PCObject;
import processing.PObject;
import processing.core.PVector;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

import java.util.List;

import static main.Constants.TILE_SIZE;
import static processing.Shape.CIRCLE;

public class Basebot extends PCObject implements PythonInteractable {
    private IBasebot implementation;
    private Item held;

    public Basebot(Map map, int x, int y) {
        super(map, CIRCLE);
        width = TILE_SIZE;
        height = TILE_SIZE;
        pos = new PVector(x, y);
        implementation = new IBasebot(this);
    }

    @Override
    public void render() {
        applet.fill(255, 0, 0);
        applet.ellipse(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + 2 * Math.cos(ori));
        int newy = (int) (pos.y + 2 * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / 2f, width / 2f);
    }

    public void setHeld(Item held) {
        this.held = held;
    }

    public Item getHeld() {
        return held;
    }

    @Override
    public void interactOthers(List<PObject> others) {
        /* no content */
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
