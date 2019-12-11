package level.item;

import level.Reflective;
import processing.Assets;

import static level.item.Tags.SIMPLE;
import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class KeyItem extends Item {
    /**
     * A simple box
     */
    @Reflective
    public KeyItem(float x, float y) {
        super(RECTANGLE, x, y, SIMPLE);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        applet.image(Assets.getInstance().getImage("Key"), pos.x, pos.y);
    }
}
