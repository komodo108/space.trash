package level.item;

import bots.Basebot;
import common.PCObject;

import java.util.List;

public abstract class Item extends PCObject {
    @Override
    public void interactOthers(List<PCObject> others) {
        for(PCObject object : others) {
            if(object instanceof Basebot) {
                // TODO: Fix with real collision detection
                if(pos.x - object.pos.x < 3f && pos.y - object.pos.y < 3f) {
                    // Add to the player
                    System.out.println("Picked up!");
                    dead = true;
                }
            }
        }
    }
}
