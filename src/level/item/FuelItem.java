package level.item;

import level.Reflective;

import static level.item.Tags.FINAL;
import static main.Constants.TILE_SIZE;
import static processing.Shape.CIRCLE;

public class FuelItem extends Item {

    /**
     * A packed box
     */
    @Reflective
    public FuelItem(float x, float y) {
        super(CIRCLE, x, y, FINAL);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        applet.fill(149, 186, 48);
        applet.ellipse(pos.x, pos.y, width, height);
    }
}
