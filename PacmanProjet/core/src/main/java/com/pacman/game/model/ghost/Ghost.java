package com.pacman.game.model.ghost;

import com.badlogic.gdx.graphics.Color;
import com.pacman.game.model.Cell;
import com.pacman.game.model.Direction;
import com.pacman.game.model.Maze;
import com.pacman.game.model.Pacman;

import java.util.List;

/**
 * ✅ ABSTRACT GHOST BASE CLASS
 * All ghosts inherit from this
 */
public abstract class Ghost {
    // ✅ SPEED MODES
    public enum SpeedMode {
        SLOW(3),      // Moves every 3 frames
        NORMAL(1),    // Moves every frame
        FAST(0.5f);   // Moves twice per frame (advanced)

        public float frameskip;

        SpeedMode(float frameskip) {
            this.frameskip = frameskip;
        }
    }

    public SpeedMode speedMode = SpeedMode.NORMAL;

    /**
     * ✅ Change speed mode
     */
    public void setSpeedMode(SpeedMode mode) {
        this.speedMode = mode;
        this.speed = (int) mode.frameskip;
    }
    // ✅ ADD SPEED
    public int speed = 1;  // 1 = normal, 2 = double speed, 0.5 = half speed
    public int moveCounter = 0;  // Counter to skip frames

    // ═══════════════════════════════════════════════════════
    // IDENTITY
    // ═══════════════════════════════════════════════════════
    public final String name;
    public final Color color;
    public final int id;

    // ═══════════════════════════════════════════════════════
    // POSITION & MOVEMENT
    // ═══════════════════════════════════════════════════════
    public Cell cell;
    public Direction direction = Direction.UP;
    public Direction nextDirection = Direction.UP;

    // ═══════════════════════════════════════════════════════
    // STATE MANAGEMENT
    // ═══════════════════════════════════════════════════════
    public enum GhostMode {
        CHASE, SCATTER, FRIGHTENED, EATEN, WAITING
    }

    public GhostMode mode = GhostMode.CHASE;
    public int modeTimer = 0;
    public boolean isAlive = true;
    public int respawnTimer = 0;
    public static final int RESPAWN_TIME = 300;
    public static final int MODE_DURATION = 400;

    // ═══════════════════════════════════════════════════════
    // AI TARGETS
    // ═══════════════════════════════════════════════════════
    public Cell targetCell;
    public Cell scatterCorner;

    /**
     * ✅ Constructor
     */
    public Ghost(String name, Color color, int id, Cell startCell, Cell corner) {
        this.name = name;
        this.color = color;
        this.id = id;
        this.cell = startCell;
        this.scatterCorner = corner;
        this.targetCell = startCell;
    }

    // ✅ Set speed
    public void setSpeed(int speedLevel) {
        this.speed = speedLevel;
    }

    /**
     * ✅ Should ghost move this frame?
     */
    public boolean shouldMove() {
        moveCounter++;
        if (moveCounter >= speed) {
            moveCounter = 0;
            return true;  // Move!
        }
        return false;  // Skip this frame
    }

    // ═══════════════════════════════════════════════════════
    // ABSTRACT METHODS - Each ghost must implement
    // ═══════════════════════════════════════════════════════

    /**
     * ✅ Chase behavior (unique per ghost)
     */
    public abstract Cell getChaseBehavior(Pacman pacman, Maze maze);

    /**
     * ✅ Scatter behavior (unique per ghost)
     */
    public abstract Cell getScatterBehavior(Maze maze);

    // ═══════════════════════════════════════════════════════
    // COMMON METHODS - Shared by all ghosts
    // ═══════════════════════════════════════════════════════

    /**
     * ✅ Get target based on mode
     */
    public Cell getTarget(Pacman pacman, Maze maze) {
        switch (mode) {
            case CHASE:
                return getChaseBehavior(pacman, maze);
            case SCATTER:
                return getScatterBehavior(maze);
            case FRIGHTENED:
                return cell;  // Handled separately by AI
            case EATEN:
                return scatterCorner;  // Return to ghost house
            default:
                return cell;
        }
    }

    /**
     * ✅ Main update loop
     */
    public void update(Pacman pacman, Maze maze) {
        if (!isAlive) {
            updateRespawn();
            return;
        }

        updateModeTimer();
        // Movement handled in GameState
    }

    /**
     * ✅ Update mode timer (switch between CHASE and SCATTER)
     */
    public void updateModeTimer() {
        if (mode == GhostMode.FRIGHTENED || mode == GhostMode.EATEN) {
            return;  // Don't update timer in these modes
        }

        modeTimer++;

        if (modeTimer >= MODE_DURATION) {
            if (mode == GhostMode.CHASE) {
                setMode(GhostMode.SCATTER);
            } else if (mode == GhostMode.SCATTER) {
                setMode(GhostMode.CHASE);
            }
        }
    }

    /**
     * ✅ Set ghost mode
     */
    public void setMode(GhostMode newMode) {
        this.mode = newMode;
        this.modeTimer = 0;
    }

    /**
     * ✅ Move to new cell
     */
    public void moveTo(Cell newCell) {
        this.cell = newCell;
    }

    /**
     * ✅ Ghost eaten - send back to ghost house
     */
    public void kill(Cell ghostHouse) {
        this.isAlive = false;
        this.mode = GhostMode.EATEN;
        this.respawnTimer = 0;
        this.cell = ghostHouse;
    }

    /**
     * ✅ Update respawn timer
     */
    public void updateRespawn() {
        if (!isAlive) {
            respawnTimer++;

            if (respawnTimer >= RESPAWN_TIME) {
                isAlive = true;
                mode = GhostMode.WAITING;
                System.out.println("✨ " + name + " respawned!");
            }
        }
    }

    /**
     * ✅ Activate frightened mode
     */
    public void frighten(int duration) {
        if (isAlive && mode != GhostMode.EATEN) {
            this.mode = GhostMode.FRIGHTENED;
            this.modeTimer = 0;
        }
    }

    /**
     * ✅ Set custom target (for team coordination)
     */
    public void setTarget(Cell target) {
        this.targetCell = target;
    }

    @Override
    public String toString() {
        return name + " at (" + cell.row + ", " + cell.col + ") [" + mode + "]";
    }

    // ✅ ADD THESE
    public boolean isInGhostHouse(GhostHouse house) {
        return house.isInside(this.cell);
    }

    public void leaveGhostHouse(GhostHouse house) {
        if (isInGhostHouse(house)) {
            house.removeGhost(this);
            this.mode = GhostMode.CHASE;
        }
    }

    public void returnToGhostHouse(GhostHouse house) {
        house.addGhost(this);
        this.mode = GhostMode.EATEN;
    }
}

