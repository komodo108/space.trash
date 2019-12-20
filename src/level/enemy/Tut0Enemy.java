package level.enemy;

import bots.RealBasebot;
import level.Reflective;
import level.item.Item;
import level.map.Map;

import static main.Constants.*;
import static processing.Shape.RECTANGLE;

public class Tut0Enemy extends Enemy {

    /**
     * TUT-0 an enemy who does nothing
     */
    @Reflective
    public Tut0Enemy(Map map, RealBasebot bot, float x, float y) {
        super(map, bot, RECTANGLE, x, y, false);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        applet.fill(255, 0, 0);
        applet.rect(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + (width / 2) + EYE_OFFSET * Math.cos(ori));
        int newy = (int) (pos.y + (height / 2) + EYE_OFFSET * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / EYE_FACTOR, width / EYE_FACTOR);
    }

    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public void updateEnemy() {
        // Stand still
    }
}
