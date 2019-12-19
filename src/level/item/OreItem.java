package level.item;

import level.Reflective;

import static level.item.Tags.SIMPLE;
import static main.Constants.TILE_SIZE;
import static processing.Shape.CIRCLE;

public class OreItem extends Item {
    /**
     * A piece of martian ore
     */
    @Reflective
    public OreItem(float x, float y) {
        super(CIRCLE, x, y, SIMPLE);
        width = 0.8f * TILE_SIZE;
        height = 0.8f * TILE_SIZE;
    }

    @Override
    public void render() {
        applet.fill(201, 33, 24);
        applet.ellipse(pos.x, pos.y, width, height);
        applet.noStroke();
        applet.fill(251, 103, 94);
        applet.ellipse(pos.x, pos.y, width / 3, height / 3);
        applet.stroke(0);
    }
}
