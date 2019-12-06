package level.container;

import level.item.Item;
import processing.PObject;
import processing.Shape;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public abstract class Container extends PObject {
    protected List<Item> held;

    public Container(Shape shape, int x, int y) {
        super(shape);
        held = new ArrayList<>();
        pos = new PVector(x, y);
    }

    /**
     * Interact with a given item
     * @param item the item
     * @return if an item can be taken
     */
    public abstract boolean interact(Item item);

    /**
     * Get an item if there is one
     * @return an item
     */
    public abstract Item getItem();

    public List<Item> getHeld() {
        return held;
    }
}
