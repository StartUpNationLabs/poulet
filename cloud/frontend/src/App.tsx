import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Navbar from "./components/Navbar.tsx";
import DoctorDashboard from "./components/dashboards/Doctor-dashboard.tsx";
import Register from "./components/Register.tsx";
import Search from "./components/Search.tsx";

function App() {
    return (
        <>
            <Router>
                <Navbar/>
                <Routes>
                    {/* Add your routes here */}
                    <Route path="/" element={<DoctorDashboard/>}/>
                    <Route path="/register" element={<Register/>}/>
                    <Route path="/search" element={<Search/>}/>
                </Routes>
            </Router>
        </>
    );
}

export default App
