package gui.cutscene;

public class End extends Cutscene {
    public End() {
        super(1);
    }

    @Override
    public void render() {
        if(frame < max) {
            applet.image(assets.getImage("TestMap"), 0, 0);
        } else {
            System.exit(1);
        }
    }

    @Override
    public boolean isRender() {
        return true;
    }
}
