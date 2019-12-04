package bots;

import common.PCObject;
import processing.core.PVector;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

import java.util.List;

public class Basebot extends PCObject implements PythonInteractable {
    private IBasebot implementation;

    public Basebot() {
        width = 15;
        height = 15;
        pos = new PVector(0, 0);
        implementation = new IBasebot(this);
    }

    @Override
    public void render() {
        applet.fill(255, 0, 0);
        applet.ellipse(pos.x, pos.y, width, height);

        int newx = (int) (pos.x + 2 * Math.cos(ori));
        int newy = (int) (pos.y + 2 * Math.sin(ori));
        applet.fill(0);
        applet.ellipse(newx, newy, height / 2f, width / 2f);
    }

    @Override
    public boolean update() {
        return super.update();
    }

    @Override
    public void interactOthers(List<PCObject> others) {
        /* no content */
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
