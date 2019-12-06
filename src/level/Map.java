package level;

import level.container.Container;
import level.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Item> items;
    private List<Container> containers;

    public Map() {
        items = new ArrayList<>();
        containers = new ArrayList<>();
        // TODO: Map has items, containers & walls
    }

    public void add(Item item) {
        items.add(item);
    }

    public void add(Container container) {
        containers.add(container);
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Container> getContainers() {
        return containers;
    }

    /**
     * Render the map and elements in it
     */
    public void render() {
        // Render all items
        List<Item> removedItems = new ArrayList<>();
        for(Item item : items) {
            if(item.update()) removedItems.add(item);
            if(!item.isDead()) item.render();
        } items.removeAll(removedItems);

        // Render all containers
        List<Container> removedContainers = new ArrayList<>();
        for(Container container : containers) {
            if(container.update()) removedContainers.add(container);
            if(!container.isDead()) container.render();
        } containers.removeAll(removedContainers);
    }
}
