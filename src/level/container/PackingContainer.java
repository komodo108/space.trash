package level.container;

import level.Reflective;
import level.item.CrateItem;
import level.item.Item;
import level.item.PackedItem;
import level.map.Map;

import java.util.ArrayList;

import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class PackingContainer extends Container {
    /**
     * Takes in 3 boxes / creates and packs them into a packed item
     */
    @Reflective
    public PackingContainer(Map map, float x, float y) {
        super(map, RECTANGLE, x, y);
        width = 2 * TILE_SIZE;
        height = 2 * TILE_SIZE;
    }

    @Override
    public boolean interact(Item item) {
        if(held.size() < 3 && (item instanceof CrateItem)) held.add(item);
        else return held.size() == 3;
        return false;
    }

    @Override
    public Item getItem() {
        if(held.size() == 3) {
            held = new ArrayList<>();
            return new PackedItem(0, 0);
        } return null;
    }

    @Override
    public void render() {
        applet.fill(168, 101, 20);
        applet.rect(pos.x, pos.y, width, height);
        applet.fill(70);
        applet.noStroke();
        switch (held.size()) {
            case 1:
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (0.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                break;
            case 2:
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (0.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                applet.rect(pos.x + (1.1f * TILE_SIZE), pos.y + (0.5f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                break;
            case 3:
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (0.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                applet.rect(pos.x + (1.1f * TILE_SIZE), pos.y + (0.5f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (1.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                break;
        } applet.stroke(0);
        applet.fill(40);
        applet.rect(pos.x, pos.y, width / 8, height);
        applet.rect(pos.x + width - (width / 8), pos.y, width / 8, height);
    }
}
