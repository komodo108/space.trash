package gui.cutscene;

import processing.Assets;
import processing.PObject;

import static processing.Shape.RECTANGLE;

public abstract class Cutscene extends PObject {
    protected Assets assets;
    protected int frame;
    protected int max;

    public Cutscene(int max) {
        super(RECTANGLE);
        assets = Assets.getInstance();

        frame = 0;
        this.max = max;
    }

    public void advance() {
        frame++;
    }

    public boolean isRender() {
        return frame < max;
    }
}
