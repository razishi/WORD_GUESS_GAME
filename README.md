#  Word Guess Game — React + Spring Boot Project

## Technologies Used

### Backend (Spring Boot):
- Java 17
- Spring Boot
- RESTful API
- Serialization (for words and scores)
- Maven

### Frontend (React):
- React 18+
- React Router
- Bootstrap 5 (for styling)
- Fetch API to connect to backend
- State management using React Hooks

---

##  Scoring System

Each player's score is calculated at the end of the game based on:

### 1. ** Time Taken**  
The faster the player guesses the word, the higher the score.

### 2. ** Number of Attempts**  
Fewer incorrect guesses result in a higher score.

### 3. ** Hint Usage**  
Using a hint reduces the final score.

###  Final Score Formula:
const calculateScore = () => {
        const base = 1000;
        const penalty = attempts * 10 + (hintUsed ? 100 : 0) + elapsedTime;
        return Math.max(0, base - penalty);
    }
    
- `attempts`: Total number of guesses made by the player  
- `timeInSeconds`: Total seconds passed since the game started  
- `hintPenalty`:  
  - 0 if no hint was used  
  - 100 points deducted if a hint was used  

> A player who finishes fast with fewer mistakes and without using a hint will get the highest score.

---

##  How to Exit the Game

At any time during gameplay, the player can **exit the game without saving their score to the leaderboard**.

 Simply click on the **"Word Game"** title at the top of the screen (the main heading), and you will be redirected back to the start screen.


---

##  How to Initializing the words file
Please first run the file WordInit.java  ( the version that is in GitHub already has words.ser for your comfort) 
---


