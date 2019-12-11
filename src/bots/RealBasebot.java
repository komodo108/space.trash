package bots;

import level.enemy.Enemy;
import level.item.Item;
import level.map.Map;
import processing.PCObject;
import processing.PObject;
import processing.core.PVector;
import python.middleware.*;

import java.util.List;

import static main.Constants.*;
import static processing.Shape.CIRCLE;

public class RealBasebot extends PCObject implements PythonInteractable {
    private Basebot implementation;
    private Item held;
    private int attack;

    public RealBasebot(Map map, int x, int y) {
        super(map, CIRCLE);
        width = TILE_SIZE;
        height = TILE_SIZE;
        pos = new PVector(x, y);
        implementation = new Basebot(this);
        attack = 0;
    }

    @Override
    public void render() {
        if(attack > 0) {
            applet.noStroke();
            applet.fill(173, 216, 230, 120);
            applet.ellipse(pos.x, pos.y, 2 * TILE_SIZE * ATTACK_RADIUS, 2 * TILE_SIZE * ATTACK_RADIUS);
            applet.stroke(0);
        }

        applet.fill(255, 0, 0);
        applet.ellipse(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + EYE_OFFSET * Math.cos(ori));
        int newy = (int) (pos.y + EYE_OFFSET * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / EYE_FACTOR, width / EYE_FACTOR);
    }

    public void setHeld(Item held) {
        this.held = held;
    }

    public Item getHeld() {
        return held;
    }

    @Override
    public boolean update() {
        ActionQueue queue = implementation.getQueue(KEY);
        while(queue.peek() != null) {
            ActionString as = queue.remove();
            if (as.action == Actions.ATTACK) {
                attack = (int) (applet.frameRate * ATTACK_TIME);
            }
        } return super.update();
    }

    @Override
    public void interactOthers(List<PObject> others) {
        // Attack enemies around the bot if we are to attack
        if(attack > 0) {
            for (PObject object : others) {
                if (object instanceof Enemy) {
                    Enemy enemy = (Enemy) object;

                    // If we hit an enemy, stop attacking
                    if (enemy.pos.dist(pos) < TILE_SIZE * ATTACK_RADIUS) {
                        enemy.setDead();
                    }
                }
            } attack--;
        }
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
