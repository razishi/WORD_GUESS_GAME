package com.example.demo.controller;

import com.example.demo.model.WordEntry;
import com.example.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing word entries.
 * Provides endpoints to retrieve, add, and delete words, as well as fetch categories or random words.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/words")
public class WordController {

    @Autowired
    private WordService wordService;

    /**
     * Retrieves all word entries in the system.
     *
     * @return list of WordEntry objects
     */
    @GetMapping
    public List<WordEntry> getAllWords() {
        return wordService.getAllWords();
    }

    /**
     * Retrieves a list of all available word categories.
     *
     * @return list of category names
     */
    @GetMapping("/categories")
    public List<String> getCategories() {
        return wordService.getAllCategories();
    }

    /**
     * Retrieves a random word from a given category.
     *
     * @param category the category to choose a word from
     * @return a randomly selected WordEntry
     */
    @GetMapping("/random")
    public WordEntry getRandomByQueryParam(@RequestParam String category) {
        return wordService.getRandomWordByCategory(category);
    }

    /**
     * Adds a new word entry to the system.
     *
     * @param entry the WordEntry object to add
     * @return ResponseEntity with HTTP 200 status
     */
    @PostMapping
    public ResponseEntity<Void> addWord(@RequestBody WordEntry entry) {
        wordService.addWord(entry);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a word entry from the system.
     * This method uses a DELETE request with a JSON body (non-standard but supported).
     *
     * @param entry the WordEntry object to delete
     * @return ResponseEntity with HTTP 204 No Content status
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Void> deleteWord(@RequestBody WordEntry entry) {
        wordService.deleteWord(entry);
        return ResponseEntity.noContent().build();
    }
}
