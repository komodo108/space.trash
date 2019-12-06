package level.container;

import level.item.Item;
import level.item.TestItem;

import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class TestContainer extends Container {
    public TestContainer(int x, int y) {
        super(RECTANGLE, x, y);
        width = 2 * TILE_SIZE;
        height = 2 * TILE_SIZE;
    }

    @Override
    public boolean interact(Item item) {
        if(item instanceof TestItem) {
            held.add(item);
        } return false;
    }

    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public void render() {
        if(held.size() == 1) applet.fill(255, 255, 0);
        else applet.fill(255);
        applet.rect(pos.x, pos.y, width, height);
    }
}
