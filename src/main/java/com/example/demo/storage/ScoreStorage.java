package com.example.demo.storage;

import com.example.demo.model.ScoreEntry;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.util.Constants.SCORES_FILE;

/**
 * Handles persistence and retrieval of player scores using file-based serialization.
 * Maintains a list of ScoreEntry objects and provides methods to add, retrieve, and clear scores.
 */
public class ScoreStorage {

    // The filename used for storing serialized score data
    private static final String FILE_NAME = SCORES_FILE;
    // Internal list of scores loaded from the file
    private List<ScoreEntry> scores;

    /**
     * Constructor that initializes and loads scores from disk.
     */
    public ScoreStorage() {
        this.scores = loadScores();
    }

    /**
     * Loads scores from the serialized file.
     * If the file doesn't exist or loading fails, returns an empty list.
     *
     * @return list of ScoreEntry objects
     */
    private List<ScoreEntry> loadScores() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<ScoreEntry>) obj;
            }
        } catch (Exception e) {
            System.err.println("Error loading " + FILE_NAME + ": " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Saves the current list of scores to the serialized file.
     */
    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            System.err.println("Error saving " + FILE_NAME + ": " + e.getMessage());
        }
    }

    /**
     * Adds a new score entry.
     * If a score already exists for the nickname, it replaces it only if the new score is higher.
     *
     * @param newEntry the new ScoreEntry to add
     */
    public void addScore(ScoreEntry newEntry) {
        Optional<ScoreEntry> existing = scores.stream()
                .filter(s -> s.getNickname().equalsIgnoreCase(newEntry.getNickname()))
                .findFirst();

        if (existing.isPresent()) {
            if (newEntry.getScore() > existing.get().getScore()) {
                scores.remove(existing.get());
                scores.add(newEntry);
            }
        } else {
            scores.add(newEntry);
        }

        saveScores();
    }

    /**
     * Returns the top N scores sorted in descending order by score.
     *
     * @param limit maximum number of top scores to return
     * @return list of top ScoreEntry objects
     */
    public List<ScoreEntry> getTopScores(int limit) {
        return scores.stream()
                .sorted(Comparator.comparingInt(ScoreEntry::getScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Returns all scores in the system.
     *
     * @return list of all ScoreEntry objects
     */
    public List<ScoreEntry> getAllScores() {
        return new ArrayList<>(scores);
    }

    /**
     * Clears all stored scores and updates the file.
     */
    public void clearScores() {
        scores.clear();
        saveScores();
    }
}
