import {useState} from 'react';
import {Box, Button, Container, MenuItem, TextField, Typography} from '@mui/material';

export default function Register() {
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [gender, setGender] = useState('');

    const isFormValid = firstname && lastname && gender;

    return (
        <Container sx={{pt: 4}}>
            <Typography variant={"h4"} gutterBottom>
                Register a New Patient
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
                <TextField
                    select
                    label="Gender"
                    value={gender}
                    onChange={(e) => setGender(e.target.value)}
                    required
                >
                    <MenuItem value="Male">Male</MenuItem>
                    <MenuItem value="Female">Female</MenuItem>
                    <MenuItem value="Undefined">Undefined / Other</MenuItem>
                </TextField>
                <Button
                    variant="contained"
                    color="primary"
                    disabled={!isFormValid}
                >
                    Register
                </Button>
            </Box>
        </Container>
    );
}