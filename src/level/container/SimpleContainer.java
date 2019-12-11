package level.container;

import level.Reflective;
import level.item.CrateItem;
import level.item.Item;
import level.item.KeyItem;

import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class SimpleContainer extends Container {
    /**
     * Simple container, can hold boxes & crates
     */
    @Reflective
    public SimpleContainer(float x, float y) {
        super(RECTANGLE, x, y);
        width = 2 * TILE_SIZE;
        height = 2 * TILE_SIZE;
    }

    @Override
    public boolean interact(Item item) {
        if(item instanceof KeyItem || item instanceof CrateItem) {
            held.add(item);
        } return false;
    }

    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public void render() {
        applet.fill(168, 101, 20);
        applet.rect(pos.x, pos.y, width, height);
        applet.fill(70);
        applet.noStroke();
        switch (held.size()) {
            case 0:
                break;
            case 1:
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (0.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                break;
            case 2:
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (0.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                applet.rect(pos.x + (1.1f * TILE_SIZE), pos.y + (0.5f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                break;
            default:
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (0.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                applet.rect(pos.x + (1.1f * TILE_SIZE), pos.y + (0.5f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                applet.rect(pos.x + (0.1f * TILE_SIZE), pos.y + (1.1f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
                break;
        } applet.stroke(0);
    }
}
