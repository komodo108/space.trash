package level.map;

import processing.PObject;
import processing.core.PVector;

import static level.map.CellTypes.CCWALL;
import static level.map.CellTypes.WALL;
import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class Cell extends PObject {
    private CellTypes type;
    private int timer;
    private boolean top;

    public Cell(int x, int y, CellTypes type) {
        super(RECTANGLE);
        this.type = type;

        pos = new PVector(x, y);
        width = TILE_SIZE;
        height = TILE_SIZE;
    }

    public CellTypes getType() {
        return type;
    }

    public void setType(CellTypes type) {
        this.type = type;
    }

    public boolean isCollidable() { return type == WALL || type == CCWALL; }

    @Override
    public void render() {
        applet.stroke(0, 50);
        switch (type) {
            case WALL:
                applet.fill(0);
                break;
            case CCWALL:
                applet.fill(255, 255, 0);
                break;
            case GOAL:
                timer += top ? -5 : 5;
                if(timer >= 255) top = true;
                if(timer <= 0) top = false;

                applet.fill(timer);
                break;
            default:
                applet.noFill();
                break;
        } applet.rect(pos.x, pos.y, width, height);
        applet.fill(0);
        applet.stroke(0, 100);
    }
}
