package com.example.demo.init;

import com.example.demo.model.WordEntry;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to initialize and serialize a list of default word entries.
 * It creates a `words.ser` file containing predefined WordEntry objects.
 */
public class WordInit {

    // File where the serialized word list will be saved
    private static final String FILE_NAME = "words.ser";

    /**
     * Main method that runs the initializer.
     * Creates a list of sample WordEntry objects and serializes them into a file.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        List<WordEntry> defaultWords = new ArrayList<>();

        // Adding sample word entries by category
        defaultWords.add(new WordEntry("animals", "lion", "King of the jungle"));
        defaultWords.add(new WordEntry("animals", "tiger", "Striped big cat"));
        defaultWords.add(new WordEntry("colors", "blue", "Sky color"));
        defaultWords.add(new WordEntry("colors", "green", "Grass color"));
        defaultWords.add(new WordEntry("fruits", "apple", "Keeps the doctor away"));
        defaultWords.add(new WordEntry("fruits", "banana", "Yellow and sweet"));
        defaultWords.add(new WordEntry("body", "heart", "Pumps blood"));
        defaultWords.add(new WordEntry("body", "brain", "Controls thoughts"));
        defaultWords.add(new WordEntry("countries", "japan", "Land of the rising sun"));
        defaultWords.add(new WordEntry("countries", "egypt", "Home of the pyramids"));

        // Serialize the list to a .ser file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(defaultWords);
            System.out.println("✅ words.ser created with " + defaultWords.size() + " words.");
        } catch (Exception e) {
            System.err.println("❌ Failed to write words.ser: " + e.getMessage());
        }
    }
}
