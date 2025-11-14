package com.pacman.game.model.ghost;
import com.pacman.game.model.Cell;
import com.pacman.game.model.Maze;
import com.badlogic.gdx.graphics.Color;
import com.pacman.game.model.Pacman;

import java.util.Random;

/**
 * ✅ INKY (Cyan Ghost)
 * Personality: TACTICAL - Strategic & unpredictable
 * Strategy: Mix of direct chase and random tactics
 */
public class Inky extends Ghost {

    private Random random = new Random();

    public Inky(Cell startCell, Cell scatterCorner) {
        super("Inky", new Color(0, 1, 1, 1), 2, startCell, scatterCorner);
        this.speed = 3;

    }

    @Override
    public Cell getChaseBehavior(Pacman pacman, Maze maze) {
        // 🔵 70% direct, 30% go to random corner
        if (random.nextDouble() < 0.7) {
            return pacman.currentCell;
        } else {
            // Pick random corner
            int corner = random.nextInt(4);
            switch (corner) {
                case 0: return maze.getCellAt(0, 0);      // Top-left
                case 1: return maze.getCellAt(0, 27);     // Top-right
                case 2: return maze.getCellAt(30, 0);     // Bottom-left
                case 3: return maze.getCellAt(30, 27);    // Bottom-right
                default: return pacman.currentCell;
            }
        }
    }

    @Override
    public Cell getScatterBehavior(Maze maze) {
        // 🔵 Go to bottom-right corner
        return scatterCorner;
    }
}
