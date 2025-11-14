package com.pacman.game.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pacman.game.model.GameState;
import com.pacman.game.model.Cell;

public class PelletRenderer {
    private static final int CELL_SIZE = 32;
    private static final Color PELLET_COLOR = Color.WHITE;
    private static final float PELLET_RADIUS = 2.0f;
    private ShapeRenderer shapeRenderer;

    public PelletRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(GameState gameState, float offsetX, float offsetY) {
        if (gameState == null || gameState.pelletsRemaining == null) return;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(PELLET_COLOR);

        for (Cell cell : gameState.pelletsRemaining) {
            if (cell == null) continue;

            // ✅ CENTER PELLET IN CELL (add offset + half cell size)
            float x = cell.col * CELL_SIZE + offsetX + CELL_SIZE / 2.0f;
            float y = cell.row * CELL_SIZE + offsetY + CELL_SIZE / 2.0f;

            shapeRenderer.circle(x, y, PELLET_RADIUS);
        }

        shapeRenderer.end();
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
