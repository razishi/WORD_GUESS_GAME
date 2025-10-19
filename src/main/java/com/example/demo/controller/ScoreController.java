package com.example.demo.controller;

import com.example.demo.model.ScoreEntry;
import com.example.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling score-related operations.
 * This controller provides endpoints to add scores and retrieve the leaderboard.
 */
@CrossOrigin(origins = "http://localhost:3000")// Allow requests from frontend (React)
@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    // Inject the ScoreService which handles business logic
    @Autowired
    private ScoreService scoreService;

    /**
     * Adds a new score entry to the leaderboard.
     *
     * @param entry the ScoreEntry object sent in the request body
     * @return ResponseEntity with status 200 OK
     */
    @PostMapping
    public ResponseEntity<Void> addScore(@RequestBody ScoreEntry entry) {
        scoreService.addScore(entry);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the current leaderboard as a list of score entries.
     *
     * @return List of ScoreEntry objects
     */
    @GetMapping
    public List<ScoreEntry> getLeaderboard() {
        return scoreService.getLeaderboard();
    }
}
