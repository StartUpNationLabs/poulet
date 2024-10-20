import {Box, Button, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";

interface DashboardSectionProps {
    title: string;
    actionText: string;
    url: string;
}

export default function DashboardSection(props: DashboardSectionProps) {
    const navigate = useNavigate();
    return (
        <Box textAlign="center" p={4} borderRadius={2} m={2}>
            <Typography variant="h5" gutterBottom>
                {props.title}
            </Typography>
            <Button variant="contained" color="primary" onClick={() => navigate(props.url)}>
                {props.actionText}
            </Button>
        </Box>
    );
}