package level.win;

import bots.RealBasebot;
import level.Reflective;
import level.item.Item;
import level.map.Map;

import java.util.List;

public class ClearWin extends Win {
    /**
     * Win when all items are in containers
     */
    @Reflective
    public ClearWin(Map map, RealBasebot bot) {
        super(map, bot);
    }

    @Override
    public boolean isWin() {
        List<Item> items = map.getItems();
        if(items.size() == 0) {
            return bot.getHeld() == null;
        } return false;
    }
}
