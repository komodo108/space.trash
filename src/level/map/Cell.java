package level.map;

import processing.PObject;
import processing.core.PVector;

import static main.Constants.TILE_SIZE;
import static processing.Shape.RECTANGLE;

public class Cell extends PObject {
    private CellTypes type;

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

    @Override
    public void render() {
        applet.rect(pos.x, pos.y, width, height);
    }
}
