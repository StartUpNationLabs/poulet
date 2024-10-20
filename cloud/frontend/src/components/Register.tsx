import {useState} from 'react';
import {Box, Button, Container, MenuItem, TextField, Typography} from '@mui/material';
import {useMutation} from "@tanstack/react-query";
import {PatientDTO, PatientResourceApi} from "../clients/patient-management/src";
import {useNavigate} from "react-router-dom";

export default function Register() {
    const navigate = useNavigate();

    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [emergencyContactPhoneNumber, setEmergencyContactPhoneNumber] = useState('');
    const [gender, setGender] = useState('');

    const isFormValid = firstname && lastname && gender && /^\d{10}$/.test(emergencyContactPhoneNumber);

    const mutation = useMutation({
        mutationFn: (patient: PatientDTO) => {
            console.log(patient);
            return new PatientResourceApi().patientPost({patientDTO: patient});
        },
        onSuccess: () => {
            navigate('/');
        },
        onError: (error) => {
            console.log(error);
        }
    });

    const submitRegistration = () => {
        const patient : PatientDTO= { firstname, lastname, emergencyContactPhoneNumber, gender };

        mutation.mutate(patient);
    }

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
                    label="Phone Number"
                    value={emergencyContactPhoneNumber}
                    onChange={(e) => setEmergencyContactPhoneNumber(e.target.value)}
                    required
                    inputProps={{ maxLength: 10, pattern: "\\d{10}", inputMode: "numeric" }}
                    error={emergencyContactPhoneNumber.length !== 10 || /\D/.test(emergencyContactPhoneNumber)}
                    helperText={emergencyContactPhoneNumber.length !== 10 ? "Phone number must be exactly 10 digits" : /\D/.test(emergencyContactPhoneNumber) ? "Phone number must contain only digits" : ""}
                />
                <TextField
                    select
                    label="Gender"
                    value={gender}
                    onChange={(e) => setGender(e.target.value)}
                    required
                >
                    <MenuItem value="M">Male</MenuItem>
                    <MenuItem value="F">Female</MenuItem>
                    <MenuItem value="U">Undefined / Other</MenuItem>
                </TextField>
                <Button
                    variant="contained"
                    color="primary"
                    disabled={!isFormValid}
                    onClick={submitRegistration}
                >
                    Register
                </Button>
            </Box>
        </Container>
    );
}