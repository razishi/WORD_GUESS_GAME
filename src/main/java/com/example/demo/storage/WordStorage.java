package com.example.demo.storage;

import com.example.demo.model.WordEntry;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.util.Constants.WORDS_FILE;

/**
 * Handles file-based storage and retrieval of word entries.
 * Supports adding, deleting, validating, and fetching words by category.
 */
public class WordStorage {

    // Path to the file where words are stored
    private static final String FILE_NAME = WORDS_FILE;
    // In-memory list of WordEntry objects loaded from file
    private List<WordEntry> words;

    /**
     * Constructor that loads word entries from disk on initialization.
     */
    public WordStorage() {
        this.words = loadWords();
    }

    /**
     * Loads and sanitizes word entries from the serialized file.
     * Filters out invalid entries and normalizes valid ones.
     *
     * @return a list of cleaned and validated WordEntry objects
     */
    private List<WordEntry> loadWords() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return ((List<WordEntry>) obj).stream()
                        .filter(w -> w != null &&
                                w.getCategory() != null &&
                                w.getWord() != null &&
                                w.getHint() != null &&
                                w.getCategory().matches("[a-zA-Z]+") &&
                                w.getWord().matches("[a-zA-Z]+"))
                        .map(w -> new WordEntry(
                                w.getCategory().trim().toLowerCase(),
                                w.getWord().trim().toLowerCase(),
                                w.getHint().trim()))
                        .distinct()
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("Error loading " + FILE_NAME + ": " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Saves the current list of word entries to the file.
     */
    private void saveWords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(words);
        } catch (IOException e) {
            System.err.println("Error saving " + FILE_NAME + ": " + e.getMessage());
        }
    }

    /**
     * Retrieves all stored word entries.
     *
     * @return list of WordEntry objects
     */
    public List<WordEntry> getAllWords() {
        return new ArrayList<>(words);
    }

    /**
     * Retrieves a distinct list of all word categories.
     *
     * @return list of category strings
     */
    public List<String> getAllCategories() {
        return words.stream()
                .map(WordEntry::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Fetches a random word from a specified category.
     *
     * @param category the category to search in
     * @return a random WordEntry or null if none exist
     */
    public WordEntry getRandomWordByCategory(String category) {
        List<WordEntry> filtered = words.stream()
                .filter(w -> w.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        if (filtered.isEmpty()) return null;
        return filtered.get(new Random().nextInt(filtered.size()));
    }

    /**
     * Adds a new word entry after checking for duplicates and normalizing input.
     *
     * @param entry the WordEntry to add
     * @throws IllegalArgumentException if a duplicate word exists in the category
     */
    public void addWord(WordEntry entry) {
        String newCategory = entry.getCategory().trim().toLowerCase();
        String newWord = entry.getWord().trim().toLowerCase();

        boolean exists = words.stream().anyMatch(w ->
                w.getCategory().equalsIgnoreCase(newCategory) &&
                        w.getWord().equalsIgnoreCase(newWord));

        if (exists) {
            throw new IllegalArgumentException("This word already exists in the selected category.");
        }

        entry.setCategory(newCategory);
        entry.setWord(newWord);
        entry.setHint(entry.getHint().trim());

        words.add(entry);
        saveWords();
    }

    /**
     * Deletes a word entry by matching category and word (case-insensitive).
     *
     * @param entry the WordEntry to delete
     */
    public void deleteWord(WordEntry entry) {
        words.removeIf(w ->
                w.getCategory().equalsIgnoreCase(entry.getCategory()) &&
                        w.getWord().equalsIgnoreCase(entry.getWord()));
        saveWords();
    }
}
