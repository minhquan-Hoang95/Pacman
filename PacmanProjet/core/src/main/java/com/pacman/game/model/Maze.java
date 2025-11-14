package com.pacman.game.model;

import com.pacman.game.model.Cell;
import com.pacman.game.model.Direction;

import java.util.*;

/**
 * ✅ MAZE CLASS - Complete
 *
 * Représente la structure du labyrinthe
 * Gère:
 * - Accès aux cellules (row, col)
 * - Vérification de mouvement (canMove)
 * - Affichage debug
 */
public class Maze {

    // ═══════════════════════════════════════════
    // DONNÉES
    // ═══════════════════════════════════════════

    public String id;           // ID unique du maze
    public int rows, cols;      // Dimensions
    public Cell[][] cells;      // Grille 2D de cellules


    // ═══════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════

    /**
     * ✅ Créer Maze depuis MazeData (récupérée de l'API)
     */
    public Maze(String id, int rows, int cols, Cell[][] cells) {
        this.id = id;
        this.rows = rows;
        this.cols = cols;
        this.cells = cells;

        System.out.println("✅ Maze created: " + id + " (" + rows + "×" + cols + ")");
    }


    // ═══════════════════════════════════════════
    // ACCESSORS
    // ═══════════════════════════════════════════

    /**
     * ✅ Récupérer cellule à (r, c)
     */
    public Cell getCellAt(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return null;
        return cells[r][c];
    }


    // ═══════════════════════════════════════════
    // MOVEMENT
    // ═══════════════════════════════════════════

    /**
     * ✅ Vérifier si on peut se déplacer d'une cell à une autre
     *
     * Logique:
     * 1. Get neighbor dans direction
     * 2. Check si exists + isWalkable + linked
     */
    public boolean canMove(Cell from, Direction dir) {
        if (from == null || dir == null || dir == Direction.NONE) return false;

        // Get neighbor
        Cell to = null;
        switch(dir) {
            case UP -> to = from.north;
            case DOWN -> to = from.south;
            case LEFT -> to = from.west;
            case RIGHT -> to = from.east;
            default -> {}
        }

        // Check: exists + walkable + linked
        return to != null && to.isWalkable && from.isLinked(to);
    }

    /**
     * ✅ Alternate: simpler version (if links only)
     */
    public boolean canMoveTo(Cell to) {
        return to != null && to.isWalkable;
    }


    // ═══════════════════════════════════════════
    // DEBUG
    // ═══════════════════════════════════════════

    /**
     * ✅ Afficher structure du maze
     */
    public void printDebug() {
        System.out.println("\n🗺️  ════════════════════════════════════");
        System.out.println("    MAZE: " + id);
        System.out.println("    Size: " + rows + "×" + cols + " cells");
        System.out.println("════════════════════════════════════");

        // Grille de symboles
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                System.out.print(cell.isWalkable ? "." : "X");
            }
            System.out.println();
        }

        System.out.println("════════════════════════════════════\n");
    }

    /**
     * ✅ Afficher informations d'une cell
     */
    public void printCellInfo(int r, int c) {
        Cell cell = getCellAt(r, c);
        if (cell == null) {
            System.out.println("❌ Cell [" + r + "," + c + "] out of bounds!");
            return;
        }

        System.out.println("\n📍 Cell [" + r + "," + c + "]");
        System.out.println("   Walkable: " + cell.isWalkable);
        System.out.println("   Pellet: " + cell.hasPellet);
        System.out.println("   Links: " + cell.links().size());
        System.out.println("   Neighbors:");
        if (cell.north != null) System.out.println("     ↑ North: " + cell.north);
        if (cell.south != null) System.out.println("     ↓ South: " + cell.south);
        if (cell.west != null) System.out.println("     ← West: " + cell.west);
        if (cell.east != null) System.out.println("     → East: " + cell.east);
    }


    // ═══════════════════════════════════════════
    // UTILITY
    // ═══════════════════════════════════════════

    /**
     * ✅ Compter cellules walkable
     */
    public int countWalkableCells() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (cells[r][c].isWalkable) count++;
            }
        }
        return count;
    }

    /**
     * ✅ Compter cellules avec pellets
     */
    public int countPellets() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (cells[r][c].hasPellet) count++;
            }
        }
        return count;
    }

    /**
     * ✅ Trouver cellule la plus proche d'une autre (Manhattan distance)
     */
    public Cell findNearestWalkable(int startR, int startC) {
        Cell start = getCellAt(startR, startC);
        if (start == null) return null;

        Cell nearest = null;
        int minDist = Integer.MAX_VALUE;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (cells[r][c].isWalkable) {
                    int dist = Math.abs(r - startR) + Math.abs(c - startC);
                    if (dist > 0 && dist < minDist) {
                        minDist = dist;
                        nearest = cells[r][c];
                    }
                }
            }
        }

        return nearest;
    }

    @Override
    public String toString() {
        return "Maze{" +
            "id='" + id + '\'' +
            ", size=" + rows + "×" + cols +
            ", walkable=" + countWalkableCells() +
            ", pellets=" + countPellets() +
            '}';
    }

    public Cell getRandomCell() {
        Random rand = new Random();
        int r, c;
        Cell cell;
        do {
            r = rand.nextInt(rows);
            c = rand.nextInt(cols);
            cell = getCellAt(r, c);
        } while (cell == null || !cell.isWalkable);
        return cell;
    }

    public int mazeDistance(Cell cell, Cell currentCell) {
        if (cell == null || currentCell == null) return -1;

        // BFS pour trouver la distance minimale
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();
        Map<Cell, Integer> distanceMap = new HashMap<>();

        queue.add(currentCell);
        visited.add(currentCell);
        distanceMap.put(currentCell, 0);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int currentDistance = distanceMap.get(current);

            if (current == cell) {
                return currentDistance;
            }

            for (Cell neighbor : current.links()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    distanceMap.put(neighbor, currentDistance + 1);
                }
            }
        }

        return -1; // Cell non atteignable
    }

    public void printMaze() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = getCellAt(r, c);
                if (cell.isWalkable) {
                    System.out.print(" . ");
                } else {
                    System.out.print(" # ");
                }
            }
            System.out.println();
        }
    }

    public String getInfo() {
        return "Maze ID: " + id + ", Size: " + rows + "x" + cols +
            ", Walkable Cells: " + countWalkableCells() +
            ", Pellets: " + countPellets();
    }

    public Cell getCenterCell() {
        int centerRow = rows / 2;
        int centerCol = cols / 2;
        return getCellAt(centerRow, centerCol);
    }
}



