package com.example.demo.repository;

import com.example.demo.models.entities.MazeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository interface for MazeEntity.
 * Extends MongoRepository to provide CRUD operations and custom queries.
 */
public interface MazeRepository extends MongoRepository<MazeEntity, String> {
    // Custom query method to find mazes with rating greater than or equal to a specified value
    List<MazeEntity> findByRatingGreaterThanEqual(Integer rating);
}
