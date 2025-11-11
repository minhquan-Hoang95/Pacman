package com.example.demo.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "mazes")
public class MazeEntity {

    @Id
    private String id;

    private int rows;
    private int cols;
    private String algorithm;
    private List<List<Map<String, Boolean>>> cells = new ArrayList<>();
    private Integer rating; // 0..5 ou null

    public MazeEntity() {
    }

    public MazeEntity(int rows, int cols, String algorithm, List<List<Map<String, Boolean>>> cells) {
        this.rows = rows;
        this.cols = cols;
        this.algorithm = algorithm;
        this.cells = cells;
        this.rating = null;
    }

    // GETTERS
    public String getId() { return id; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public String getAlgorithm() { return algorithm; }
    public List<List<Map<String, Boolean>>> getCells() { return cells; }
    public Integer getRating() { return rating; }

    // SETTERS
    public void setId(String id) { this.id = id; }
    public void setRows(int rows) { this.rows = rows; }
    public void setCols(int cols) { this.cols = cols; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public void setCells(List<List<Map<String, Boolean>>> cells) { this.cells = cells; }
    public void setRating(Integer rating) { this.rating = rating; }
}
