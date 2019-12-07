package ai;

import processing.core.PVector;

import static main.Constants.TILE_SIZE;

public class AStarTree implements Comparable<AStarTree> {

    private AStarTree prev;
    private PVector position;
    private double cost;
    private double estimate;

    public AStarTree(AStarTree prev, PVector position) {
        this.prev = prev;
        this.position = new PVector(position.x * TILE_SIZE, position.y * TILE_SIZE);
    }

    public AStarTree getPrev() {
        return prev;
    }

    public PVector getPosition() {
        return position;
    }

    public PVector getMapPosition() {
        return new PVector(position.x / TILE_SIZE, position.y / TILE_SIZE);
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public double getTotal() {
        return cost + estimate;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(AStarTree o) {
        return Double.compare(cost + estimate, o.cost + o.estimate);
    }
}
