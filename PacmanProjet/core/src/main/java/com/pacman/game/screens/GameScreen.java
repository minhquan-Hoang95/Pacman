package com.pacman.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

import com.pacman.game.model.Cell;
import com.pacman.game.model.Maze;
import com.pacman.game.model.GameState;
import com.pacman.game.model.Direction;
import com.pacman.game.model.ghost.Ghost;
import com.pacman.game.renderers.*;
import com.pacman.game.service.MazeApiClient;

/**
 * ✅ COMPLETE FINAL VERSION
 * - Fixed cell size (32px)
 * - Custom padding (top, bottom, left, right)
 * - 4 Ghosts with AI
 * - Responsive to window resize
 * - API-driven levels
 */
public class GameScreen implements Screen {

//    private static final int CELL_SIZE = 32;
//    private static final int PADDING_TOP = 80;
//    private static final int PADDING_LEFT = 32;
//
//    private Maze maze;
//    private GameState gameState;
//    private int currentLevel = 1;
//
//    private MazeRenderer mazeRenderer;
//    private PacmanRenderer pacmanRenderer;
//    private PelletRenderer pelletRenderer;  // ✅ ADD THIS
//    private OrthographicCamera camera;
//
//    private MazeApiClient apiClient;
//    private boolean mazeLoaded = false;
//
//    public GameScreen() {
//        this.mazeRenderer = new MazeRenderer();
//        this.pacmanRenderer = new PacmanRenderer();
//        this.pelletRenderer = new PelletRenderer();  // ✅ ADD THIS
//        this.apiClient = new MazeApiClient();
//        loadMazeFromAPI();
//    }
//
//    private void loadMazeFromAPI() {
//        apiClient.fetchMaze(new MazeApiClient.MazeCallback() {
//            @Override
//            public void onSuccess(Maze loadedMaze) {
//                maze = loadedMaze;
//                gameState = new GameState(maze);
//                setupCamera();
//                mazeLoaded = true;
//                System.out.println("✅ Level " + currentLevel + " loaded!");
//            }
//
//            @Override
//            public void onError(String error) {
//                System.err.println("❌ Load failed: " + error);
//                mazeLoaded = false;
//            }
//        });
//    }
//
//    private void setupCamera() {
//        if (maze == null) return;
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
//        camera.update();
//    }
//
//    @Override
//    public void show() {}
//
//    @Override
//    public void render(float delta) {
//        if (!mazeLoaded || maze == null || gameState == null) {
//            Gdx.gl.glClearColor(0, 0, 0, 1);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//            return;
//        }
//
//        handleInput();
//
//        if (!gameState.gameOver && !gameState.won) {
//            gameState.update();
//            checkGhostCollision();
//        }
//
//        checkGameState();
//
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        camera.update();
//
//        float offsetX = PADDING_LEFT;
//        float offsetY = PADDING_TOP;
//
//        mazeRenderer.render(maze);
//        pelletRenderer.render(gameState, offsetX, offsetY);  // ✅ ADD THIS
//        pacmanRenderer.render(gameState.pacman, PADDING_LEFT, PADDING_TOP);
//    }
//
//    private void handleInput() {
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            gameState.pacman.setNextDirection(Direction.UP);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            gameState.pacman.setNextDirection(Direction.DOWN);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            gameState.pacman.setNextDirection(Direction.LEFT);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            gameState.pacman.setNextDirection(Direction.RIGHT);
//        }
//
//        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
//            restartLevel();
//        }
//
//        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
//            nextLevel();
//        }
//    }
//
//    private void checkGhostCollision() {
//        if (gameState.pacman.currentCell == null) return;
//
//        for (Ghost ghost : gameState.ghosts) {
//            if (gameState.pacman.currentCell == ghost.cell) {
//                gameState.lives--;
//                System.out.println("💥 Caught by ghost! Lives: " + gameState.lives);
//                gameState.pacman.currentCell = maze.getCellAt(26, 1);
//            }
//        }
//    }
//
//    private void checkGameState() {
//        if (gameState.isGameWon()) {
//            gameState.won = true;
//            gameState.gameOver = true;
//            System.out.println("🎉 LEVEL " + currentLevel + " WON!");
//            System.out.println("Score: " + gameState.score);
//            System.out.println("Press N for next level");
//            return;
//        }
//
//        if (gameState.lives <= 0) {
//            gameState.gameOver = true;
//            System.out.println("💀 GAME OVER!");
//            System.out.println("Final Score: " + gameState.score);
//            System.out.println("Press R to restart");
//        }
//    }
//
//    private void restartLevel() {
//        System.out.println("🔄 Restarting level " + currentLevel);
//        gameState.gameOver = false;
//        gameState.won = false;
//        loadMazeFromAPI();
//    }
//
//    private void nextLevel() {
//        currentLevel++;
//        System.out.println("📍 Loading level " + currentLevel);
//        gameState.gameOver = false;
//        gameState.won = false;
//        loadMazeFromAPI();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        if (camera != null) {
//            camera.viewportWidth = width;
//            camera.viewportHeight = height;
//            camera.update();
//        }
//    }
//
//    @Override
//    public void pause() {}
//    @Override
//    public void resume() {}
//    @Override
//    public void hide() {}
//
//    @Override
//    public void dispose() {
//        if (mazeRenderer != null) mazeRenderer.dispose();
//        if (pacmanRenderer != null) pacmanRenderer.dispose();
//    }


    // ✅ CONSTANTS
    private static final int CELL_SIZE = 32;
    private static final int MAZE_ROWS = 31;
    private static final int MAZE_COLS = 28;

    // ✅ INDIVIDUAL PADDING FOR EACH SIDE
    private static final int PADDING_TOP = 100;
    private static final int PADDING_BOTTOM = 100;
    private static final int PADDING_LEFT = 80;
    private static final int PADDING_RIGHT = 80;

    // ✅ GAME OBJECTS
    private Maze maze;
    private GameState gameState;
    private int currentLevel = 1;
    private int maxLevels = 5;

    // ✅ API CLIENT
    private MazeApiClient mazeApiClient;

    // ✅ RENDERERS
    private MazeRenderer mazeRenderer;
    private PacmanRenderer pacmanRenderer;
    private PelletRenderer pelletRenderer;
    private UIRenderer uiRenderer;
    //private GhostRenderer ghostRenderer;

    // ✅ CAMERA & OFFSET
    private OrthographicCamera camera;
    private float offsetX;
    private float offsetY;

    // ✅ STATE
    private boolean mazeLoading = false;
    private String loadingMessage = "Loading level...";

    /**
     * ✅ INITIALIZE SCREEN
     */
    @Override
    public void show() {
//        System.out.println("🎮 ==========================================");
//        System.out.println("🎮 PACMAN GAME - COMPLETE VERSION");
//        System.out.println("🎮 ==========================================\n");

        // ✅ Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // ✅ Initialize API client
        mazeApiClient = new MazeApiClient();

        // ✅ Initialize renderers
        mazeRenderer = new MazeRenderer();
        pacmanRenderer = new PacmanRenderer();
        pelletRenderer = new PelletRenderer();
        uiRenderer = new UIRenderer();
        //ghostRenderer = new GhostRenderer();

        Matrix4 projection = camera.combined;
        mazeRenderer.setProjectionMatrix(projection);

        // ✅ Calculate offset and load level
        updateMazeOffset(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        loadLevel(1);
    }

    /**
     * ✅ UPDATE MAZE OFFSET WITH INDIVIDUAL PADDING
     */
    private void updateMazeOffset(int screenWidth, int screenHeight) {
        int mazeWidth = MAZE_COLS * CELL_SIZE;
        int mazeHeight = MAZE_ROWS * CELL_SIZE;

        int effectiveWidth = mazeWidth + PADDING_LEFT + PADDING_RIGHT;
        int effectiveHeight = mazeHeight + PADDING_TOP + PADDING_BOTTOM;

        offsetX = (screenWidth - effectiveWidth) / 2.0f + PADDING_LEFT;
        offsetY = (screenHeight - effectiveHeight) / 2.0f + PADDING_TOP;

        System.out.println("🎯 Maze offset: (" + offsetX + ", " + offsetY + ")");
    }

    /**
     * ✅ LOAD A LEVEL - ASYNC API
     */
    private void loadLevel(int levelNum) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📍 LOADING LEVEL " + levelNum);
        System.out.println("=".repeat(50));

        currentLevel = levelNum;
        mazeLoading = true;
        loadingMessage = "Loading level " + levelNum + "...";

        mazeApiClient.fetchMaze(new MazeApiClient.MazeCallback() {
            @Override
            public void onSuccess(Maze loadedMaze) {
                maze = loadedMaze;
                gameState = new GameState(maze);
                gameState.level = levelNum;

//                System.out.println("✅ Maze loaded!");
//                System.out.println("🍒 Pellets: " + gameState.getPelletsRemaining());
//                System.out.println("👻 Ghosts: " + gameState.ghosts.size() + "\n");

                mazeLoading = false;
            }

            @Override
            public void onError(String error) {
                System.err.println("❌ ERROR: " + error);
                loadingMessage = "Error: " + error;
                maze = generateFallbackMaze();
                gameState = new GameState(maze);
                gameState.level = levelNum;
                mazeLoading = false;
            }
        });
    }

    /**
     * ✅ FALLBACK MAZE
     */
    private Maze generateFallbackMaze() {
        Cell[][] cells = new Cell[MAZE_ROWS][MAZE_COLS];

        for (int r = 0; r < MAZE_ROWS; r++) {
            for (int c = 0; c < MAZE_COLS; c++) {
                cells[r][c] = new Cell(r, c);
            }
        }

        for (int r = 0; r < MAZE_ROWS; r++) {
            for (int c = 0; c < MAZE_COLS; c++) {
                Cell cell = cells[r][c];
                if (r > 0) {
                    cell.north = cells[r-1][c];
                    cell.link(cell.north, false);
                }
                if (c > 0) {
                    cell.west = cells[r][c-1];
                    cell.link(cell.west, false);
                }
            }
        }

        return new Maze("fallback", MAZE_ROWS, MAZE_COLS, cells);
    }

    /**
     * ✅ MAIN GAME LOOP
     */
    @Override
    public void render(float delta) {
        // Check if still loading
        if (mazeLoading || gameState == null) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
            uiRenderer.renderLoadingScreen(loadingMessage, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            return;
        }

        // ========== PHASE 1: INPUT ==========
        handleInput();

        //

        // ========== PHASE 2: UPDATE ==========
        if (!gameState.gameOver) {
            gameState.update();
        }

        // ========== PHASE 3: CHECK WIN/LOSE ==========
        checkGameOver();

        // ========== PHASE 4: CLEAR SCREEN ==========
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);



        // ========== PHASE 5: RENDER (Back to Front) ==========
        mazeRenderer.render(maze);
        //ghostRenderer.renderGhostHouse(gameState.ghostHouse, offsetX, offsetY);
        pelletRenderer.render(gameState, offsetX, offsetY);
        //ghostRenderer.render(gameState.ghosts, gameState.ghostHouse, offsetX, offsetY);  // ✅ GHOSTS
        pacmanRenderer.render(gameState.pacman, offsetX, offsetY);
        uiRenderer.render(gameState, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * ✅ HANDLE KEYBOARD INPUT
     */
    private void handleInput() {
        // Movement
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            gameState.pacman.setNextDirection(Direction.UP);
            gameState.pacman.update(maze); // move once!
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            gameState.pacman.setNextDirection(Direction.DOWN);
            gameState.pacman.update(maze); // move once!
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            gameState.pacman.setNextDirection(Direction.LEFT);
            gameState.pacman.update(maze); // move once!
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            gameState.pacman.setNextDirection(Direction.RIGHT);
            gameState.pacman.update(maze); // move once!
        }

        // Controls
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            restartLevel();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            if (currentLevel < maxLevels) {
                loadLevel(currentLevel + 1);
            }
        }
    }

    /**
     * ✅ CHECK WIN/LOSE CONDITIONS
     */
    private void checkGameOver() {
        if (gameState.gameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                restartLevel();
            }
            return;
        }

         //CHECK GHOST COLLISION
        for (Ghost ghost : gameState.ghosts)
        {
            if (gameState.pacman.currentCell == ghost.cell) {
                if (ghost.mode == Ghost.GhostMode.FRIGHTENED) {
                    // Eat ghost!
                    gameState.score += 200;
                    ghost.moveTo(maze.getCellAt(15, 14));
                    System.out.println("😱 Ate " + ghost.name + "! +200 points!");
                } else {
                    // Caught!
                    gameState.lives--;
                    System.out.println("💥 Caught by " + ghost.name + "! Lives: " + gameState.lives);
                    gameState.pacman.currentCell = maze.getCenterCell();
                }
            }
        }

        // WIN CONDITION
        if (gameState.isGameWon()) {
            gameState.won = true;
            gameState.gameOver = true;
            System.out.println("\n🎉 LEVEL " + currentLevel + " WON!");
            System.out.println("Score: " + gameState.score);

            if (currentLevel < maxLevels) {
                System.out.println("Press N for next level");
            } else {
                System.out.println("🏆 ALL LEVELS COMPLETED!");
            }
            return;
        }

        // LOSE CONDITION
        if (gameState.lives <= 0) {
            gameState.gameOver = true;
            System.out.println("\n💀 GAME OVER!");
            System.out.println("Final Score: " + gameState.score);
        }
    }

    /**
     * ✅ RESTART LEVEL
     */
    private void restartLevel() {
        System.out.println("🔄 Restarting level " + currentLevel);
        loadLevel(currentLevel);
    }

    // ========== SCREEN LIFECYCLE ==========

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        updateMazeOffset(width, height);
    }

    @Override
    public void pause() {
        System.out.println("⏸️  PAUSED");
    }

    @Override
    public void resume() {
        System.out.println("▶️  RESUMED");
    }

    @Override
    public void hide() {
        System.out.println("🚪 Screen hidden");
    }

    @Override
    public void dispose() {
        System.out.println("🧹 Disposing...");
        if (mazeRenderer != null) mazeRenderer.dispose();
        if (pacmanRenderer != null) pacmanRenderer.dispose();
        if (pelletRenderer != null) pelletRenderer.dispose();
        if (uiRenderer != null) uiRenderer.dispose();
       // if (ghostRenderer != null) ghostRenderer.dispose();
        System.out.println("✅ Done!");
    }
}
