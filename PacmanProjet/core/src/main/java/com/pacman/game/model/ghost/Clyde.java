package com.pacman.game.model.ghost;

import com.pacman.game.model.Cell;
import com.pacman.game.model.Maze;
import com.pacman.game.model.Pacman;
import com.badlogic.gdx.graphics.Color;
import java.util.Random;

/**
 * ✅ CLYDE (Orange Ghost)
 * Personality: RANDOM - Silly & unpredictable
 * Strategy: 50% chase, 50% random (and shy!)
 */
public class Clyde extends Ghost {

    private Random random = new Random();

    public Clyde(Cell startCell, Cell scatterCorner) {
        super("Clyde", new Color(1, 0.8f, 0.5f, 1), 3, startCell, scatterCorner);
        this.speed = 3;

    }

    @Override
    public Cell getChaseBehavior(Pacman pacman, Maze maze) {
        // 🟠 50% chase, 50% random
        if (random.nextDouble() < 0.5) {
            return pacman.currentCell;
        } else {
            return maze.getRandomCell();
        }
    }

    @Override
    public Cell getScatterBehavior(Maze maze) {
        // 🟠 Go to bottom-left corner
        return scatterCorner;
    }
}

