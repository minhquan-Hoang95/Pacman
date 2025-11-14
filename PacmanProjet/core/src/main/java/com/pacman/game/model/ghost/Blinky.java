package com.pacman.game.model.ghost;

import com.badlogic.gdx.graphics.Color;
import com.pacman.game.model.Cell;
import com.pacman.game.model.Maze;
import com.pacman.game.model.Pacman;

/**
 * ✅ BLINKY (Red Ghost)
 * Personality: AGGRESSIVE - Direct chaser
 * Strategy: Always chase Pacman directly
 */
public class Blinky extends Ghost {

    public Blinky(Cell startCell, Cell scatterCorner) {
        super("Blinky", new Color(1, 0, 0, 1), 0, startCell, scatterCorner);
        this.speed = 3;
    }

    @Override
    public Cell getChaseBehavior(Pacman pacman, Maze maze) {
        // 🔴 Simple: Chase Pacman directly - no prediction
        return pacman.currentCell;
    }

    @Override
    public Cell getScatterBehavior(Maze maze) {
        // 🔴 Go to top-right corner
        return scatterCorner;
    }

}
