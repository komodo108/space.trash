package ai;

import processing.core.PVector;

public enum Direction {
    NORTH('N', new PVector(0, -1)),
    SOUTH('S', new PVector(0, 1)),
    EAST('E', new PVector(-1, 0)),
    WEST('W', new PVector(1, 0));

    private char name;
    private PVector move;

    Direction(char name, PVector move) {
        this.name = name;
        this.move = move;
    }

    public PVector getMove() {
        return move;
    }

    public static Direction fromChar(char name) {
        for(Direction d : Direction.values()) {
            if(d.name == name) return d;
        } return null;
    }
}
