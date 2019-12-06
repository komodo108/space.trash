package processing;

import processing.core.PVector;

import static processing.Shape.CIRCLE;
import static processing.Shape.RECTANGLE;

public final class CollisionDetect {

    /**
     * Returns if value is within the range (min, max) inclusive
     * @param value the value
     * @param min the minimum value can be
     * @param max the maximum value can be
     * @return if value is within that range
     */
    public static boolean isInRange(float value, float min, float max) {
        return (value >= min) && (value <= max);
    }

    /**
     * Returns if a circle intersects a rectangle
     * @param circle the circle
     * @param rect the rectangle
     * @return if they intersect
     */
    private static boolean isCircleRectInside(PObject circle, PObject rect) {
        // https://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
        PVector circleDistance = new PVector(Math.abs(circle.pos.x - (rect.pos.x + rect.width / 2f)),
                Math.abs(circle.pos.y - (rect.pos.y + rect.height / 2f)));

        if(circleDistance.x > (rect.width / 2f + circle.width / 2f)) return false;
        if(circleDistance.y > (rect.height / 2f + circle.width / 2f)) return false;

        if(circleDistance.x <= (rect.width / 2f)) return true;
        if(circleDistance.y <= (rect.height / 2f)) return true;

        double cornerDsqrt = Math.pow(circleDistance.x - rect.width / 2f, 2) +
                Math.pow(circleDistance.y - rect.height / 2f, 2);

        return (cornerDsqrt <= Math.pow(circle.width / 2f, 2));
    }

    /**
     * True if this is inside the object
     * @param object another object
     * @return if this is inside the object
     */
    public static boolean isInside(PObject object, PObject other) {
        if(other.shape == RECTANGLE && object.shape == RECTANGLE) {
            boolean xOverlap = isInRange(object.pos.x, other.pos.x, other.pos.x + other.width) ||
                    isInRange(other.pos.x, object.pos.x, object.pos.x + object.width);

            boolean yOverlap = isInRange(object.pos.y, other.pos.y, other.pos.y + other.height) ||
                    isInRange(other.pos.y, object.pos.y, object.pos.y + object.height);

            return xOverlap && yOverlap;
        } else if(other.shape == RECTANGLE && object.shape == CIRCLE) {
            // Collision of a rectangle (this) and a circle (object)
            return isCircleRectInside(object, other);
        } else if(other.shape == CIRCLE && object.shape == RECTANGLE) {
            // Collision of a circle (this) and a rectangle (object)
            return isCircleRectInside(other, object);
        } else {
            // FIXME: Something seems to be wrong here? Try big boi bot with smol robot
            // Collision of two circles
            // https://stackoverflow.com/questions/8367512/how-do-i-detect-intersections-between-a-circle-and-any-other-circle-in-the-same
            return Math.hypot(other.pos.x - object.pos.x, other.pos.y - object.pos.y) <= (other.width + object.width);
        }
    }
}
