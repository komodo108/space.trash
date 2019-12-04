package ai;

/**
 * Implementation assisted by both lecture material and https://www.geeksforgeeks.org/a-search-algorithm/
 */
public class AStarSearch {
    /*private AStarTree tree;
    private List<AStarTree> open;
    private List<AStarTree> closed;

    private PVector start;
    private PVector end;
    private AStarTree found;

    private Map map;

    public AStarSearch(Map map) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.map = map;
    }

    public boolean find(PVector start, PVector end) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();

        this.start = new PVector((int) start.x, (int) start.y);
        this.end = new PVector((int) end.x, (int) end.y);;

        tree = new AStarTree(null, this.start.copy());
        open.add(tree);

        while(!open.isEmpty()) {
            // Get the node with the least f
            Collections.sort(open);
            AStarTree q = open.remove(0);

            // Generate successors to q
            List<AStarTree> successors = new ArrayList<>();
            PVector qp = q.getPosition();
            if(isOkay(qp.x + 1, qp.y + 1)) successors.add(new AStarTree(q, qp.copy().add(1, 1)));
            if(isOkay(qp.x + 1, qp.y)) successors.add(new AStarTree(q, qp.copy().add(1, 0)));
            if(isOkay(qp.x + 1, qp.y - 1)) successors.add(new AStarTree(q, qp.copy().add(1, -1)));
            if(isOkay(qp.x, qp.y + 1)) successors.add(new AStarTree(q, qp.copy().add(0, 1)));
            if(isOkay(qp.x, qp.y - 1)) successors.add(new AStarTree(q, qp.copy().add(0, -1)));
            if(isOkay(qp.x - 1, qp.y + 1)) successors.add(new AStarTree(q, qp.copy().add(-1, 1)));
            if(isOkay(qp.x - 1, qp.y)) successors.add(new AStarTree(q, qp.copy().add(-1, 0)));
            if(isOkay(qp.x - 1, qp.y - 1)) successors.add(new AStarTree(q, qp.copy().add(-1, -1)));

            for(AStarTree qs : successors) {
                if(qs.getPosition().dist(this.end) < 1) {
                    found = qs;
                    return true;
                }

                // Set g and h, remember f = g + h
                qs.setCost(q.getCost() + q.getPosition().dist(qs.getPosition()));
                qs.setEstimate(qs.getPosition().dist(end));

                // Skip this node if there is already a node with that a smaller total in the open or closed list
                boolean skip = false;
                for(AStarTree openNode : open) {
                    if(openNode.getPosition().equals(qs.getPosition()) && openNode.getTotal() <= qs.getTotal())
                        skip = true;
                } for(AStarTree closedNode : closed) {
                    if(closedNode.getPosition().equals(qs.getPosition()) && closedNode.getTotal() <= qs.getTotal())
                        skip = true;
                }

                // If we didn't skip, add to open list
                if(!skip) open.add(qs);
            } closed.add(q);
        } return false;
    }

    private boolean isOkay(float x, float y) {
        return map.getCell(x, y) != null && map.getCell(x, y).getTag() == 'r';
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
    }*/
    // TODO: Implement map, and this?
}
