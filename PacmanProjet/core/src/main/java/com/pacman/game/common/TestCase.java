package com.pacman.game.common;

import com.pacman.model.components.algorithms.BinaryTree;
import com.pacman.model.components.algorithms.MazeAlgorithms;
import com.pacman.model.components.algorithms.Sidewinder;
import com.pacman.model.components.maze.*;
import com.pacman.model.components.maze.character.Pacman;
import com.pacman.model.components.maze.character.PacmanController;

public class TestCase {
    public static void main(String[] args) {
        Position p1 = new Position(5, 10);
        Position p2 = new Position(5, 10);
        Position p3 = new Position(6, 10);

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p3 = " + p3);

        // Test equals
        System.out.println("p1.equals(p2) ? " + p1.equals(p2)); // true
        System.out.println("p1.equals(p3) ? " + p1.equals(p3)); // false

        // Test translate
        Position p4 = p1.translate(1, -2);
        System.out.println("p4 (p1 + (1,-2)) = " + p4); // Position(6, 8)

        // Test distances
        System.out.println("Manhattan(p1,p3) = " + p1.manhattanDistance(p3)); // 1
        System.out.println("Euclidean(p1,p3) = " + p1.euclideanDistance(p3)); // 1.0

        // HashCode contract (si equals -> même hash)
        System.out.println("hash p1 = " + p1.hashCode());
        System.out.println("hash p2 = " + p2.hashCode());

        if (p1.equals(p2) && p1.hashCode() == p2.hashCode()) {
            System.out.println("OK: equals / hashCode cohérents ✅");
        } else {
            System.out.println("❌ Problème avec equals/hashCode");
        }

        Grid grid = new Grid(12, 12);
        BinaryTree bt = new BinaryTree();
        bt.on(grid);

        // Cells distance test
        Cell start = grid.getCell(0, 0);
        Cell goal = grid.getCell(3, 3);

        Distances distances = start.distances();

        Distances path = distances.pathTo(goal);
        System.out.println("Distance from start to goal: " + distances.get(goal));
        System.out.println("Path from start to goal:");
        for (Cell cell : path.cells()) {
            System.out.println("Cell at (" + cell.row + ", " + cell.col + ")");
        }

        // MAze environment test
        MazeEnvironment maze = new MazeEnvironment(grid);


        // Inner walls
        maze.setTile(2, 3, TilePane.WALL);
        maze.setTile(3, 3, TilePane.WALL);
        maze.setTile(4, 3, TilePane.WALL);
        maze.setTile(5, 5, TilePane.WALL);
        maze.setTile(6, 5, TilePane.WALL);
        maze.setTile(7, 5, TilePane.WALL);

        // Set Pacman spawn
        maze.setPacmanSpawn(grid.getCell(1, 1));

        // Check if Pacman can move to left wall


        // Set Ghost spawns
        maze.addGhostSpawn(grid.getCell(2, 2));

        // Set Pellets
        // pellets
        for (int r = 1; r < 4; r++)
            for (int c = 1; c < 6; c++)
                if (maze.getTile(r, c) == TilePane.PATH)
                    maze.setTile(r, c, TilePane.PELLET);

        // power pellets
        maze.setTile(1, 6, TilePane.POWER_PELLET);
        System.out.println("Setting power pellet at " + "(1,6)");
        maze.setTile(4, 1, TilePane.POWER_PELLET);
        System.out.println("Setting power pellet at " + "(4,1)");

        // Set Fruit spawn
        maze.setTile(3, 3, TilePane.FRUIT_SPAWN);
        System.out.println("Setting fruit spawn at " + "(3,3)");






         // test if can move into wall

        Grid grid2 = new Grid(5, 5);
        MazeEnvironment maze2 = new MazeEnvironment(grid2);
        //Sidewinder.on(grid2);
        Cell center = grid2.getCell(2, 2);
        Cell right = grid2.getCell(2, 3);

        Cell left = grid2.getCell(2, 1);
        // check wall  between center and left
        System.out.println("Is there a wall between center and left? " + center.isLinked(left)); // should be true


        center.link(right); // create a passage between center and right

        Pacman newPacman = new Pacman(center, grid2);
        System.out.println("New Pacman trying to move LEFT into wall: " + newPacman.tryMove(Direction.LEFT)); // should be false

        System.out.println("New Pacman initial position: " + newPacman.getCurrentCell());
        System.out.println("New Pacman lives: " + newPacman.tryMove(Direction.RIGHT)); // should be true
        System.out.println("New Pacman position after moving RIGHT: " + newPacman.getCurrentCell());

        System.out.println("New Pacman trying to move LEFT into wall: " + newPacman.tryMove(Direction.LEFT)); // should be false
        System.out.println("New Pacman position after trying to move LEFT: " + newPacman.getCurrentCell());

        // Visualize maze
        System.out.println("Maze Visualization:");
        maze.printConsole();

        // New maze with Sidewinder
        Grid grid3 = new Grid(12, 12);
        Sidewinder sw = new Sidewinder();
        sw.on(grid3);
        MazeEnvironment maze3 = new MazeEnvironment(grid3);
        System.out.println("Maze generated with Sidewinder:");
        maze3.printConsole();



        Grid grid4 = new Grid(3, 3);
// carve a corridor: (1,0) <-> (1,1) <-> (1,2)
        Cell c10 = grid4.getCell(1,0);
        Cell c11 = grid4.getCell(1,1);
        Cell c12 = grid4.getCell(1,2);
        c10.link(c11);
        c11.link(c12);
        PacmanController alwaysRightController = new PacmanController() {
            @Override
            public Direction decideDirection(Pacman pacman, Grid grid) {
                return Direction.RIGHT;
            }
        };
        Pacman p = new Pacman(c10, grid4);
        p.setController(alwaysRightController);
//
//// first update -> from (1,0) to (1,1)
//        p.update(0.016f);
//        System.out.println(p.getCurrentCell()); // expect Cell(1,1)
//
//// second update -> from (1,1) to (1,2)
//        p.update(0.016f);
//        System.out.println(p.getCurrentCell()); // expect Cell(1,2)
//
//// third update -> can't move RIGHT (edge), so stays in (1,2)
//        p.update(0.016f);
//        System.out.println(p.getCurrentCell()); // still Cell(1,2)



    }
}

