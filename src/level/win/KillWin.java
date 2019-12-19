package level.win;

import bots.RealBasebot;
import level.Reflective;
import level.enemy.Enemy;
import level.map.Map;
import processing.PObject;

public class KillWin extends Win {
    /**
     * Win when all enemies are dead
     */
    @Reflective
    public KillWin(Map map, RealBasebot bot) {
        super(map, bot);
    }

    @Override
    public boolean isWin(boolean update) {
        for(PObject object : objects) {
            if(object instanceof Enemy) return false;
        } return true;
    }
}
