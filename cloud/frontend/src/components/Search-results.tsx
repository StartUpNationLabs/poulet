import {Container, Typography} from "@mui/material";
import {PatientResourceApi} from '../clients/patient-management/src';
import {useQuery} from "@tanstack/react-query";
import {useLocation, useNavigate} from "react-router-dom"; // Adjust the import path as necessary

export default function SearchResult() {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const firstname = searchParams.get('firstname');
    const lastname = searchParams.get('lastname');
    const navigate = useNavigate();

    // Redirect to search form if firstname or lastname is missing
    if (!firstname || !lastname) {
        navigate('/search');
    }

    const {
        data: patients,
        isLoading
    } = useQuery({
        queryKey: ['searchPatientByName', firstname, lastname],
        queryFn: async () => {
            const patientManagementService: PatientResourceApi = new PatientResourceApi();
            return patientManagementService.patientFindByNameGet({firstname: firstname ?? "", lastname: lastname ?? ""});
        },
        enabled: firstname !== undefined && lastname !== undefined,
        refetchOnWindowFocus: 'always',
        retry: 0
    });

    if (isLoading) {
        return <Typography>Loading...</Typography>;
    }

    return (
        <Container sx={{pt: 4}}>
            <Typography variant={"h4"} gutterBottom>
                Search results
            </Typography>
            { patients !== undefined && Array.from(patients.data).length > 0 ? (
                Array.from(patients.data).map(patient => (
                    <Typography key={patient.id}>
                        {patient.firstname} {patient.lastname} {patient.gender} {patient.id}
                    </Typography>
                ))
            ) : (
                <Typography>No patients found</Typography>
            )}
        </Container>
    );
}