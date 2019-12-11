package level.enemy;

import bots.RealBasebot;
import level.Reflective;
import level.map.Map;

import static main.Constants.*;
import static processing.Shape.CIRCLE;

public class PigEnemy extends Enemy {

    /**
     * A simple wandering passive enemy
     */
    @Reflective
    public PigEnemy(Map map, RealBasebot bot, int x, int y) {
        super(map, bot, CIRCLE, x, y, false);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        applet.fill(255, 192, 203);
        applet.ellipse(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + EYE_OFFSET * Math.cos(ori));
        int newy = (int) (pos.y + EYE_OFFSET * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / EYE_FACTOR, width / EYE_FACTOR);
    }

    @Override
    void interactPlayer(RealBasebot bot) { /* No nothing */ }

    @Override
    public void updateEnemy() {
        // Just wander around
        wander();
    }
}
