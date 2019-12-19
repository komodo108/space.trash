package level.item;

import processing.PObject;
import processing.Shape;
import processing.core.PVector;

public abstract class Item extends PObject {
    private Tags tag;

    public Item(Shape shape, float x, float y, Tags tag) {
        super(shape);
        pos = new PVector(x, y);
        this.tag = tag;
    }

    public Tags getTag() {
        return tag;
    }
}
