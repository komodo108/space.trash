package level.item;

import processing.PObject;
import processing.core.PVector;

import static processing.Shape.RECTANGLE;

public abstract class Item extends PObject {
    public Item(int x, int y) {
        super(RECTANGLE);
        pos = new PVector(x, y);
    }
}
