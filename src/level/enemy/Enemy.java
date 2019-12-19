package level.enemy;

import ai.Delegate;
import bots.RealBasebot;
import level.item.Item;
import level.map.Map;
import processing.CollisionDetect;
import processing.PCObject;
import processing.PObject;
import processing.Shape;
import processing.core.PVector;

import java.util.List;

import static main.Constants.*;

public abstract class Enemy extends PCObject {
    protected Delegate delegate;
    protected RealBasebot bot;
    protected boolean hostile;

    public Enemy(Map map, RealBasebot bot, Shape shape, float x, float y, boolean hostile) {
        super(map, shape);
        pos = new PVector(x, y);
        delegate = new Delegate();
        this.bot = bot;
        this.hostile = hostile;
        vel = new PVector(MAX_SPEED, MAX_SPEED);
    }

    @Override
    public boolean update() {
        super.integrate();
        updateEnemy();
        return super.update();
    }

    /**
     * Standard wandering AI
     */
    void wander() {
        delegate.fleeWall(this, map);
        delegate.wander(this);
    }

    /**
     * Update the enemy
     */
    public abstract void updateEnemy();

    /**
     * Returns the item the enemy drops
     * @return the item the enemy drops
     */
    public abstract Item getItem();

    @Override
    public void interactOthers(List<PObject> others) {
        for(PObject object : others) {
            if(object instanceof RealBasebot) {
                RealBasebot bot = (RealBasebot) object;
                if(CollisionDetect.isInside(this, bot)) {
                    if(hostile) bot.setDead();
                } else if(pos.dist(bot.pos) <= TILE_SIZE * TARGET_RADIUS) bot.addNear(this);
                else bot.removeNear(this);
            }
        }
    }
}
