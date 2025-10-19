import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import GamePage from "./pages/GamePage";
import LeaderboardPage from "./pages/LeaderboardPage";
import ManageWordsPage from "./pages/ManageWordsPage";
import AboutPage from "./pages/AboutPage";
import Navbar from "./components/Navbar"; // <-- import this

function App() {
    return (
        <Router>
            <Navbar /> {/* Add this above the Routes */}
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/game" element={<GamePage />} />
                <Route path="/leaderboard" element={<LeaderboardPage />} />
                <Route path="/manage-words" element={<ManageWordsPage />} />
                <Route path="/about" element={<AboutPage />} />
            </Routes>
        </Router>
    );
}

export default App;
