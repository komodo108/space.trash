package level.item;

import level.Reflective;

import static level.item.Tags.FINAL;
import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class PackedItem extends Item {

    /**
     * A packed box
     */
    @Reflective
    public PackedItem(float x, float y) {
        super(RECTANGLE, x, y, FINAL);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        applet.fill(173, 135, 135);
        applet.rect(pos.x, pos.y, width / 3, height);
        applet.fill(0);
        applet.fill(pos.x + width / 3, pos.y, width / 3, height);
        applet.fill(173, 135, 135);
        applet.fill(pos.x + (2 * width / 3), pos.y, width / 3, height);
    }
}
