package bots;

import level.enemy.Enemy;
import level.item.Item;
import level.map.Map;
import processing.PCObject;
import processing.PObject;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import python.middleware.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static main.Constants.*;
import static processing.Shape.CIRCLE;

public class RealBasebot extends PCObject implements PythonInteractable {
    private Basebot implementation;
    private Item held;
    private int attack;
    private boolean special;
    private Set<Enemy> near;
    private final Color BLACK = Color.BLACK, HOLE = Color.WHITE;

    public RealBasebot(Map map, int x, int y, boolean special) {
        super(map, CIRCLE);
        width = TILE_SIZE;
        height = TILE_SIZE;
        pos = new PVector(x, y);
        attack = 0;
        this.special = special;
        near = ConcurrentHashMap.newKeySet();

        BasebotSingleton.getInstance().setBot(this, KEY);
        implementation = new Basebot();
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
        if(special) {
            applet.rect(pos.x, pos.y, width, height);

            int newx = (int) (pos.x + (width / 2) + EYE_OFFSET * Math.cos(ori));
            int newy = (int) (pos.y + (height / 2) + EYE_OFFSET * Math.sin(ori));
            applet.fill(0);
            applet.ellipse(newx, newy, height / EYE_FACTOR, width / EYE_FACTOR);
        } else {
            applet.ellipse(pos.x, pos.y, width, height);

            int newx = (int) (pos.x + EYE_OFFSET * Math.cos(ori));
            int newy = (int) (pos.y + EYE_OFFSET * Math.sin(ori));
            applet.fill(0);
            applet.ellipse(newx, newy, height / EYE_FACTOR, width / EYE_FACTOR);
        }

        // forum.processing.org/two/discussion/11066/cookie-cut-shape-from-other-shape
        if(map.getSetting().isDark()) {
            PImage image = masked();
            applet.image(setAllColorAlpha(image, HOLE), 0, 0);
        }
    }

    private PImage masked() {
        PGraphics pg = applet.createGraphics(WIDTH, HEIGHT, PConstants.JAVA2D);
        pg.smooth(4);
        pg.beginDraw();

        pg.noStroke();
        pg.fill(BLACK.getRed(), BLACK.getGreen(), BLACK.getBlue(), BLACK.getAlpha());
        pg.rect(0, 0, WIDTH, HEIGHT);
        pg.fill(HOLE.getRed(), HOLE.getGreen(), HOLE.getBlue(), HOLE.getAlpha());
        pg.ellipse(pos.x, pos.y, TILE_SIZE * 20, TILE_SIZE * 20);

        pg.endDraw();
        return pg.get();
    }

    private PImage setAllColorAlpha(PImage image, Color color) {
        image.loadPixels();
        int icolor = color.getRGB() & 0x00FFFFFF;
        int p[] = image.pixels, i = p.length, q;
        while(i-- != 0) {
            if((q = p[i] & 0x00FFFFFF) == icolor)
                p[i] = q;
        } image.updatePixels();
        return image;
    }

    public void setHeld(Item held) {
        this.held = held;
    }

    public Item getHeld() {
        return held;
    }

    @Override
    public boolean update() {
        ActionQueue queue = QueueSingleton.getInstance().getQueue();
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
                        if(held == null) map.getItems().add(enemy.getItem());
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

    public void addNear(Enemy enemy) {
        near.add(enemy);
    }

    public void removeNear(Enemy enemy) {
       near.remove(enemy);
    }

    public Set<Enemy> getNear() {
        return near;
    }
}
