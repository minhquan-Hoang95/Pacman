package com.example.demo.controller;

import com.example.demo.models.components.algorithms.BinaryTree;
import com.example.demo.models.components.algorithms.RecursiveBacktracker;
import com.example.demo.models.components.algorithms.Sidewinder;
import com.example.demo.models.components.algorithms.TruePrims;
import com.example.demo.models.components.maze.Cell;
import com.example.demo.models.components.maze.Grid;
import com.example.demo.models.entities.MazeEntity;
import com.example.demo.repository.MazeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller for Maze generation.
 *
 * Provides an endpoint to generate maze structures dynamically.
 * Example request:
 *   GET /api/maze?rows=20&cols=20&algo=rb
 *
 * Returns:
 *   JSON structure representing the maze grid and walls.
 */
@RestController
@RequestMapping("/api") // Base URL prefix
public class MazeController {

    private final MazeRepository mazeRepository;

    public MazeController(MazeRepository mazeRepository) {
        this.mazeRepository = mazeRepository;
    }

    /**
     * Generate a maze based on given parameters.
     *
     * @param rows  number of rows in the maze
     * @param cols  number of columns in the maze
     * @param algo  algorithm to use ("bt" = Binary Tree, "rb" = Recursive Backtracker)
     * @return      ResponseEntity containing the maze data or an error message
     */
    @GetMapping("/maze")
    public ResponseEntity<?> getMaze(
            @RequestParam(defaultValue = "10") int rows,
            @RequestParam(defaultValue = "10") int cols,
            @RequestParam(defaultValue = "rb") String algo) {

        // ✅ Validation
        if (rows <= 0 || cols <= 0) {
            Map<String, Object> error = Map.of(
                    "error", "Invalid parameters",
                    "message", "rows and cols must be positive integers",
                    "status", HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(error);
        }

        // ✅ Generate maze
        Grid grid = new Grid(rows, cols);
        switch (algo.toLowerCase()) {
            case "bt" -> BinaryTree.on(grid);
            case "rb" -> RecursiveBacktracker.on(grid);
            case "tprims" -> TruePrims.on(grid);
            case "swinder" -> Sidewinder.on(grid);
            default -> {
                Map<String, Object> error = Map.of(
                        "error", "Unknown algorithm",
                        "message", "Supported: 'bt', 'rb'",
                        "status", HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        }



        // ... dans getMaze()

// ✅ Serialize maze / Build cells structure
        List<List<Map<String, Boolean>>> cells = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            List<Map<String, Boolean>> rowList = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                Cell cell = grid.getCell(r, c);
                Map<String, Boolean> cellMap = Map.of(
                    "north", !cell.isLinked(cell.north),
                    "east",  !cell.isLinked(cell.east),
                    "south", !cell.isLinked(cell.south),
                    "west",  !cell.isLinked(cell.west)
                );
                rowList.add(cellMap);
            }
            cells.add(rowList);
        }

// ✅ Créer l'entité pour Mongo
        MazeEntity entity = new MazeEntity(rows, cols, algo, cells);

// ✅ Sauvegarder
        mazeRepository.save(entity);

// ✅ Construire la réponse JSON
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", entity.getId());     // 👈 ident renvoyé au client
        response.put("rows", rows);
        response.put("cols", cols);
        response.put("cells", cells);
        response.put("meta", Map.of(
            "algorithm", algo,
            "timestamp", new Date().toString(),
            "author", "Pacman Project - Groupe K"
        ));

        return ResponseEntity.ok(response);

    }

    /**
     * Simple health check endpoint.
     * Example: GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Maze Generator API",
                "version", "1.0.0"

        ));
    }
    /**
     * Récupérer la liste de tous les mazes générés (sans les cellules)
     * Exemple :
     *  GET /api/maze/all
     */

    @GetMapping("/maze/all")
    public ResponseEntity<List<Map<String, Object>>> getAllMazes() {
        List<MazeEntity> mazes = mazeRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (MazeEntity maze : mazes) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", maze.getId());
            m.put("rows", maze.getRows());
            m.put("cols", maze.getCols());
            m.put("algorithm", maze.getAlgorithm());
            m.put("rating", maze.getRating());
            result.add(m);
        }

        return ResponseEntity.ok(result);
    }
    /**
     * Noter un maze : 0 (bad) ... 5 (good)
     * Exemple :
     *  PATCH /api/maze/654b3e.../rating?score=4
     */
    @PatchMapping("/maze/{id}/rating")
    public ResponseEntity<?> rateMaze(
        @PathVariable String id,
        @RequestParam("score") Integer rating) {

        if (rating == null || rating < 0 || rating > 5) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid rating",
                "message", "score must be between 0 and 5"
            ));
        }

        return mazeRepository.findById(id)
            .map(maze -> {
                maze.setRating(rating);
                mazeRepository.save(maze);
                return ResponseEntity.ok(Map.of(
                    "id", id,
                    "rating", rating
                ));
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Maze not found", "id", id)
            ));
    }

    /**
     * GET /api/maze/{id}
     * Récupère un maze spécifique par son ID
     */
    @GetMapping("/maze/{id}")
    public ResponseEntity<Map<String, Object>> getMazeById(@PathVariable String id) {
        return mazeRepository.findById(id)
            .map(this::getMapResponseEntity)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Maze not found", "id", id)));
    }

    /**
     * GET /api/maze/random?minRating=X
     * Récupère un maze aléatoire avec rating minimum
     */
    @GetMapping("/maze/random")
    public ResponseEntity<Map<String, Object>> getRandomMaze(
        @RequestParam(defaultValue = "0") Integer minRating) {

        List<MazeEntity> candidates = mazeRepository.findByRatingGreaterThanEqual(minRating);

        if (candidates.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "No maze found with rating >= " + minRating,
                    "suggestion", "Generate new mazes or lower minRating")
            );
        }

        // Choisir un maze aléatoire
        Random rand = new Random();
        MazeEntity maze = candidates.get(rand.nextInt(candidates.size()));

        return getMapResponseEntity(maze);
    }

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(MazeEntity maze) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", maze.getId());
        response.put("rows", maze.getRows());
        response.put("cols", maze.getCols());
        response.put("cells", maze.getCells());
        response.put("algorithm", maze.getAlgorithm());
        response.put("rating", maze.getRating());

        Map<String, String> meta = new LinkedHashMap<>();
        meta.put("algorithm", maze.getAlgorithm());
        response.put("meta", meta);

        return ResponseEntity.ok(response);
    }


}

