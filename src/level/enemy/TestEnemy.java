package level.enemy;

import ai.AStarSearch;
import ai.Path;
import bots.RealBasebot;
import level.map.Map;

import static main.Constants.TILE_SIZE;
import static processing.Shape.CIRCLE;

public class TestEnemy extends Enemy {
    private AStarSearch search;
    private Path path;

    public TestEnemy(Map map, RealBasebot bot, int x, int y) {
        super(map, bot, CIRCLE, x, y);
        width = TILE_SIZE;
        height = TILE_SIZE;

        search = new AStarSearch(map);
    }

    @Override
    public void render() {
        applet.fill(0, 255, 0);
        applet.ellipse(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + 2 * Math.cos(ori));
        int newy = (int) (pos.y + 2 * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / 2f, width / 2f);

        // Uncomment the following lines to show A* path
        applet.stroke(255, 0, 0);
        if(path != null) {
            for(int i = 0; i < path.getList().size() - 1; i++) {
                applet.line(path.getList().get(i).x, path.getList().get(i).y, path.getList().get(i + 1).x, path.getList().get(i + 1).y);
            }
        } applet.stroke(0);
    }

    @Override
    public void updateEnemy() {
        // If we have no path, then load one in a new thread for performance
        if((int) applet.random(50) == 0) {
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

        // If the player is within a certain distance, pursue them
        if(pos.dist(bot.pos) < 5 * TILE_SIZE) {
            nullPath();
            delegate.pursue(this, bot);
        }

        // Otherwise, follow the A* path if one exists
        else if(path != null) {
            delegate.followPath(path, this);
        }

        // If no path exists then wander
        else if(path == null) {
            wander();
        }
    }

    private void nullPath() {
        path = null;
        delegate.setPathDis(0f);
    }
}
