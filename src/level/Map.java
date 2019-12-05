package level;

import level.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Item> items;

    public Map() {
        items = new ArrayList<>();
        // TODO: Map has items, containers & walls
    }

    public void add(Item item) { items.add(item); }

    public List<Item> getItems() {
        return items;
    }

    public void render() {
        List<Item> removed = new ArrayList<>();
        for(Item item : items) {
            if(item.update()) removed.add(item);
            if(!item.isDead()) item.render();
        } items.removeAll(removed);
    }
}
