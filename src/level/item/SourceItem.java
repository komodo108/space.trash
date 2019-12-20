package level.item;

import level.Reflective;

import static level.item.Tags.FINAL;
import static main.Constants.TILE_SIZE;
import static processing.Shape.CIRCLE;

public class SourceItem extends Item {
    private int timer;
    private boolean top;

    /**
     * Source code for PRPF
     */
    @Reflective
    public SourceItem(float x, float y) {
        super(CIRCLE, x, y, FINAL);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        timer += top ? -5 : 5;
        if(timer >= 255) top = true;
        if(timer <= 0) top = false;

        applet.fill(timer, 0, 0);
        applet.ellipse(pos.x, pos.y, width, height);
    }
}
