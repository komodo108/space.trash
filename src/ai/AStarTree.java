package ai;

import processing.core.PVector;

public class AStarTree implements Comparable<AStarTree> {

    private AStarTree prev;
    private PVector position;
    private double cost;
    private double estimate;

    public AStarTree(AStarTree prev, PVector position) {
        this.prev = prev;
        this.position = position;
    }

    public AStarTree getPrev() {
        return prev;
    }

    public void setPrev(AStarTree prev) {
        this.prev = prev;
    }

    public PVector getPosition() {
        return position;
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

    public double getEstimate() {
        return estimate;
    }

    @Override
    public int compareTo(AStarTree o) {
        return Double.compare(cost + estimate, o.cost + o.estimate);
    }
}
