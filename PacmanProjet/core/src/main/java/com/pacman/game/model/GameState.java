package com.pacman.game.model;

import com.pacman.game.model.ghost.*;
import java.util.*;

public class GameState {
    public Pacman pacman;
    public Maze maze;
    public List<Ghost> ghosts;
    public int score;
    public int lives;
    public boolean gameOver;
    public boolean won;

    public Set<Cell> pelletsRemaining;
    public int level;

    public GameState(Maze maze) {
        this.maze = maze;
        this.score = 0;
        this.lives = 3;
        this.level = 1;
        this.gameOver = false;
        this.won = false;
        this.ghosts = new ArrayList<>();

        Cell startCell = maze.getCellAt(26, 1);
        this.pacman = new Pacman(startCell);

        this.pelletsRemaining = new HashSet<>();
        spawnPellets();
    }

    private void spawnPellets() {
        for (int r = 0; r < maze.rows; r++) {
            for (int c = 0; c < maze.cols; c++) {
                Cell cell = maze.getCellAt(r, c);
                if (cell == null) continue;

                // ✅ ONLY add pellets to WALKABLE cells (not Pacman start)
                if (!(r == 26 && c == 1)) {  // Not Pacman start
                    pelletsRemaining.add(cell);
                }
            }
        }
    }

    public boolean hasPelletAt(Cell cell) {
        return cell != null && pelletsRemaining.contains(cell);
    }

    public int eatPelletAt(Cell cell) {
        if (hasPelletAt(cell)) {
            pelletsRemaining.remove(cell);
            this.score += 10;
            return 10;
        }
        return 0;
    }

    public boolean isGameWon() {
        return pelletsRemaining.isEmpty();
    }

    public void update() {
        if (gameOver || won || pacman == null) return;

        pacman.update(maze);

        if (hasPelletAt(pacman.currentCell)) {
            eatPelletAt(pacman.currentCell);
        }
    }

    @Override
    public String toString() {
        return String.format("Score: %d | Lives: %d | Pacman: %s",
            score, lives, pacman);
    }

    public String getPelletsRemaining() {
        return "Pellets Remaining: " + pelletsRemaining.size();
    }
}
