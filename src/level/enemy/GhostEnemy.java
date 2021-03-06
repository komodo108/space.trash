package level.enemy;

import bots.RealBasebot;
import level.Reflective;
import level.item.Item;
import level.map.Map;

import static main.Constants.*;
import static processing.Shape.CIRCLE;

public class GhostEnemy extends Enemy {
    /**
     * A creepy enemy that is invisible
     */
    @Reflective
    public GhostEnemy(Map map, RealBasebot bot, float x, float y) {
        super(map, bot, CIRCLE, x, y, true);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        applet.noFill();
        applet.ellipse(pos.x, pos.y, width, height);
        applet.fill(0);
    }

    @Override
    public void updateEnemy() {
        // If we have no path, then load one in a new thread for performance
        if (pos.dist(bot.pos) < 40 * TILE_SIZE) delegate.pursue(this, bot);

        // Otherwise, no path exists so wander
        else wander();
    }

    @Override
    public Item getItem() {
        return null;
    }


    @Override
    protected void interactMap() {
        // Collision with the outside walls only
        if(pos.x < 0 || pos.x > WIDTH) {
            vel.x = -vel.x;
            pos.x += pos.x < -2 ? 1 : (pos.x > WIDTH + 2 ? -1 : 0);
        } if(pos.y < 0 || pos.y > HEIGHT) {
            vel.y = -vel.y;
            pos.y += pos.y < -2 ? 1 : (pos.y > HEIGHT + 2 ? -1 : 0);
        }
    }
}
