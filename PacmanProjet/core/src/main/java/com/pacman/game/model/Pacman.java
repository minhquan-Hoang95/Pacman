
//**The problem:**
//    1. ❌ `update()` never calls `move()`
//    2. ❌ `update()` tries to use `nextDirection.getdRow()` even if it's NONE
//    3. ❌ No actual position change happens
//
//---
//
//    # ✅ **FIXED VERSION - Pacman.java**

package com.pacman.game.model;

public class Pacman {
    public Cell currentCell;
    public Direction direction;
    public Direction nextDirection;
    public int lives;

    public Pacman(Cell startCell) {
        this.currentCell = startCell;
        this.direction = Direction.NONE;
        this.nextDirection = Direction.NONE;
        this.lives = 3;
    }

    public int getRow() {
        return currentCell != null ? currentCell.row : -1;
    }

    public int getCol() {
        return currentCell != null ? currentCell.col : -1;
    }

    private Cell getNextCell(Direction dir) {
        if (currentCell == null || dir == Direction.NONE) return null;

        switch(dir) {
            case UP: return currentCell.south;
            case DOWN: return currentCell.north;
            case LEFT: return currentCell.west;
            case RIGHT: return currentCell.east;
            default: return null;
        }
    }

    public boolean canMove(Direction dir) {
        if (dir == Direction.NONE) return false;
        Cell nextCell = getNextCell(dir);
        if (nextCell == null) return false;
        return currentCell.isLinked(nextCell);
    }

    public void update(Maze maze) {
        if (currentCell == null) return;

        if (canMove(nextDirection)) {
            direction = nextDirection;
            currentCell = getNextCell(nextDirection);
            return;
        }

        if (canMove(direction)) {
            currentCell = getNextCell(direction);
        }
    }

    public void setNextDirection(Direction dir) {
        this.nextDirection = dir;
    }

    public void turnAround() {
        direction = direction.opposite();
    }

    public void loseLive() {
        lives--;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    @Override
    public String toString() {
        return String.format("Pacman(%d,%d) Direction=%s Lives=%d",
            getRow(), getCol(), direction, lives);
    }

}
