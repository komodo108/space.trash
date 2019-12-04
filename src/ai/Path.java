package ai;

import processing.core.PVector;

import java.util.List;

public class Path {
    private List<PVector> segments;
    private float pathLength;
    private PVector B;

    public Path(List<PVector> segments) {
        this.segments = segments;

        // Calculate length
        for(int i = 0; i < segments.size() - 1; i++) {
            pathLength += getLength(segments.get(i), segments.get(i + 1));
        }
    }

    // Uncomment this to show A* path
    public List<PVector> getList() {
        return segments;
    }

    /**
     * Content adapted from lectures
     */
    public float getParam(PVector position, float old) {
        if(old >= pathLength) old = pathLength - 1;
        PVector O = getPosition(old);
        PVector OP = position.copy().sub(O);
        PVector OB = B.copy().sub(O);

        float ob2 = OB.dot(OB);
        if(ob2 == 0) ob2 = 1f;
        float opob = OP.dot(OB);
        float t = opob / ob2;

        if(t < 0f) t = 0f;
        else if(t > 1f) t = 1f;
        OB.mult(t);
        return old + OB.mag();
    }

    public float getPathLength() {
        return pathLength;
    }

    public PVector getPosition(float param) {
        if(param <= 0) {
            B = segments.get(1).copy();
            return segments.get(0).copy();
        }

        for(int i = 1; i < segments.size() - 1; i++) {
            if(param >= 0.1f) {
                param -= getLength(segments.get(i - 1), segments.get(i));
            } else {
                PVector end = segments.get(i).copy();
                PVector AB = end.sub(segments.get(i - 1)).normalize().mult(param);
                B = segments.get(i + 1).copy();
                return segments.get(i - 1).copy().add(AB);
            }
        } B = segments.get(segments.size() - 1).copy();
        return segments.get(segments.size() - 1).copy();
    }

    private float getLength(PVector A, PVector B) {
        PVector end = B.copy().sub(A);
        return end.mag();
    }

}
