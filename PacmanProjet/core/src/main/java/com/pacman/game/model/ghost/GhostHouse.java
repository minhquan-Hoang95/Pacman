package com.pacman.game.model.ghost;

import com.pacman.game.model.Cell;
import com.pacman.game.model.Maze;
import com.pacman.game.model.ghost.Ghost;

import java.util.ArrayList;
import java.util.List;

/**
 * ✅ FLEXIBLE GHOST HOUSE - Any size!
 */
public class GhostHouse {

    // ═══════════════════════════════════════════════════════
    // CONFIGURABLE SIZE
    // ═══════════════════════════════════════════════════════

    public int centerRow;
    public int centerCol;
    public int width;   // ✅ CONFIGURABLE
    public int height;  // ✅ CONFIGURABLE

    public List<Ghost> ghostsInside;
    public List<Cell> cells;

    // ═══════════════════════════════════════════════════════
    // SPAWN CONTROL
    // ═══════════════════════════════════════════════════════

    private int[] spawnTimers;
    public static final int SPAWN_DELAY = 300;

    // ═══════════════════════════════════════════════════════
    // SPAWN POSITIONS (vary by size)
    // ═══════════════════════════════════════════════════════

    private Cell[] spawnPositions;

    /**
     * ✅ Constructor with SIZE parameter
     */
    public GhostHouse(int centerRow, int centerCol, int width, int height, Maze maze) {
        this.centerRow = centerRow;
        this.centerCol = centerCol;
        this.width = width;
        this.height = height;
        this.ghostsInside = new ArrayList<>();
        this.cells = new ArrayList<>();
        this.spawnTimers = new int[4];
        this.spawnPositions = new Cell[4];

        // ✅ Build ghost house cells
        buildGhostHouse(maze);

        // ✅ Define spawn positions (different for each ghost)
        defineSpawnPositions();
    }

    /**
     * ✅ Build ghost house from center with width/height
     */
    private void buildGhostHouse(Maze maze) {
        int startRow = centerRow - (height / 2);
        int startCol = centerCol - (width / 2);

        for (int r = startRow; r < startRow + height; r++) {
            for (int c = startCol; c < startCol + width; c++) {
                if (r >= 0 && r < 31 && c >= 0 && c < 28) {
                    cells.add(maze.getCellAt(r, c));
                }
            }
        }

        System.out.println("🏠 Ghost house built: " + width + "×" + height +
            " (" + cells.size() + " cells)");
    }

    /**
     * ✅ Define spawn positions
     */
    private void defineSpawnPositions() {
        int startRow = centerRow - (height / 2);
        int startCol = centerCol - (width / 2);

        // ✅ Blinky - top left
        spawnPositions[0] = getCellAt(startRow, startCol);

        // ✅ Pinky - top right
        spawnPositions[1] = getCellAt(startRow, startCol + width - 1);

        // ✅ Inky - bottom left
        spawnPositions[2] = getCellAt(startRow + height - 1, startCol);

        // ✅ Clyde - bottom right
        spawnPositions[3] = getCellAt(startRow + height - 1, startCol + width - 1);
    }

    /**
     * ✅ Check if position is inside
     */
    public boolean isInside(Cell cell) {
        return cells.contains(cell);
    }

    /**
     * ✅ Get center cell
     */
    public Cell getCenterCell() {
        for (Cell c : cells) {
            if (c.row == centerRow && c.col == centerCol) {
                return c;
            }
        }
        return cells.get(cells.size() / 2);
    }

    /**
     * ✅ Get spawn position for ghost
     */
    public Cell getSpawnPosition(int ghostId) {
        if (spawnPositions[ghostId] != null) {
            return spawnPositions[ghostId];
        }
        return getCenterCell();
    }

    private Cell getCellAt(int row, int col) {
        for (Cell c : cells) {
            if (c.row == row && c.col == col) {
                return c;
            }
        }
        return getCenterCell();
    }

    /**
     * ✅ Add ghost to house
     */
    public void addGhost(Ghost ghost) {
        if (!ghostsInside.contains(ghost)) {
            ghostsInside.add(ghost);
            ghost.moveTo(getSpawnPosition(ghost.id));
            spawnTimers[ghost.id] = 0;
            System.out.println("👻 " + ghost.name + " in ghost house");
        }
    }

    /**
     * ✅ Remove ghost from house
     */
    public void removeGhost(Ghost ghost) {
        ghostsInside.remove(ghost);
        System.out.println("👻 " + ghost.name + " left ghost house");
    }

    /**
     * ✅ Update respawn timers
     */
    public void update() {
        for (Ghost ghost : new ArrayList<>(ghostsInside)) {
            spawnTimers[ghost.id]++;

            if (spawnTimers[ghost.id] >= SPAWN_DELAY) {
                ghost.setMode(Ghost.GhostMode.CHASE);
                removeGhost(ghost);
            }
        }
    }

    /**
     * ✅ Check if should exit
     */
    public boolean shouldGhostExit(Ghost ghost) {
        return spawnTimers[ghost.id] >= SPAWN_DELAY;
    }

    public List<Ghost> getGhostsInside() {
        return new ArrayList<>(ghostsInside);
    }

    public boolean hasGhosts() {
        return !ghostsInside.isEmpty();
    }

    // ═══════════════════════════════════════════════════════
    // GETTERS (for rendering)
    // ═══════════════════════════════════════════════════════

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getCenterRow() { return centerRow; }
    public int getCenterCol() { return centerCol; }
    public List<Cell> getCells() { return cells; }
}
