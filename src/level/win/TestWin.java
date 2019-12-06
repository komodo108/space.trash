package level.win;

import bots.Basebot;
import level.container.Container;
import level.item.Item;
import level.map.Map;

import java.util.List;

public class TestWin extends Win {
    public TestWin(Map map, Basebot bot) {
        super(map, bot);
    }

    @Override
    public boolean isWin() {
        List<Container> containers = map.getContainers();
        for(Container c : containers) {
            List<Item> held = c.getHeld();
            if(held.size() > 1) return true;
        } return false;
    }
}
