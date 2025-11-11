package com.pacman.model.components.maze.character;

import java.util.List;

/**
 * Represents the ghost house area in the maze where ghosts start and return to after being eaten.
 * To manage ghost spawning and respawning mechanics.
 */
public class GhostManager {
    private final List<Ghost> ghosts;
    private GhostMode currentMode = GhostMode.SCATTER;
    private long modeStartTime;

    // Mode durations (milliseconds)
    private static final long SCATTER_DURATION = 7000;
    private static final long CHASE_DURATION = 20000;

    public GhostManager(List<Ghost> ghosts) {
        this.ghosts = ghosts;
        this.modeStartTime = System.currentTimeMillis();
    }

    public void update(Pacman pacman)
    {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - modeStartTime;

        // Example mode switching logic based on time
        if (currentMode == GhostMode.SCATTER && elapsedTime > SCATTER_DURATION) {
            currentMode = GhostMode.CHASE;
            modeStartTime = currentTime;
        } else if (currentMode == GhostMode.CHASE && elapsedTime > CHASE_DURATION) {
            currentMode = GhostMode.SCATTER;
            modeStartTime = currentTime;
        }

        // Update each ghost's mode
        for (Ghost ghost : ghosts) {
            ghost.setGhostMode(currentMode);
            ghost.update(1f/60f); // Assuming 60 FPS for deltaTime
        }


    }
    public void triggerFrightenedMode() {
        for (Ghost ghost : ghosts) {
            ghost.setFrightened(true);
        }

    }
    public void setMode(GhostMode mode) {
        this.currentMode = mode;
        this.modeStartTime = System.currentTimeMillis();
        for (Ghost ghost : ghosts) {
            ghost.setGhostMode(mode);
        }
    }

    public GhostMode getCurrentMode() {
        return currentMode;
    }
}
