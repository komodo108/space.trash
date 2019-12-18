package gui.cutscene;

public class Mid extends Cutscene {
    public Mid() {
        super(6);
    }

    @Override
    public void render() {
        applet.image(assets.getImage("Fired" + (frame + 1)), 0, 0);
    }
}
