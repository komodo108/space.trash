package level.item;

import processing.core.PVector;

public class TestItem extends Item {
    public TestItem(int x, int y) {
        width = 10;
        height = 10;
        pos = new PVector(x, y);
    }

    @Override
    public void render() {
        applet.fill(170);
        applet.rect(pos.x, pos.y, width, height);
    }
}
