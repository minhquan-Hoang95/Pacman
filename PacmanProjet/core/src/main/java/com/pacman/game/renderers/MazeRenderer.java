package com.pacman.game.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.pacman.game.model.Maze;
import com.pacman.game.model.Cell;

public class MazeRenderer {
    private static final int CELL_SIZE = 32;
    private static final Color WALL_COLOR = Color.BLUE;
    private static final Color GHOST_HOUSE_COLOR = Color.RED;
    private static final float WALL_THICKNESS = 2.0f;

    private ShapeRenderer shapeRenderer;

    public MazeRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(Maze maze) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(WALL_THICKNESS);

        // ✅ CALCULATE MAZE DIMENSIONS
        int mazeWidth = maze.cols * CELL_SIZE;   // 28 * 32 = 896
        int mazeHeight = maze.rows * CELL_SIZE;  // 31 * 32 = 992

        // ✅ SCREEN DIMENSIONS (LibGDX window)
        float screenWidth = Gdx.graphics.getWidth();   // e.g., 1024
        float screenHeight = Gdx.graphics.getHeight(); // e.g., 768

        // ✅ CALCULATE CENTER OFFSET
        float offsetX = (screenWidth - mazeWidth) / 2.0f;
        float offsetY = (screenHeight - mazeHeight) / 2.0f;

//        System.out.println("Screen: " + screenWidth + "×" + screenHeight);
//        System.out.println("Maze: " + mazeWidth + "×" + mazeHeight);
//        System.out.println("Offset: (" + offsetX + ", " + offsetY + ")");

        // ✅ Ghost House and Tunnel zones
        int midRow = maze.rows / 2;
        int midCol = maze.cols / 2;
        int minRow = midRow - 2;
        int maxRow = midRow + 1;
        int minCol = midCol - 4;
        int maxCol = midCol + 3;
        int tunnelRow = maze.rows / 2;

        for (int r = 0; r < maze.rows; r++) {
            for (int c = 0; c < maze.cols; c++) {
                Cell cell = maze.cells[r][c];
                if (cell == null) continue;

                // ✅ ADD OFFSET to all coordinates!
                float x = offsetX + c * CELL_SIZE ;
//                float y = offsetY + (maze.rows - 1 - r) * CELL_SIZE + 0.5f;
                float y = offsetY + r * CELL_SIZE;

                Cell north = maze.getCellAt(r - 1, c);
                Cell south = maze.getCellAt(r + 1, c);
                Cell east = maze.getCellAt(r, c + 1);
                Cell west = maze.getCellAt(r, c - 1);

                // Check zones
//                boolean isGhostHouse = (r >= minRow && r <= maxRow &&
//                    c >= minCol && c <= maxCol);
//                boolean isTunnel = (r == tunnelRow && (c == 0 || c == maze.cols - 1));
//
//                boolean northInGH = (north != null && north.row >= minRow && north.row <= maxRow &&
//                    north.col >= minCol && north.col <= maxCol);
//                boolean eastInGH = (east != null && east.row >= minRow && east.row <= maxRow &&
//                    east.col >= minCol && east.col <= maxCol);
//                boolean southInGH = (south != null && south.row >= minRow && south.row <= maxRow &&
//                    south.col >= minCol && south.col <= maxCol);
//                boolean westInGH = (west != null && west.row >= minRow && west.row <= maxRow &&
//                    west.col >= minCol && west.col <= maxCol);

                // ✅ NORTH wall
                if (north == null || !cell.isLinked(north)) {
                    Color wallColor = WALL_COLOR;
//                    if (isGhostHouse || northInGH) {
//                        wallColor = GHOST_HOUSE_COLOR;
//                    }
//                    if (isTunnel) {
//                        wallColor = Color.GREEN;
//                    }
                    shapeRenderer.setColor(wallColor);
                    shapeRenderer.line(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);
                }

                // ✅ EAST wall
                if (east == null || !cell.isLinked(east)) {
                    Color wallColor = WALL_COLOR;
//                    if (isGhostHouse || eastInGH) {
//                        wallColor = GHOST_HOUSE_COLOR;
//                    }
//                    if (isTunnel) {
//                        wallColor = Color.GREEN;
//                    }
                    shapeRenderer.setColor(wallColor);
                    shapeRenderer.line(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);
                }

                // ✅ SOUTH wall
                if (south == null || !cell.isLinked(south)) {
                    Color wallColor = WALL_COLOR;
//                    if (isGhostHouse || southInGH) {
//                        wallColor = GHOST_HOUSE_COLOR;
//                    }
//                    if (isTunnel) {
//                        wallColor = Color.GREEN;
//                    }
                    shapeRenderer.setColor(wallColor);
                    shapeRenderer.line(x, y, x + CELL_SIZE, y);
                }

                // ✅ WEST wall
                if (west == null || !cell.isLinked(west)) {
                    Color wallColor = WALL_COLOR;
//                    if (isGhostHouse || westInGH) {
//                        wallColor = GHOST_HOUSE_COLOR;
//                    }W
//                    if (isTunnel) {
//                        wallColor = Color.GREEN;
//                    }
                    shapeRenderer.setColor(wallColor);
                    shapeRenderer.line(x, y, x, y + CELL_SIZE);
                }
            }
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1.0f);
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }

    public void setProjectionMatrix(Matrix4 combined) {
        shapeRenderer.setProjectionMatrix(combined);
    }
}
