import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";


/**
 * GamePage component handles the logic and UI for the word guessing game.
 * It fetches a word, tracks guesses, provides hints, and calculates the final score.
 */
function GamePage() {
    const location = useLocation();
    const navigate = useNavigate();

    // Game state variables
    const [wordData, setWordData] = useState(null);
    const [displayedWord, setDisplayedWord] = useState("");
    const [guess, setGuess] = useState("");
    const [attempts, setAttempts] = useState(0);
    const [hintUsed, setHintUsed] = useState(false);
    const [startTime, setStartTime] = useState(null);
    const [elapsedTime, setElapsedTime] = useState(0);
    const [intervalId, setIntervalId] = useState(null);
    const [message, setMessage] = useState(""); // ✅

    // Extract nickname and category from query params
    const queryParams = new URLSearchParams(location.search);
    const nickname = queryParams.get("nickname");
    const category = queryParams.get("category");

    /**
     * Effect to start the game: fetches a random word from the server.
     * Also initializes the timer.
     */
    useEffect(() => {
        if (!nickname || !category) {
            navigate("/");
            return;
        }

        fetch(`/api/words/random?category=${category}`)
            .then((res) => {
                if (!res.ok) throw new Error("Failed to fetch word");
                return res.json();
            })
            .then((data) => {
                setWordData(data);
                setDisplayedWord("_".repeat(data.word.length));
                const start = Date.now();
                setStartTime(start);
                const id = setInterval(() => {
                    setElapsedTime(Math.floor((Date.now() - start) / 1000));
                }, 1000);
                setIntervalId(id);
            })
            .catch((err) => {
                setMessage("❌ " + err.message);
                navigate("/");
            });

        return () => {
            if (intervalId) clearInterval(intervalId);
        };
    }, []);

    /**
     * Updates the timer every second based on the startTime.
     */
    useEffect(() => {
        if (!startTime) return;
        const id = setInterval(() => {
            const seconds = Math.floor((Date.now() - startTime) / 1000);
            setElapsedTime(seconds);
        }, 1000);
        setIntervalId(id);
        return () => clearInterval(id);
    }, [startTime]);

    /**
     * Handles user guess (letter or full word).
     * Updates the displayed word and checks for win condition.
     */
    const handleGuess = () => {
        if (!guess || !wordData) return;
        setAttempts((prev) => prev + 1);

        if (guess.length === 1) {
            const updated = wordData.word
                .split("")
                .map((c, i) =>
                    displayedWord[i] !== "_" ? displayedWord[i] : c === guess.toLowerCase() ? c : "_"
                )
                .join("");
            setDisplayedWord(updated);
            if (!updated.includes("_")) setTimeout(() => endGame(updated), 300);
        } else if (guess.toLowerCase() === wordData.word.toLowerCase()) {
            setDisplayedWord(wordData.word);
            setTimeout(() => endGame(wordData.word), 300);
        }

        setGuess("");
    };

    /**
     * Displays a hint to the player (if not already used).
     */
    const showHint = () => {
        setHintUsed(true);
        setMessage("💡 Hint: " + wordData?.hint);
    };

    /**
     * Calculates the score based on time, attempts, and whether a hint was used.
     * @returns {number} Final score
     */
    const calculateScore = () => {
        const base = 1000;
        const penalty = attempts * 10 + (hintUsed ? 100 : 0) + elapsedTime;
        return Math.max(0, base - penalty);
    };

    /**
     * Ends the game by calculating the score and sending it to the backend.
     * Navigates to the leaderboard afterward.
     *
     * @param {string} finalWord - The final revealed word.
     */
    const endGame = (finalWord) => {
        clearInterval(intervalId);
        const finalScore = calculateScore();

        fetch("/api/scores", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                nickname,
                score: finalScore,
                time: elapsedTime,
                attempts,
                usedHint: hintUsed,
            }),
        })
            .then(() => navigate("/leaderboard"))
            .catch((err) => {
                setMessage("⚠️ Failed to save score: " + err.message);
                navigate("/leaderboard");
            });
    };

    // JSX rendering the game interface
    return (
        <div className="container mt-5">
            <h2>Hello, {nickname}!</h2>
            <h4>Category: {category}</h4>

            {message && (
                <div className="alert alert-info alert-dismissible fade show" role="alert">
                    {message}
                    <button type="button" className="btn-close" onClick={() => setMessage("")}></button>
                </div>
            )}

            {wordData ? (
                <>
                    <h1 className="my-4">{displayedWord.split("").join(" ")}</h1>
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="form-control"
                            value={guess}
                            onChange={(e) => setGuess(e.target.value)}
                            placeholder="Enter letter or word"
                        />
                        <button className="btn btn-success" onClick={handleGuess}>
                            Guess
                        </button>
                    </div>

                    <button className="btn btn-warning mb-3" onClick={showHint} disabled={hintUsed}>
                        {hintUsed ? "Hint used" : "Show Hint"}
                    </button>

                    <div className="mt-3">
                        <p>⏱ Time: {elapsedTime} sec</p>
                        <p>❗ Attempts: {attempts}</p>
                    </div>
                </>
            ) : (
                <p>Loading word...</p>
            )}
        </div>
    );
}

export default GamePage;
