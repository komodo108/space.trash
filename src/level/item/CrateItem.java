package level.item;

import level.Reflective;

import static level.item.Tags.SIMPLE;
import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class CrateItem extends Item {
    /**
     * A simple crate
     */
    @Reflective
    public CrateItem(float x, float y) {
        super(RECTANGLE, x, y, SIMPLE);
        width = 1.3f * TILE_SIZE;
        height = 1.3f * TILE_SIZE;
    }

    @Override
    public void render() {
        applet.fill(168, 101, 20);
        applet.rect(pos.x, pos.y, width, height);
        applet.fill(70);
        applet.noStroke();
        applet.rect(pos.x + (0.4f * TILE_SIZE), pos.y + (0.3f * TILE_SIZE), TILE_SIZE * 0.8f, TILE_SIZE * 0.8f);
        applet.stroke(0);
    }
}
