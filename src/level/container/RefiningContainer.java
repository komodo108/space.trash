package level.container;

import level.Reflective;
import level.item.FuelItem;
import level.item.Item;
import level.item.OreItem;
import level.map.Map;

import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class RefiningContainer extends Container {
    /**
     * Takes in 2 ores and packs them into a packed item
     */
    @Reflective
    public RefiningContainer(Map map, float x, float y) {
        super(map, RECTANGLE, x, y);
        width = 2 * TILE_SIZE;
        height = 2 * TILE_SIZE;
    }

    @Override
    public boolean interact(Item item) {
        if(held.size() < 2 && (item instanceof OreItem)) held.add(item);
        return held.size() == 2;
    }

    @Override
    public Item getItem() {
        if(held.size() == 2) {
            return new FuelItem(0, 0);
        } return null;
    }

    @Override
    public void render() {
        applet.fill(120);
        applet.rect(pos.x, pos.y, width, height);
        applet.fill(149, 186, 48);
        applet.noStroke();
        switch (held.size()) {
            case 1:
                applet.rect(pos.x + (0.5f * TILE_SIZE), pos.y + (height / 2), TILE_SIZE, TILE_SIZE * 0.5f);
                break;
            case 2:
                applet.rect(pos.x + (0.5f * TILE_SIZE), pos.y + (0.5f * TILE_SIZE), TILE_SIZE, TILE_SIZE);
                break;
        } applet.stroke(0);
    }
}
