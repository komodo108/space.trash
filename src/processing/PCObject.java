package processing;

import ai.Steer;
import level.map.Cell;
import level.map.CellTypes;
import level.map.Map;
import processing.core.PVector;

import java.util.List;

import static level.map.CellTypes.WALL;
import static main.Constants.*;

public abstract class PCObject extends PObject {
    public PVector vel;
    public float ori;
    public Steer steer;
    public Map map;

    /**
     * Make a new Processing object
     */
    public PCObject(Map map, Shape shape) {
        super(shape);
        this.map = map;

        vel = new PVector(0, 0);
        steer = new Steer();
        ori = 0;
    }

    public void integrate() {
        pos.add(vel);
        interactMap();
        steer.smoothFace(this);
    }

    private void interactMap() {
        List<Cell> cells = map.getCells(this);

        // Collision with the walls
        if(pos.x < 0 || pos.x > WIDTH) {
            vel.x = -vel.x;
            pos.x += pos.x < -2 ? 1 : (pos.x > WIDTH + 2 ? -1 : 0);
        } if(pos.y < 0 || pos.y > HEIGHT) {
            vel.y = -vel.y;
            pos.y += pos.y < -2 ? 1 : (pos.y > HEIGHT + 2 ? -1 : 0);
        }

        // Collision with the map
        // TODO: Also do corner only collision - i.e. if the top left corner is colliding then push it up and right
        if(cellIs(cells, 0, WALL) && cellIs(cells, 2, WALL)) vel.x += -vel.x + PUSHING_AMOUNT;
        if(cellIs(cells, 2, WALL) && cellIs(cells, 3, WALL)) vel.y += -vel.y - PUSHING_AMOUNT;
        if(cellIs(cells, 0, WALL) && cellIs(cells, 1, WALL)) vel.y += -vel.y + PUSHING_AMOUNT;
        if(cellIs(cells, 1, WALL) && cellIs(cells, 3, WALL)) vel.x += -vel.x - PUSHING_AMOUNT;
    }

    private boolean cellIs(List<Cell> cells, int index, CellTypes type) {
        return cells.get(index) != null && cells.get(index).getType() == type;
    }

    /**
     * Interact with other objects.
     * Implementation can be on any side providing this is called correctly
     * @param others the other objects
     */
    public abstract void interactOthers(List<PObject> others);
}
