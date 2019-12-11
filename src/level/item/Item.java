package level.item;

import processing.PObject;
import processing.Shape;
import processing.core.PVector;

public abstract class Item extends PObject {
    private Tags tag;

    public Item(Shape shape, int x, int y, Tags tag) {
        super(shape);
        pos = new PVector(x, y);
        tag = this.tag;
    }

    public Tags getTag() {
        return tag;
    }
}
