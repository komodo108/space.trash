package level.item;

public class TestItem extends Item {
    public TestItem(int x, int y) {
        super(x, y);
        width = 18;
        height = 18;
    }

    @Override
    public void render() {
        applet.fill(170);
        applet.rect(pos.x, pos.y, width, height);
    }
}
