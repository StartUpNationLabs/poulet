import {useState} from 'react';
import {Box, Button, Container, TextField, Typography} from '@mui/material';

export default function Search() {
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');

    const isFormValid = firstname && lastname;

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
                <Button
                    variant="contained"
                    color="primary"
                    disabled={!isFormValid}
                >
                    Search
                </Button>
            </Box>
        </Container>
    );
}