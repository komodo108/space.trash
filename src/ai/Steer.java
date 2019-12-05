package ai;

import processing.PCObject;
import processing.core.PVector;

import static main.Constants.*;

/**
 * Some content taken from lectures
 */
public class Steer {
    public void smoothFace(PCObject object) {
        // Smooth facing
        float tori = (float) Math.atan2(object.vel.y, object.vel.x);
        if(Math.abs(tori - object.ori) <= ORIENTATION_BASE) {
            object.ori = tori;
            return;
        }

        if(tori < object.ori) {
            if(object.ori - tori < Math.PI) object.ori -= ORIENTATION_BASE;
            else object.ori += Math.PI / 32;
        } else {
            if(object.ori - tori < Math.PI) object.ori += ORIENTATION_BASE;
            else object.ori -= Math.PI / 32;
        } if (object.ori > Math.PI) object.ori -= 2 * Math.PI;
        else if (object.ori < -Math.PI) object.ori += 2 * Math.PI;

    }

    public void seekFast(PCObject object, PVector target) {
        PVector tcopy = target.copy();
        tcopy.normalize().mult(MAX_ACCELERATION);
        object.vel.add(tcopy);
        if(object.vel.mag() > MAX_SPEED) object.vel.normalize().mult(MAX_SPEED);
    }

    public void seek(PCObject object, PVector target) {
        PVector tcopy = target.copy();
        float dis = tcopy.mag();
        if(dis > TARGET_RADIUS) {
            float tSpeed = MAX_SPEED;
            if(dis <= SLOW_RADIUS) tSpeed = MAX_SPEED * dis / SLOW_RADIUS;

            PVector tVel = tcopy.normalize().mult(tSpeed);
            PVector acc = tVel.copy().sub(object.vel);

            if(acc.mag() > MAX_ACCELERATION) acc.normalize().mult(MAX_ACCELERATION);
            object.vel.add(acc);
            if(object.vel.mag() > MAX_SPEED) object.vel.normalize().mult(MAX_SPEED);
        } object.vel.mult(DAMPING);
    }
}
