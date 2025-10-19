package com.example.demo.service;

import com.example.demo.model.ScoreEntry;
import com.example.demo.storage.ScoreStorage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling score-related logic.
 * Acts as a bridge between the controller and the storage layer.
 */
@Service
public class ScoreService {

    // ScoreStorage instance to handle actual data persistence and retrieval
    private final ScoreStorage scoreStorage = new ScoreStorage();

    /**
     * Adds a new score entry to the system.
     *
     * @param entry the score entry to be added
     */
    public void addScore(ScoreEntry entry) {
        scoreStorage.addScore(entry);
    }

    /**
     * Retrieves the top N scores to display in the leaderboard.
     *
     * @return a list of top ScoreEntry objects (default: top 10)
     */
    public List<ScoreEntry> getLeaderboard() {
        return scoreStorage.getTopScores(10);
    }
}
