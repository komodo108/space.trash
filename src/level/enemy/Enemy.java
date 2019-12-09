package level.enemy;

import ai.Delegate;
import bots.RealBasebot;
import level.map.Map;
import processing.CollisionDetect;
import processing.PCObject;
import processing.PObject;
import processing.Shape;
import processing.core.PVector;

import java.util.List;

import static main.Constants.MAX_SPEED;

public abstract class Enemy extends PCObject {
    protected Delegate delegate;
    protected RealBasebot bot;
    boolean target = false;

    public Enemy(Map map, RealBasebot bot, Shape shape, int x, int y) {
        super(map, shape);
        pos = new PVector(x, y);
        delegate = new Delegate();
        this.bot = bot;
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
        target = false;
        delegate.fleeWall(this, map);
        delegate.wander(this);
    }

    /**
     * Update the enemy
     */
    public abstract void updateEnemy();

    @Override
    public void interactOthers(List<PObject> others) {
        for(PObject object : others) {
            if(object instanceof RealBasebot) {
                RealBasebot bot = (RealBasebot) object;
                if(CollisionDetect.isInside(this, bot)) {
                    bot.setDead();
                    bot.setDead();
                }
            }
        }
    }
}
