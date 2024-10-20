import {Box, Button, Container, TextField, Typography} from '@mui/material';
import {useNavigate} from "react-router-dom";
import {useState} from "react";

export default function Search() {
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const isFormValid = firstname && lastname;

    const handleSearch = () => {
        if (!firstname.trim() || !lastname.trim()) {
            setError('Both firstname and lastname are required.');
            return;
        }
        setError('');
        navigate(`/search-results?firstname=${firstname}&lastname=${lastname}`);
    };

    return (
        <Container sx={{pt: 4}}>
            <Typography variant={"h4"} gutterBottom>
                Search for a Patient
            </Typography>
            <Box component="form"
                 sx={{display: 'flex', flexDirection: 'column', gap: 2, width: '300px', margin: 'auto'}}>
                <TextField
                    label="First Name"
                    value={firstname}
                    onChange={(e) => setFirstname(e.target.value)}
                    required
                />
                <TextField
                    label="Last Name"
                    value={lastname}
                    onChange={(e) => setLastname(e.target.value)}
                    required
                />
                {error && <Typography color="error">{error}</Typography>}
                <Button
                    variant="contained"
                    color="primary"
                    disabled={!isFormValid}
                    onClick={handleSearch}
                >
                    Search
                </Button>
            </Box>
        </Container>
    );
}