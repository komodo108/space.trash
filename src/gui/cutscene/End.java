package gui.cutscene;

import level.Save;

public class End extends Cutscene {
    private Save save;

    public End(Save save) {
        super(1);
        this.save = save;
    }

    @Override
    public void render() {
        if(frame < max) {
            applet.image(assets.getImage("ThankYou"), 0, 0);
        } else {
            save.delete();
            System.exit(1);
        }
    }

    @Override
    public boolean isRender() {
        return true;
    }
}
