package com.pacman.game.model.ghost;

import com.pacman.game.model.Cell;
import com.pacman.game.model.Direction;
import com.pacman.game.model.Maze;
import com.pacman.game.model.Pacman;
import java.util.List;

/**
 * ✅ GHOST TEAM - Coordinate attacks
 */
public class GhostTeam {

    private List<Ghost> ghosts;
    private Pacman pacman;
    private Maze maze;

    public GhostTeam(List<Ghost> ghosts, Pacman pacman, Maze maze) {
        this.ghosts = ghosts;
        this.pacman = pacman;
        this.maze = maze;
    }

    /**
     * ✅ Coordinated attack strategy
     */
    public void coordinatedAttack() {
        if (ghosts.size() < 2) return;

        // Find closest ghost to Pacman
        Ghost leader = findClosestGhost();

        for (Ghost ghost : ghosts) {
            if (ghost == leader) {
                // Leader: Direct chase
                ghost.setMode(Ghost.GhostMode.CHASE);
            } else {
                // Blockers: Intercept escape route
                Cell blockingPos = calculateBlockingPosition(ghost);
                ghost.setTarget(blockingPos);
            }
        }
    }

    /**
     * ✅ Find ghost closest to Pacman
     */
    private Ghost findClosestGhost() {
        Ghost closest = ghosts.get(0);
        int minDistance = Integer.MAX_VALUE;

        for (Ghost ghost : ghosts) {
            if (!ghost.isAlive) continue;

            int distance = maze.mazeDistance(ghost.cell, pacman.currentCell);
            if (distance < minDistance) {
                minDistance = distance;
                closest = ghost;
            }
        }

        return closest;
    }

    /**
     * ✅ Calculate blocking position
     */
    private Cell calculateBlockingPosition(Ghost ghost) {
        // Get Pacman's likely escape direction
        Direction escapeDir = getPacmanEscapeDirection();

        // Get cell 2-3 moves ahead in that direction
        Cell blockingCell = getPacmanCellInDirection(escapeDir, 3);

        return blockingCell != null ? blockingCell : ghost.cell;
    }

    /**
     * ✅ Predict Pacman's escape direction
     */
    private Direction getPacmanEscapeDirection() {
        Ghost closest = findClosestGhost();
        int maxDistance = Integer.MIN_VALUE;
        Direction escapeDir = Direction.UP;

        for (Direction dir : Direction.values()) {
            Cell nextCell = getNextCellInDirection(pacman.currentCell, dir);
            if (nextCell != null) {
                int distance = maze.mazeDistance(nextCell, closest.cell);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    escapeDir = dir;
                }
            }
        }

        return escapeDir;
    }

    /**
     * ✅ Get cell N steps ahead in direction
     */
    private Cell getPacmanCellInDirection(Direction dir, int steps) {
        Cell current = pacman.currentCell;

        for (int i = 0; i < steps; i++) {
            Cell next = getNextCellInDirection(current, dir);
            if (next != null) {
                current = next;
            } else {
                break;
            }
        }

        return current;
    }

    /**
     * ✅ Get next cell in given direction
     */
    private Cell getNextCellInDirection(Cell cell, Direction direction) {
        if (direction == null) return null;

        switch (direction) {
            case UP:
                return cell.row > 0 ? maze.getCellAt(cell.row - 1, cell.col) : null;
            case DOWN:
                return cell.row < 30 ? maze.getCellAt(cell.row + 1, cell.col) : null;
            case LEFT:
                return cell.col > 0 ? maze.getCellAt(cell.row, cell.col - 1) : null;
            case RIGHT:
                return cell.col < 27 ? maze.getCellAt(cell.row, cell.col + 1) : null;
            default:
                return null;
        }
    }
}

