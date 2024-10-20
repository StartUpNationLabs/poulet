import {AppBar, Button, Toolbar, Typography} from '@mui/material';
import {useNavigate} from 'react-router-dom';

export default function Navbar() {
    const navigate = useNavigate();

    return (
        <AppBar position="static">
            <Toolbar sx={{justifyContent: "space-between", textTransform: "uppercase"}}>
                <Typography variant="h6" component="div" sx={{cursor: "pointer"}} onClick={() => navigate('/')}>
                    Health Check App
                </Typography>
                <Button color="inherit" onClick={() => navigate('/login')} sx={{fontSize: '1.25rem'}}>
                    Login
                </Button>
            </Toolbar>
        </AppBar>
    );
}