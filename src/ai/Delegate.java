package ai;


import level.map.Cell;
import level.map.Map;
import processing.PCObject;
import processing.core.PVector;

/**
 * Some content taken from lectures
 */
public class Delegate {
    private Steer steer = new Steer();
    private float pathDis = 0f;

    public void pursue(PCObject object, PCObject target) {
        float xt = target.pos.x, yt = target.pos.y;
        float xr = object.pos.x, yr = object.pos.y;
        PVector dir = new PVector(xt - xr, yt - yr);

        float dis = dir.mag();
        float speed = object.vel.mag();
        float pred = dis / speed;

        PVector pTarget = target.vel.copy().mult(pred).add(target.pos).sub(object.pos);
        steer.seek(object, pTarget);
    }

    public void flee(PCObject object, PCObject enemy) {
        PVector target = object.pos.copy().sub(enemy.pos);
        steer.seekFast(object, target);
    }

    public void wander(PCObject object) {
        PVector random = PVector.random2D().normalize().mult(20);
        steer.seekFast(object, random);
    }

    public void fleeWall(PCObject object, Map map) {
        PVector target = null;
        for(int i = 0; i < 7; i++) {
            PVector vec = object.pos.copy().add(object.vel.rotate((float) (-3 * Math.PI / 6)).rotate((float) (i * Math.PI / 6)).copy().normalize().mult(1.3f));
            Cell cell = map.getCell(vec.x, vec.y);
            if(cell != null && cell.isCollide()) target = object.pos.copy().sub(vec);
        } if(target != null) steer.seekFast(object, target);
    }

    public void setPathDis(float pathDis) {
        this.pathDis = pathDis;
    }

    public void followPath(Path path, PCObject object) {
        pathDis = path.getParam(object.pos, pathDis);
        float targetParam = pathDis + object.width;
        if(targetParam >= path.getPathLength()) {
            path = null;
            pathDis = 0f;
            return;
        } PVector target = path.getPosition(targetParam).sub(object.pos);
        steer.seekFast(object, target);
    }
}
