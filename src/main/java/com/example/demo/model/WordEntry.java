package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a word entry in the word guessing game.
 * Each entry contains a category, a word, and a hint.
 * This class is used for storing, transferring, and comparing word data.
 */
public class WordEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String category;
    private String word;
    private String hint;

    /**
     * Default constructor.
     * Required for serialization and deserialization.
     */
    public WordEntry() {
    }

    /**
     * Constructs a new WordEntry with all fields.
     *
     * @param category the category of the word (e.g., "animals")
     * @param word     the actual word to be guessed
     * @param hint     a textual hint to help guess the word
     */
    public WordEntry(String category, String word, String hint) {
        this.category = category;
        this.word = word;
        this.hint = hint;
    }

    /**
     * Gets the category of the word.
     *
     * @return the word's category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the word.
     *
     * @param category the new category
     */
    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * Gets the word itself.
     *
     * @return the actual word
     */
    public String getWord() {
        return word;
    }

    /**
     * Sets the word.
     *
     * @param word the new word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Gets the hint associated with the word.
     *
     * @return the hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * Sets the hint for the word.
     *
     * @param hint the new hint
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * Compares this WordEntry to another object for equality.
     * Two WordEntry objects are equal if they have the same category and word (case-insensitive).
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordEntry)) return false;
        WordEntry other = (WordEntry) o;
        return category.equalsIgnoreCase(other.category) &&
                word.equalsIgnoreCase(other.word);
    }

    /**
     * Computes the hash code based on lowercase category and word.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(category.toLowerCase(), word.toLowerCase());
    }
}
