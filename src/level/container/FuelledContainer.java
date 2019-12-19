package level.container;

import level.Reflective;
import level.item.FuelItem;
import level.item.Item;
import level.map.Map;

import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class FuelledContainer extends Container {
    /**
     * Opens the walls when interacted with fuel
     */
    @Reflective
    public FuelledContainer(Map map, float x, float y) {
        super(map, RECTANGLE, x, y);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public boolean interact(Item item) {
        if(item instanceof FuelItem) map.openClosedWalls();
        return false;
    }

    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public void render() {
        float radius = 0.5f * TILE_SIZE;
        applet.fill(179, 216, 78);
        applet.rect(pos.x, pos.y, width, height);
        applet.fill(0);
        applet.ellipse(pos.x + (width / 2), pos.y + (height / 2), radius, radius);
    }
}
