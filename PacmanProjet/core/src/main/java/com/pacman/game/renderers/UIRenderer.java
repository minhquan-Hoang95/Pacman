package com.pacman.game.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pacman.game.model.GameState;

/**
 * Renders UI: Score, Lives, Game Over messages
 */
public class UIRenderer {
    private BitmapFont font;
    private SpriteBatch batch;

    public UIRenderer() {
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    /**
     * Render score, lives, and game state messages
     */
    public void render(GameState gameState, float screenWidth, float screenHeight) {
        batch.begin();
        font.setColor(Color.WHITE);

        // Score (top-left)
        font.draw(batch, "Score: " + gameState.score, 20, screenHeight - 20);

        // Lives (top-right)
        font.draw(batch, "Lives: " + gameState.lives, screenWidth - 150, screenHeight - 20);

        // Pellets remaining (bottom-left)
        font.draw(batch, "Pellets: " + gameState.getPelletsRemaining(), 20, 30);

        // Game Over / Win messages (center)
        if (gameState.gameOver) {
            if (gameState.won) {
                font.setColor(Color.GREEN);
                font.draw(batch, "YOU WIN!", screenWidth / 2 - 50, screenHeight / 2);
            } else {
                font.setColor(Color.RED);
                font.draw(batch, "GAME OVER!", screenWidth / 2 - 80, screenHeight / 2);
            }
        }

        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
    }

    /**
     * Render loading screen while maze loads from API
     */
    public void renderLoadingScreen(String message, float screenWidth, float screenHeight) {
        batch.begin();
        font.setColor(Color.WHITE);

        float x = screenWidth / 2 - 100;
        float y = screenHeight / 2;

        font.draw(batch, message, x, y);

        batch.end();
    }

}
