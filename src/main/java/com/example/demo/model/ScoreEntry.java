package com.example.demo.model;

import java.io.Serializable;

/**
 * A data model representing a player's score entry.
 * This class is used to store and transfer score-related information
 * such as nickname, score value, time taken, number of attempts, and hint usage.
 */
public class ScoreEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nickname;
    private int score;
    private int time;
    private int attempts;
    private boolean usedHint;

    /**
     * Default constructor for deserialization and framework use.
     */
    public ScoreEntry() {}


    /**
     * Parameterized constructor for creating a complete score entry.
     *
     * @param nickname the player's nickname
     * @param score the final calculated score
     * @param time the total time taken in seconds
     * @param attempts number of guesses made
     * @param usedHint whether a hint was used
     */
    public ScoreEntry(String nickname, int score, int time, int attempts, boolean usedHint) {
        this.nickname = nickname;
        this.score = score;
        this.time = time;
        this.attempts = attempts;
        this.usedHint = usedHint;
    }

    /**
     * Gets the nickname of the player.
     * @return nickname
     */
    public String getNickname() { return nickname; }

    /**
     * Sets the nickname of the player.
     * @param nickname the new nickname
     */
    public void setNickname(String nickname) { this.nickname = nickname; }


    /**
     * Gets the player's score.
     * @return score
     */
    public int getScore() { return score; }

    /**
     * Sets the player's score.
     * @param score the new score
     */
    public void setScore(int score) { this.score = score; }

    /**
     * Gets the time taken to complete the game.
     * @return time in seconds
     */
    public int getTime() { return time; }

    /**
     * Sets the time taken.
     * @param time in seconds
     */
    public void setTime(int time) { this.time = time; }

    /**
     * Gets the number of attempts made.
     * @return number of attempts
     */
    public int getAttempts() { return attempts; }

    /**
     * Sets the number of attempts.
     * @param attempts the new attempt count
     */
    public void setAttempts(int attempts) { this.attempts = attempts; }

    /**
     * Returns true if a hint was used.
     * @return true if hint was used, false otherwise
     */
    public boolean isUsedHint() { return usedHint; }

    /**
     * Sets whether a hint was used.
     * @param usedHint true if a hint was used, false otherwise
     */
    public void setUsedHint(boolean usedHint) { this.usedHint = usedHint; }
}
