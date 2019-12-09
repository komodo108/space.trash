package gui.cutscene;

public class Start extends Cutscene {
    public Start() {
        super(1);
    }

    @Override
    public void render() {
        applet.image(assets.getImage("TestMap"), 0, 0);
    }
}
