package level.enemy;

import ai.Delegate;
import bots.Basebot;
import level.map.Map;
import processing.CollisionDetect;
import processing.PCObject;
import processing.PObject;
import processing.Shape;
import processing.core.PVector;

import java.util.List;

public abstract class Enemy extends PCObject {
    protected Delegate delegate;
    protected Basebot bot;

    public Enemy(Map map, Basebot bot, Shape shape, int x, int y) {
        super(map, shape);
        pos = new PVector(x, y);
        delegate = new Delegate();
        this.bot = bot;
    }

    @Override
    public boolean update() {
        super.integrate();
        updateEnemy();
        return super.update();
    }

    /**
     * Update the enemy
     */
    public abstract void updateEnemy();

    @Override
    public void interactOthers(List<PObject> others) {
        for(PObject object : others) {
            if(object instanceof Basebot) {
                Basebot bot = (Basebot) object;
                if(CollisionDetect.isInside(this, bot)) {
                    // hurt the bot
                    System.out.println("hurt mr. bot ;-;");
                }
            }
        }
    }
}
