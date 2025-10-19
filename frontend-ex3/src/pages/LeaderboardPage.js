import React, { useEffect, useState } from "react";

/**
 * LeaderboardPage component fetches and displays the list of top scores.
 * It shows details like nickname, score, time taken, number of attempts, and hint usage.
 */
function LeaderboardPage() {
    // State for storing fetched scores and loading status
    const [scores, setScores] = useState([]);
    const [loading, setLoading] = useState(true);

    /**
     * Fetch leaderboard scores from the backend API when component is mounted.
     */
    useEffect(() => {
        fetch("http://localhost:8080/api/scores")
            .then((res) => res.json())
            .then((data) => setScores(data))
            .catch((err) => console.error("Failed to load scores:", err))
            .finally(() => setLoading(false));
    }, []);

    // JSX that displays the leaderboard table or appropriate message
    return (
        <div className="container mt-5">
            <h2 className="mb-4">🏆 Leaderboard</h2>

            {loading ? (
                <p>Loading...</p>
            ) : scores.length === 0 ? (
                <p>No scores yet.</p>
            ) : (
                <table className="table table-striped table-bordered">
                    <thead className="table-light">
                    <tr>
                        <th>#</th>
                        <th>Nickname</th>
                        <th>Score</th>
                        <th>Time (s)</th>
                        <th>Attempts</th>
                        <th>Hint Used</th>
                    </tr>
                    </thead>
                    <tbody>
                    {scores.map((entry, idx) => (
                        <tr key={idx}>
                            <td>{idx + 1}</td>
                            <td>{entry.nickname}</td>
                            <td>{entry.score}</td>
                            <td>{entry.time}</td>
                            <td>{entry.attempts}</td>
                            <td>{entry.usedHint ? "Yes" : "No"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default LeaderboardPage;
