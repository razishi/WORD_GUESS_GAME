import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

/**
 * HomePage component provides the entry point for users.
 * It allows users to enter a nickname, choose a category, and start the game.
 */
function HomePage() {
    // Local state for nickname, categories, selected category, and error/info message
    const [nickname, setNickname] = useState("");
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    /**
     * Fetches the list of categories from the backend when the component is mounted.
     * Displays an error message if fetching fails.
     */
    useEffect(() => {
        fetch("/api/words/categories")
            .then((res) => res.json())
            .then((data) => setCategories(data))
            .catch((err) => {
                console.error("Error loading categories:", err);
                setMessage("⚠️ Failed to load categories. Is the server running?");
            });
    }, []);

    /**
     * Validates user input and navigates to the GamePage if all fields are filled.
     */
    const startGame = () => {
        if (!nickname || !selectedCategory) {
            setMessage("❗ Please enter a nickname and select a category.");
            return;
        }

        navigate(`/game?nickname=${nickname}&category=${selectedCategory}`);
    };


    // JSX rendering the homepage form and interactions
    return (
        <div className="container mt-5">
            <h1 className="mb-4">🎮 Welcome to the Word Game!</h1>

            {message && (
                <div className="alert alert-danger alert-dismissible fade show" role="alert">
                    {message}
                    <button type="button" className="btn-close" onClick={() => setMessage("")}></button>
                </div>
            )}

            <div className="mb-3">
                <label className="form-label">Nickname:</label>
                <input
                    type="text"
                    className="form-control"
                    value={nickname}
                    onChange={(e) => setNickname(e.target.value)}
                />
            </div>

            <div className="mb-3">
                <label className="form-label">Category:</label>
                <select
                    className="form-select"
                    value={selectedCategory}
                    onChange={(e) => setSelectedCategory(e.target.value)}
                >
                    <option value="">-- Choose a Category --</option>
                    {categories.map((cat, idx) => (
                        <option key={idx} value={cat}>
                            {cat}
                        </option>
                    ))}
                </select>
            </div>

            <button
                className="btn btn-primary"
                disabled={!nickname || !selectedCategory}
                onClick={startGame}
            >
                Start Game
            </button>
        </div>
    );
}

export default HomePage;
