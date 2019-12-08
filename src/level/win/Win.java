package level.win;

import bots.RealBasebot;
import level.map.Map;
import processing.PObject;

import java.util.List;

public abstract class Win {
    protected Map map;
    protected List<PObject> objects;
    protected RealBasebot bot;

    public Win(Map map, RealBasebot bot) {
        this.map = map;
        this.bot = bot;
    }

    public void setObjects(List<PObject> objects) {
        this.objects = objects;
    }

    /**
     * Returns if the player has won the level
     * @return if the player has won the level
     */
    public abstract boolean isWin();
}
