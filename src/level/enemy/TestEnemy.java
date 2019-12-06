package level.enemy;

import bots.Basebot;
import level.map.Map;
import processing.core.PVector;

import static main.Constants.TILE_SIZE;
import static processing.Shape.CIRCLE;

public class TestEnemy extends Enemy {
    public TestEnemy(Map map, Basebot bot, int x, int y) {
        super(map, bot, CIRCLE, x, y);
        width = 3 * TILE_SIZE;
        height = 2 * TILE_SIZE;
        vel = new PVector(1, 1);
    }

    @Override
    public void render() {
        applet.fill(0, 255, 0);
        applet.ellipse(pos.x, pos.y, width, height);
    }

    @Override
    public void updateEnemy() {
        delegate.pursue(this, bot);
    }
}
