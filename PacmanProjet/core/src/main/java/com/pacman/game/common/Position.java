package com.pacman.game.common;

/**
 * Represents a position in the game grid.
 */
public final class Position {

    private final int row;    // y logique : ligne dans la grille
    private final int col;    // x logique : colonne dans la grille

    /** Constructor
     *  Creates a Position object with specified row and column.
     * @param row line ( 0 at the top, increasing downwards)
     * @param col column ( 0 at the left, increasing rightwards)
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /** Getter for row
     * @return the row of the position
     */
    public int getRow() {
        return row;
    }

    /** Getter for column
     * @return the column of the position
     */
    public int getCol() {
        return col;
    }

    /** Return a new Position translated by the given deltas.
     * @param deltaRow the change in row
     * @param deltaCol the change in column
     * @return a new Position object translated by the specified deltas
     */
    public Position translate(int deltaRow, int deltaCol) {
        return new Position(this.row + deltaRow, this.col + deltaCol);
    }

    /** Distance de Manhattan entre deux positions for pathfinding.
     * @param other the other position
     * @return the Manhattan distance between this position and the other position
     */
    public int manhattanDistance(Position other) {
        return Math.abs(this.row - other.row) + Math.abs(this.col - other.col);
    }

    /** Distance Euclidienne entre deux positions.
     * @param other the other position
     * @return the Euclidean distance between this position and the other position
     */
    public double euclideanDistance(Position other) {
        return Math.sqrt(Math.pow(this.row - other.row, 2) + Math.pow(this.col - other.col, 2));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(row);
        result = 31 * result + Integer.hashCode(col);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" + "row=" + row + ", col=" + col + '}';
        }
}
