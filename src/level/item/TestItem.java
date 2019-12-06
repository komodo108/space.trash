package level.item;

public class TestItem extends Item {
    public TestItem(int x, int y) {
        super(x, y);
        width = 10;
        height = 10;
    }

    @Override
    public void render() {
        applet.fill(170);
        applet.rect(pos.x, pos.y, width, height);
    }
}
