package com.pacman.model.components.maze.character;

import com.pacman.model.components.maze.Cell;
import com.pacman.model.components.maze.Grid;

public class Blinky extends Ghost{
    private Grid grid;
    public Blinky(Cell spawnCell, Grid grid) {
        super("Blinky", spawnCell, null);
    }

    @Override
    protected void chase() {
        // Blinky's chase behavior implementation
        // 1 : Target Pacman's current position directly
        Cell pacmanCell =
        // 2 : Use shortest pathfinding to reach Pacman



    }
}
