import React from "react";

/**
 * Functional component that displays information about the project and its creators.
 *
 * @returns {JSX.Element} The About page content including description and team member details.
 */
function AboutPage() {
    return (
        <div className="container mt-5">
            <h2 className="mb-3">📘 About the Project</h2>
            <p>
                This is a single-page application built with <strong>React</strong> and <strong>Spring Boot </strong>
                that challenges players to guess hidden words from various categories.
                It combines mechanics from games like Hangman and Wordle and includes
                features like hints, score tracking, and a leaderboard.
            </p>

            <p>
                The goal is to provide a fun and educational way to practice vocabulary, logic, and memory.
            </p>

            <hr />

            <h4>👨‍💻 Submitted by:</h4>
            <ul>
                <li>Razi Shibli , e-mail : razish@edu.jmc.ac.il</li>
                <li>Raneen Salem , e-mail : raneensal@edu.jmc.ac.il</li>

            </ul>

        </div>
    );
}

export default AboutPage;
