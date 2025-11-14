package com.pacman.game.model.ghost;

import com.badlogic.gdx.graphics.Color;
import com.pacman.game.model.Cell;
import com.pacman.game.model.Direction;
import com.pacman.game.model.Maze;
import com.pacman.game.model.Pacman;

/**
 * ✅ PINKY (Pink Ghost)
 * Personality: AMBUSHER - Predicts Pacman's moves
 * Strategy: Aim 4 cells ahead of Pacman
 */
public class Pinky extends Ghost {

    public Pinky(Cell startCell, Cell scatterCorner) {
        super("Pinky", new Color(1, 0.75f, 0.8f, 1), 1, startCell, scatterCorner);
        this.speed = 3;

    }

    @Override
    public Cell getChaseBehavior(Pacman pacman, Maze maze) {
        // 🟡 Predict 4 cells ahead
        Cell target = pacman.currentCell;

        for (int i = 0; i < 4; i++) {
            Cell next = getNextCellInDirection(target, pacman.nextDirection, maze);
            if (next != null) {
                target = next;
            }
        }

        return target;
    }

    @Override
    public Cell getScatterBehavior(Maze maze) {
        // 🟡 Go to top-left corner
        return scatterCorner;
    }

    private Cell getNextCellInDirection(Cell cell, Direction dir, Maze maze) {
        if (dir == null) return cell;

        switch (dir) {
            case UP:
                return cell.row > 0 ? maze.getCellAt(cell.row - 1, cell.col) : cell;
            case DOWN:
                return cell.row < 30 ? maze.getCellAt(cell.row + 1, cell.col) : cell;
            case LEFT:
                return cell.col > 0 ? maze.getCellAt(cell.row, cell.col - 1) : cell;
            case RIGHT:
                return cell.col < 27 ? maze.getCellAt(cell.row, cell.col + 1) : cell;
            default:
                return cell;
        }
    }
}
