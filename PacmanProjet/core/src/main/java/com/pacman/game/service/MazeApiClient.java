package com.pacman.game.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.pacman.game.model.Cell;
import com.pacman.game.model.Maze;
import com.pacman.game.model.Direction;

/**
 * Service HTTP pour récupérer mazes depuis l'API REST du backend
 * Gère communication asynchrone + parsing JSON
 */
public class MazeApiClient {
    // URL de l'API backend (localhost:8080)
    private static final String API_URL = "http://localhost:8080/api/maze/random";

    /**
     * Callback pour résultat asynchrone
     * LibGDX appelle onSuccess ou onError selon résultat
     */
    public interface MazeCallback {
        void onSuccess(Maze maze);   // Appeler quand maze chargé
        void onError(String error);  // Appeler si erreur
    }

    /**
     * Récupérer un maze aléatoire depuis l'API
     * ASYNCHRONE = retour immédiat, réponse via callback
     */
    public void fetchMaze(MazeCallback callback) {
        // Créer requête HTTP GET
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(API_URL);

        // Envoyer et attendre réponse
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            // Appelé quand réponse reçue avec succès
            @Override
            public void handleHttpResponse(Net.HttpResponse response) {
                try {
                    // Récupérer le JSON string
                    String jsonString = response.getResultAsString();
                    // Parser le JSON en objet Maze
                    Maze maze = parseMaze(jsonString);
                    // Retourner via callback (succès)
                    callback.onSuccess(maze);
                    Gdx.app.log("MazeApiClient", "✓ Maze loaded successfully");
                } catch (Exception e) {
                    // Erreur parsing
                    callback.onError("Parse error: " + e.getMessage());
                    Gdx.app.error("MazeApiClient", "Parse error: " + e.getMessage());
                }
            }

            // Appelé si erreur réseau HTTP
            @Override
            public void failed(Throwable t) {
                callback.onError("HTTP error: " + t.getMessage());
                Gdx.app.error("MazeApiClient", "HTTP failed: " + t.getMessage());
            }

            // Appelé si requête annulée
            @Override
            public void cancelled() {
                callback.onError("Request cancelled");
                Gdx.app.log("MazeApiClient", "Request cancelled");
            }
        });
    }

    /**
     * Parser le JSON reçu et construire objet Maze avec Cell[][]
     *
     * Étapes:
     * 1. Parser JSON: récupérer id, rows, cols, cells array
     * 2. Créer Cell[][] vide
     * 3. Remplir chaque Cell
     * 4. Configurer voisins + liens selon JSON (walls)
     */
    private Maze parseMaze(String jsonString) {
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(jsonString);  // Parser JSON string

        // Récupérer données de haut niveau du JSON
        String id = root.getString("id");
        int rows = root.getInt("rows");
        int cols = root.getInt("cols");

        Gdx.app.log("MazeApiClient", "Parsing maze: " + id + " (" + rows + "x" + cols + ")");

        // ÉTAPE 1: Créer toutes les Cell (juste row/col d'abord)
        Cell[][] cells = new Cell[rows][cols];
        JsonValue cellsArray = root.get("cells");

        // Remplir grille de Cell vides
        for (int r = 0; r < rows; r++) {
            JsonValue rowArray = cellsArray.get(r);
            for (int c = 0; c < cols; c++) {
                cells[r][c] = new Cell(r, c);  // Créer Cell à position (r, c)
            }
        }

        // ÉTAPE 2: Configurer voisins et liens selon le JSON
        for (int r = 0; r < rows; r++) {
            JsonValue rowArray = cellsArray.get(r);
            for (int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                JsonValue cellObj = rowArray.get(c);


                // NORD: vérifier s'il existe une cell au nord ET pas de mur
                if (r > 0 && !cellObj.getBoolean("north")) {
                    Cell north = cells[r - 1][c];  // Cell une ligne au-dessus
                    cell.north = north;  // Définir voisin nord
                    cell.link(north);  // Créer lien (false = pas de reciproque ici)
                }

                // SUD: vérifier s'il existe une cell au sud ET pas de mur
                if (r < rows - 1 && !cellObj.getBoolean("south")) {
                    Cell south = cells[r + 1][c];  // Cell une ligne en-dessous
                    cell.south = south;  // Définir voisin sud
                    cell.link(south);  // Créer lien
                }
                // EST: vérifier s'il existe une cell à l'est ET pas de mur
                if (c < cols - 1 && !cellObj.getBoolean("east")) {
                    Cell east = cells[r][c + 1];  // Cell une colonne à droite
                    cell.east = east;  // Définir voisin est
                    cell.link(east);  // Créer lien
                }

                // OUEST: vérifier s'il existe une cell à l'ouest ET pas de mur
                if (c > 0 && !cellObj.getBoolean("west")) {
                    Cell west = cells[r][c - 1];  // Cell une colonne à gauche
                    cell.west = west;  // Définir voisin ouest
                    cell.link(west);  // Créer lien
                }

                // Déterminer si walkable
//                boolean hasPassage =
//                    !cellObj.getBoolean("north") ||
//                        !cellObj.getBoolean("south") ||
//                        !cellObj.getBoolean("east") ||
//                        !cellObj.getBoolean("west");
//
//                cell.isWalkable = hasPassage;

                // Après avoir défini isWalkable:
//                if (r == 0 && c == 0) {
//                    Gdx.app.log("DEBUG", "Cell[0,0] isWalkable=" + cell.isWalkable);
//                }
            }

        }

        // Créer objet Maze final avec grille complètement configurée
        Maze maze = new Maze(id, rows, cols, cells);
        Gdx.app.log("MazeApiClient", "✓ Maze parsed: " + maze.getInfo());


        System.out.println("\n🔍 SAMPLE CELL:");
        JsonValue sample = cellsArray.get(1).get(1);
        System.out.println("Cell[1,1] - north: " + sample.getBoolean("north"));
        System.out.println("Cell[1,1] - south: " + sample.getBoolean("south"));
        System.out.println("Cell[1,1] - east: " + sample.getBoolean("east"));
        System.out.println("Cell[1,1] - west: " + sample.getBoolean("west"));


        return maze;
    }
}
