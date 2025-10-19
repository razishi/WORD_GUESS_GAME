package com.example.demo.service;

import com.example.demo.model.WordEntry;
import com.example.demo.storage.WordStorage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that handles all word-related business logic.
 * Delegates storage operations to the WordStorage class.
 */
@Service
public class WordService {

    // Instance of storage handling the persistence layer
    private final WordStorage wordStorage = new WordStorage();

    /**
     * Retrieves all words stored in the system.
     *
     * @return a list of WordEntry objects
     */
    public List<WordEntry> getAllWords() {
        return wordStorage.getAllWords();
    }

    /**
     * Retrieves all unique categories from the stored words.
     *
     * @return a list of category names
     */
    public List<String> getAllCategories() {
        return wordStorage.getAllCategories();
    }

    /**
     * Retrieves a random word from the specified category.
     *
     * @param category the name of the category to fetch from
     * @return a random WordEntry from that category
     */
    public WordEntry getRandomWordByCategory(String category) {
        return wordStorage.getRandomWordByCategory(category);
    }

    /**
     * Adds a new word to the system after validating input fields.
     *
     * @param entry the WordEntry to add
     * @throws IllegalArgumentException if any field is missing or invalid
     */
    public void addWord(WordEntry entry) {
        if (entry.getCategory() == null || entry.getWord() == null || entry.getHint() == null) {
            throw new IllegalArgumentException("All fields must be filled.");
        }

        if (!entry.getCategory().matches("[a-zA-Z]+") || !entry.getWord().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Category and word must contain only a–z letters.");
        }

        wordStorage.addWord(entry);
    }

    /**
     * Deletes a word entry from the system.
     *
     * @param entry the WordEntry to be deleted
     */
    public void deleteWord(WordEntry entry) {
        wordStorage.deleteWord(entry);
    }
}
