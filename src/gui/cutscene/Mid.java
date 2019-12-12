package gui.cutscene;

public class Mid extends Cutscene {
    public Mid() {
        super(1);
    }

    @Override
    public void render() {
        applet.image(assets.getImage("Title"), 0, 0);
    }
}
