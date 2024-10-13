import {Box, Container, Typography} from '@mui/material';
import DashboardSection from "./Dashboard-section.tsx";

export default function DoctorDashboard() {
    return (
        <Container sx={{pt: 4, justifyContent: "center"}}>
            <Typography variant="h4">
                Dashboard
            </Typography>
            <Box display="flex" justifyContent="center" alignItems="center">
                <DashboardSection title={"Register a New Patient"} actionText={"register"} url={"/register"}/>
                <DashboardSection title={"Search for a Patient"} actionText={"Search"} url={"/search"}/>
            </Box>
        </Container>
    );
}