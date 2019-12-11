package level.enemy;

import ai.AStarSearch;
import ai.Path;
import bots.RealBasebot;
import level.Reflective;
import level.map.Map;

import static main.Constants.*;
import static processing.Shape.CIRCLE;

public class AlienEnemy extends Enemy {
    private AStarSearch search;
    private Path path;

    /**
     * A powerful enemy that uses A* to attack the player
     */
    @Reflective
    public AlienEnemy(Map map, RealBasebot bot, float x, float y) {
        super(map, bot, CIRCLE, x, y, true);
        width = TILE_SIZE;
        height = TILE_SIZE;

        search = new AStarSearch(map);
    }

    @Override
    public void render() {
        applet.fill(0, 255, 0);
        applet.ellipse(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + EYE_OFFSET * Math.cos(ori));
        int newy = (int) (pos.y + EYE_OFFSET * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / EYE_FACTOR, width / EYE_FACTOR);

        // Uncomment the following lines to show A* path
        applet.stroke(255, 0, 0);
        if(path != null) {
            for(int i = 0; i < path.getList().size() - 1; i++) {
                applet.line(path.getList().get(i).x, path.getList().get(i).y, path.getList().get(i + 1).x, path.getList().get(i + 1).y);
            }
        } applet.stroke(0);
    }

    @Override
    void interactPlayer(RealBasebot bot) { /* Is not called */ }

    @Override
    public void updateEnemy() {
        // If we have no path, then load one in a new thread for performance
        if((int) applet.random(20) == 0 && pos.dist(bot.pos) < 35 * TILE_SIZE) {
            new Thread(() -> {
                try {
                    // Make a path & follow it
                    if (search.find(pos.copy(), bot.pos.copy())) path = new Path(search.getPath());
                } catch (Exception e) {
                    // This may be modified while reloading, so catch exceptions
                    search = new AStarSearch(map);
                }
            }).start();
        }

        // If the player is close, pursue them
        if(pos.dist(bot.pos) < 5 * TILE_SIZE) {
            nullPath();
            delegate.pursue(this, bot);
        }

        // Otherwise, follow the A* path if one exists
        else if(path != null) delegate.followPath(path, this);

        // Otherwise, no path exists so wander
        else wander();
    }

    /**
     * Make the path null
     */
    private void nullPath() {
        path = null;
        delegate.setPathDis(0f);
    }
}
