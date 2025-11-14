package com.pacman.game.renderers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.pacman.game.model.ghost.Ghost;
import com.pacman.game.model.ghost.GhostHouse;
import java.util.List;

public class GhostRenderer {

    private ShapeRenderer shapeRenderer;
    private static final int CELL_SIZE = 32;

    public GhostRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }

    /**
     * ✅ RENDER GHOST HOUSE - No setLineWidth()!
     */
    public void renderGhostHouse(GhostHouse house, float offsetX, float offsetY) {
        //shapeRenderer.setProjectionMatrix(null);

        float startX = offsetX + (house.getCenterCol() - house.getWidth() / 2) * CELL_SIZE;
        float startY = offsetY + (house.getCenterRow() - house.getHeight() / 2) * CELL_SIZE;
        float width = house.getWidth() * CELL_SIZE;
        float height = house.getHeight() * CELL_SIZE;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background
        shapeRenderer.setColor(new Color(0.2f, 0, 0.4f, 0.7f));
        shapeRenderer.rect(startX, startY, width, height);

        // Border (thick lines using rectangles)
        shapeRenderer.setColor(new Color(1, 0.5f, 0, 1));
        float borderThickness = 3;

        // Top
        shapeRenderer.rect(startX, startY + height - borderThickness, width, borderThickness);
        // Bottom
        shapeRenderer.rect(startX, startY, width, borderThickness);
        // Left
        shapeRenderer.rect(startX, startY, borderThickness, height);
        // Right
        shapeRenderer.rect(startX + width - borderThickness, startY, borderThickness, height);

        shapeRenderer.end();
    }

    /**
     * ✅ Render all ghosts
     */
    public void render(List<Ghost> ghosts, GhostHouse house, float offsetX, float offsetY) {
        //shapeRenderer.setProjectionMatrix(null);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Ghost ghost : ghosts) {
            renderGhost(ghost, offsetX, offsetY);
        }

        shapeRenderer.end();
    }

    /**
     * ✅ Render single ghost
     */
    private void renderGhost(Ghost ghost, float offsetX, float offsetY) {
        float x = offsetX + (ghost.cell.col * CELL_SIZE) + (CELL_SIZE / 2);
        float y = offsetY + (ghost.cell.row * CELL_SIZE) + (CELL_SIZE / 2);
        float radius = CELL_SIZE / 2 - 2;

        switch (ghost.mode) {
            case CHASE:
            case SCATTER:
                shapeRenderer.setColor(ghost.color);
                break;
            case FRIGHTENED:
                shapeRenderer.setColor(Color.BLUE);
                break;
            case EATEN:
                shapeRenderer.setColor(new Color(1, 1, 0, 1));
                radius = CELL_SIZE / 4;
                break;
            default:
                shapeRenderer.setColor(new Color(ghost.color.r, ghost.color.g, ghost.color.b, 0.5f));
                break;
        }

        shapeRenderer.circle(x, y, radius);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
