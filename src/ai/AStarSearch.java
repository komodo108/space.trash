package ai;

import level.map.Map;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import static level.map.CellTypes.WALL;
import static main.Constants.TILE_SIZE;

/**
 * Implementation assisted by both lecture material and https://www.geeksforgeeks.org/a-search-algorithm/
 */
public class AStarSearch {
    private AStarTree tree;
    private List<AStarTree> open;
    private List<AStarTree> closed;

    private PVector start;
    private PVector end;
    private AStarTree found;

    private Map map;

    public AStarSearch(Map map) {
        this.open = new CopyOnWriteArrayList<>();
        this.closed = new CopyOnWriteArrayList<>();
        this.map = map;
    }

    public boolean find(PVector start, PVector end) {
        this.open = new CopyOnWriteArrayList<>();
        this.closed = new CopyOnWriteArrayList<>();

        this.start = new PVector((start.x / TILE_SIZE), (start.y / TILE_SIZE));
        this.end = new PVector((end.x / TILE_SIZE), (end.y / TILE_SIZE));

        tree = new AStarTree(null, this.start.copy());
        open.add(tree);

        while(!open.isEmpty()) {
            // Get the node with the least f
            Collections.sort(open);
            AStarTree q = open.remove(0);

            // Generate successors to q
            List<AStarTree> successors = new ArrayList<>();
            PVector qp = q.getMapPosition();
            if(isOkay(qp.x + 1, qp.y + 1)) successors.add(new AStarTree(q, qp.copy().add(1, 1)));
            if(isOkay(qp.x + 1, qp.y)) successors.add(new AStarTree(q, qp.copy().add(1, 0)));
            if(isOkay(qp.x + 1, qp.y - 1)) successors.add(new AStarTree(q, qp.copy().add(1, -1)));
            if(isOkay(qp.x, qp.y + 1)) successors.add(new AStarTree(q, qp.copy().add(0, 1)));
            if(isOkay(qp.x, qp.y - 1)) successors.add(new AStarTree(q, qp.copy().add(0, -1)));
            if(isOkay(qp.x - 1, qp.y + 1)) successors.add(new AStarTree(q, qp.copy().add(-1, 1)));
            if(isOkay(qp.x - 1, qp.y)) successors.add(new AStarTree(q, qp.copy().add(-1, 0)));
            if(isOkay(qp.x - 1, qp.y - 1)) successors.add(new AStarTree(q, qp.copy().add(-1, -1)));

            for(AStarTree qs : successors) {
                if(qs.getMapPosition().dist(this.end) < 1) {
                    found = qs;
                    return true;
                }

                // Set g and h, remember f = g + h
                qs.setCost(q.getCost() + q.getMapPosition().dist(qs.getMapPosition()));
                qs.setEstimate(qs.getMapPosition().dist(this.end));

                // Skip this node if there is already a node with that a smaller total in the open or closed list
                boolean skip = false;
                for(AStarTree openNode : open) {
                    if(openNode.getMapPosition().equals(qs.getMapPosition()) && openNode.getTotal() <= qs.getTotal())
                        skip = true;
                } for(AStarTree closedNode : closed) {
                    if(closedNode.getMapPosition().equals(qs.getMapPosition()) && closedNode.getTotal() <= qs.getTotal())
                        skip = true;
                }

                // If we didn't skip, add to open list
                if(!skip) open.add(qs);
            } closed.add(q);
        } return false;
    }

    private boolean isOkay(float x, float y) {
        if(isWall(x + 1, y) || isWall(x, y + 1) || isWall(x - 1, y) || isWall(x, y - 1)) return false;
        if(isWall(x + 1, y + 1) || isWall(x - 1, y + 1) || isWall(x - 1, y - 1) || isWall(x + 1, y - 1)) return false;
        return map.getMapCell(x, y) != null && map.getMapCell(x, y).getType() != WALL;
    }

    private boolean isWall(float x, float y) {
        return (map.getMapCell(x, y) != null && map.getMapCell(x, y).getType() == WALL);
    }

    public List<PVector> getPath() {
        List<PVector> path = new ArrayList<>();
        Stack<PVector> stack = new Stack<>();

        // Push from back onto a stack, then take off top to get path
        AStarTree current = found;
        do {
            stack.push(current.getPosition());
            current = current.getPrev();
        } while (current != null);
        while(!stack.empty()) {
            path.add(stack.pop());
        } return path;
    }
}
