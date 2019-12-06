package level.item;

import static main.Constants.TILE_SIZE;

public class TestItem extends Item {
    public TestItem(int x, int y) {
        super(x, y);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    @Override
    public void render() {
        applet.fill(170);
        applet.rect(pos.x, pos.y, width, height);
    }
}
