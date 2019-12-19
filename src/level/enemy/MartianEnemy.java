package level.enemy;

import bots.RealBasebot;
import level.Reflective;
import level.item.Item;
import level.item.KeyItem;
import level.map.Map;

import static main.Constants.*;
import static processing.Shape.CIRCLE;

public class MartianEnemy extends Enemy {
    private int timer;
    private boolean top;

    /**
     * A dumb hostile enemy native to Mars
     */
    @Reflective
    public MartianEnemy(Map map, RealBasebot bot, float x, float y) {
        super(map, bot, CIRCLE, x, y, true);
        width = TILE_SIZE * 0.7f;
        height = TILE_SIZE * 0.7f;
    }

    @Override
    public void render() {
        timer += top ? -5 : 5;
        if(timer >= 255) top = true;
        if(timer <= 0) top = false;

        applet.fill(209, 87, 61);
        applet.ellipse(pos.x, pos.y, width, height);
        applet.noStroke();
        applet.fill(timer, 0, 0);
        applet.ellipse(pos.x - 1, pos.y, width * 0.6f, height * 0.6f);
        applet.stroke(0);

        int newx = (int) (pos.x + SMALL_EYE_OFFSET * Math.cos(ori));
        int newy = (int) (pos.y + SMALL_EYE_OFFSET * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / EYE_FACTOR, width / EYE_FACTOR);
    }

    @Override
    public Item getItem() {
        return new KeyItem(pos.x, pos.y);
    }

    @Override
    public void updateEnemy() {
        // If we have no path, then load one in a new thread for performance
        if (pos.dist(bot.pos) < 40 * TILE_SIZE) delegate.pursue(this, bot);

        // Otherwise, no path exists so wander
        else wander();
    }
}
