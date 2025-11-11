package com.pacman.model.components.maze;

public enum Direction {
    UP(-1, 0), // North
    DOWN(1, 0), // South
    LEFT(0, -1), // West
    RIGHT(0, 1), // East
    NONE(0, 0);  // standing still / no input

    private final int dRow;
    private final int dCol;

    Direction(int dRow, int dCol) {
        this.dRow = dRow;
        this.dCol = dCol;
    }

    public int dRow() {
        return dRow;
    }

    public int dCol() {
        return dCol;
    }

    /**
     * Returns the neighbor of the given cell in this direction,
     * or null if out of bounds (or NONE).
     */
    public Cell neighborOf(Cell cell) {
        if (cell == null) return null;

        return switch (this)
        {
            case UP -> cell.north;
            case DOWN -> cell.south;
            case LEFT -> cell.west;
            case RIGHT -> cell.east;
            case NONE -> null;
        };

    }

    /** Opposite direction (useful for ghost logic, tunnel logic, etc.) */
    public Direction opposite() {
        return switch (this)
        {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case NONE -> NONE;
        };
    }

}
