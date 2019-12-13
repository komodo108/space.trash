package level.win;

import bots.RealBasebot;
import level.Reflective;
import level.map.Map;
import main.AppletSingleton;
import processing.core.PApplet;

public class TimeWin extends Win {
    private int timer;
    private PApplet applet = AppletSingleton.getInstance().getApplet();

    /**
     * Wins after the level has been played for a certain amount of time
     */
    @Reflective
    public TimeWin(Map map, RealBasebot bot) {
        super(map, bot);
    }

    @Override
    public boolean isWin() {
        timer++;
        return timer >= applet.frameRate * 1.5f;
    }
}
