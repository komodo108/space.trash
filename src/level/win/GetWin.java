package level.win;

import bots.RealBasebot;
import level.Reflective;
import level.item.Item;
import level.map.Map;

import static level.item.Tags.FINAL;

public class GetWin extends Win {
    /**
     * Win when a complex item is obtained
     */
    @Reflective
    public GetWin(Map map, RealBasebot bot) {
        super(map, bot);
    }

    @Override
    public boolean isWin() {
        Item item = bot.getHeld();
        if(item != null) {
            return item.getTag() == FINAL;
        } return false;
    }
}
