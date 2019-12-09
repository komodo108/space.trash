package level.win;

import bots.RealBasebot;
import level.map.Cell;
import level.map.Map;

import java.util.ArrayList;
import java.util.List;

import static level.map.CellTypes.GOAL;
import static main.Constants.*;

public class GoalWin extends Win {
    public GoalWin(Map map, RealBasebot bot) {
        super(map, bot);
    }

    @Override
    public boolean isWin() {
        List<Cell> cells = new ArrayList<>();
        for(int x = 0; x < MAP_WIDTH; x++) {
            for(int y = 0; y < MAP_HEIGHT; y++) {
                if(map.getMapCell(x, y).getType() == GOAL) cells.add(map.getMapCell(x, y));
            }
        } for(Cell cell : cells) {
            if(bot.pos.dist(cell.pos.copy().add(cell.width / 2, cell.height / 2)) < TILE_SIZE) return true;
        } return false;
    }
}