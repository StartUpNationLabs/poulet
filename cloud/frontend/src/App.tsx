import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Navbar from "./components/Navbar.tsx";
import DoctorDashboard from "./components/dashboards/Doctor-dashboard.tsx";
import Register from "./components/Register.tsx";
import Search from "./components/Search.tsx";
import SearchResult from "./components/Search-results.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import Profil from "./components/profil/Profil.tsx";

function App() {
    const queryClient = new QueryClient();

    return (
        <QueryClientProvider client={queryClient}>
            <Router>
                <Navbar/>
                <Routes>
                    <Route path="/" element={<DoctorDashboard/>}/>
                    <Route path="/register" element={<Register/>}/>
                    <Route path="/search" element={<Search/>}/>
                    <Route path="/search-results" element={<SearchResult/>}/>
                    <Route path="/patient/:patientId" element={<Profil/>}/>
                    <Route path="/patient/" element={<Profil/>}/>
                </Routes>
            </Router>
        </QueryClientProvider>
    );
}

export default App
