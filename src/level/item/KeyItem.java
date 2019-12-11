package level.item;

import level.Reflective;

import static level.item.Tags.SIMPLE;
import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class KeyItem extends Item {
    /**
     * A simple box
     */
    @Reflective
    public KeyItem(int x, int y) {
        super(RECTANGLE, x, y, SIMPLE);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        // TODO: Replace this with a key
        applet.fill(156, 103, 39);
        applet.rect(pos.x, pos.y, width, height);
    }
}
