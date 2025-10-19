import React, { useEffect, useState } from "react";

/**
 * ManageWordsPage component allows an admin to manage the word database.
 * Includes adding, editing, deleting words, with validation.
 */
function ManageWordsPage() {
    const [words, setWords] = useState([]);
    const [category, setCategory] = useState("");
    const [word, setWord] = useState("");
    const [hint, setHint] = useState("");
    const [message, setMessage] = useState("");
    const [editIndex, setEditIndex] = useState(null);
    const [editedWord, setEditedWord] = useState({ category: "", word: "", hint: "" });

    /**
     * Load words when component mounts.
     */
    useEffect(() => {
        loadWords();
    }, []);

    /**
     * Fetches all word entries from the backend API.
     */
    const loadWords = () => {
        fetch("/api/words")
            .then((res) => res.json())
            .then((data) => setWords(data))
            .catch(() => setMessage("Failed to fetch words."));
    };

    /**
     * Validates that all required fields are filled and category/word contain only letters.
     * @param {Object} entry - The word entry to validate.
     * @returns {boolean} True if valid, false if not.
     */
    const validateEntry = (entry) => {
        const letters = /^[a-zA-Z]+$/;
        if (!entry.category || !entry.word || !entry.hint) {
            setMessage("All fields are required.");
            return false;
        }
        if (!letters.test(entry.category) || !letters.test(entry.word)) {
            setMessage("Category and word must contain only a–z letters.");
            return false;
        }
        return true;
    };

    /**
     * Adds a new word to the database after validation.
     */
    const addWord = () => {
        const newEntry = {
            category: category.toLowerCase().trim(),
            word: word.toLowerCase().trim(),
            hint: hint.trim(),
        };

        if (!validateEntry(newEntry)) return;

        fetch("/api/words", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newEntry),
        })
            .then((res) => {
                if (!res.ok) return res.text().then((text) => { throw new Error(text); });
                setCategory(""); setWord(""); setHint(""); setMessage("");
                loadWords();
            })
            .catch((err) => setMessage(err.message));
    };

    /**
     * Deletes a word entry from the database.
     * @param {number} index - Index of the word in the words list.
     */
    const deleteWord = (index) => {
        const entry = words[index];
        fetch("/api/words", {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(entry),
        })
            .then((res) => {
                if (!res.ok) throw new Error("Failed to delete word.");
                loadWords(); setMessage("");
            })
            .catch((err) => setMessage(err.message));
    };

    /**
     * Initializes edit mode for a specific word.
     * @param {number} index - Index of the word to edit.
     */
    const startEdit = (index) => {
        setEditIndex(index);
        setEditedWord({ ...words[index] });
    };

    /**
     * Cancels the editing process and clears edited state.
     */
    const cancelEdit = () => {
        setEditIndex(null);
        setEditedWord({ category: "", word: "", hint: "" });
        setMessage("");
    };

    /**
     * Saves the edited word by replacing the original entry with the updated one.
     */
    const saveEdit = () => {
        if (!validateEntry(editedWord)) return;

        const original = words[editIndex];

        // Delete original word, then add updated word
        fetch("/api/words", {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(original),
        })
            .then((res) => {
                if (!res.ok) throw new Error("Failed to delete original word.");
                // Add updated word
                return fetch("/api/words", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        category: editedWord.category.toLowerCase().trim(),
                        word: editedWord.word.toLowerCase().trim(),
                        hint: editedWord.hint.trim(),
                    }),
                });
            })
            .then((res) => {
                if (!res.ok) return res.text().then((text) => { throw new Error(text); });
                cancelEdit(); loadWords();
            })
            .catch((err) => setMessage(err.message));
    };
    // JSX for managing words
    return (
        <div className="container mt-5">
            <h2 className="mb-4">🛠 Manage Words</h2>

            {message && (
                <div className="alert alert-danger alert-dismissible fade show" role="alert">
                    {message}
                    <button type="button" className="btn-close" onClick={() => setMessage("")}></button>
                </div>
            )}

            <div className="row mb-4">
                <div className="col">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Category (a-z only)"
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                    />
                </div>
                <div className="col">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Word (a-z only)"
                        value={word}
                        onChange={(e) => setWord(e.target.value)}
                    />
                </div>
                <div className="col">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Hint"
                        value={hint}
                        onChange={(e) => setHint(e.target.value)}
                    />
                </div>
                <div className="col">
                    <button className="btn btn-primary w-100" onClick={addWord}>
                        Add Word
                    </button>
                </div>
            </div>

            <table className="table table-bordered">
                <thead className="table-light">
                <tr>
                    <th>#</th>
                    <th>Category</th>
                    <th>Word</th>
                    <th>Hint</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {words.map((entry, index) => (
                    <tr key={index}>
                        <td>{index + 1}</td>
                        {editIndex === index ? (
                            <>
                                <td><input value={editedWord.category} onChange={(e) => setEditedWord({ ...editedWord, category: e.target.value })} className="form-control" /></td>
                                <td><input value={editedWord.word} onChange={(e) => setEditedWord({ ...editedWord, word: e.target.value })} className="form-control" /></td>
                                <td><input value={editedWord.hint} onChange={(e) => setEditedWord({ ...editedWord, hint: e.target.value })} className="form-control" /></td>
                                <td>
                                    <button className="btn btn-success btn-sm me-2" onClick={saveEdit}>Save</button>
                                    <button className="btn btn-secondary btn-sm" onClick={cancelEdit}>Cancel</button>
                                </td>
                            </>
                        ) : (
                            <>
                                <td>{entry.category}</td>
                                <td>{entry.word}</td>
                                <td>{entry.hint}</td>
                                <td>
                                    <button className="btn btn-warning btn-sm me-2" onClick={() => startEdit(index)}>Edit</button>
                                    <button className="btn btn-danger btn-sm" onClick={() => deleteWord(index)}>Delete</button>
                                </td>
                            </>
                        )}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default ManageWordsPage;
