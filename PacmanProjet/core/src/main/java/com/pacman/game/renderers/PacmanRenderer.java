package com.pacman.game.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pacman.game.model.Pacman;
import com.pacman.game.model.Direction;

/**
 * Renders Pacman on screen
 * Draws a yellow circle with rotation based on direction
 */
public class PacmanRenderer {
    private static final int CELL_SIZE = 32;
    private static final Color PACMAN_COLOR = Color.YELLOW;
    private static final float PACMAN_RADIUS = 13.0f;  // Slightly less than half cell (16)

    private ShapeRenderer shapeRenderer;

    public PacmanRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }

    /**
     * ✅ Render Pacman at current position
     * Draws a yellow circle
     *
     * @param pacman Pacman object
     * @param offsetX Camera offset X
     * @param offsetY Camera offset Y
     */
    public void render(Pacman pacman, float offsetX, float offsetY) {
        if (pacman == null || pacman.currentCell == null) {
            return;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(PACMAN_COLOR);

        float x = pacman.currentCell.col * CELL_SIZE + offsetX + CELL_SIZE / 2.0f;
        float y = pacman.currentCell.row * CELL_SIZE + offsetY + CELL_SIZE / 2.0f;
        shapeRenderer.circle(x, y, PACMAN_RADIUS);  // ✅ Centered!

        shapeRenderer.end();

        // Optional: Draw direction indicator (debug)
        // drawDirectionIndicator(pacman, x, y);
    }

    /**
     * Optional: Draw a line showing Pacman's direction (for debugging)
     */
    private void drawDirectionIndicator(Pacman pacman, float x, float y) {
        if (pacman == null) return;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        float lineLength = 8.0f;
        float endX = x;
        float endY = y;

        switch (pacman.direction) {
            case UP:
                endY += lineLength;
                break;
            case DOWN:
                endY -= lineLength;
                break;
            case LEFT:
                endX -= lineLength;
                break;
            case RIGHT:
                endX += lineLength;
                break;
            case NONE:
                break;
        }

        shapeRenderer.line(x, y, endX, endY);
        shapeRenderer.end();
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }



}

