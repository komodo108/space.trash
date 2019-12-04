package ai;


import common.PCObject;
import processing.core.PVector;

/**
 * Some content taken from lectures
 */
public class Delegate {
    private Steer steer = new Steer();

    public void pursue(PCObject robot, PCObject target) {
        float xt = target.pos.x, yt = target.pos.y;
        float xr = robot.pos.x, yr = robot.pos.y;
        PVector dir = new PVector(xt - xr, yt - yr);

        float dis = dir.mag();
        float speed = robot.vel.mag();
        float pred = dis / speed;

        PVector pTarget = target.vel.copy().mult(pred).add(target.pos).sub(robot.pos);
        steer.seek(robot, pTarget);
    }

    public void flee(PCObject object, PCObject enemy) {
        PVector target = object.pos.copy().sub(enemy.pos);
        steer.seekFast(object, target);
    }

    public void wander(PCObject robot) {
        PVector random = PVector.random2D().normalize().mult(20);
        steer.seekFast(robot, random);
    }

    /*public void fleeWall(PCObject robot, Map map) {
        PVector target = null;
        for(int i = 0; i < 7; i++) {
            PVector vec = robot.pos.copy().add(robot.vel.rotate((float) (-3 * Math.PI / 6)).rotate((float) (i * Math.PI / 6)).copy().normalize().mult(1.3f));
            Cell cell = map.getCell(vec.x, vec.y);
            if(cell != null && cell.getTag() == 's') target = robot.pos.copy().sub(vec);
        } if(target != null) steer.seekFast(robot, target);
    }*/

}
