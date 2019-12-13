package gui.cutscene;

public class Mid extends Cutscene {
    public Mid() {
        super(2);
    }

    @Override
    public void render() {
        switch (frame) {
            case 0:
                applet.image(assets.getImage("Fired"), 0, 0);
                break;
            case 1:
                applet.image(assets.getImage("Idea"), 0, 0);
                break;
        }
    }
}
