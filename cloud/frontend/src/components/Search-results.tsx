import {
    Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import {PatientResourceApi} from '../clients/patient-management/src';
import {useQuery} from "@tanstack/react-query";
import {useLocation, useNavigate} from "react-router-dom";

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
            return patientManagementService.patientFindByNameGet({
                firstname: firstname ?? "",
                lastname: lastname ?? ""
            });
        },
        enabled: firstname !== undefined && lastname !== undefined,
        refetchOnWindowFocus: 'always',
        retry: 0
    });

    if (isLoading) {
        return <Typography>Loading...</Typography>;
    }

    const handleRowClick = (id: string) => {
        navigate(`/patient/${id}`);
    };

    return (
        <Container sx={{pt: 4}}>
            <Typography variant={"h4"} gutterBottom>
                Search results
            </Typography>
            {patients !== undefined && Array.from(patients.data).length > 0 ? (
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>ID</TableCell>
                                <TableCell>First Name</TableCell>
                                <TableCell>Last Name</TableCell>
                                <TableCell>Gender</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {Array.from(patients.data).map((patient) => (
                                <TableRow key={patient.id} hover onClick={() => handleRowClick(patient.id ?? "")}
                                          style={{cursor: 'pointer'}}>
                                    <TableCell>{patient.id}</TableCell>
                                    <TableCell>{patient.firstname}</TableCell>
                                    <TableCell>{patient.lastname}</TableCell>
                                    <TableCell>{patient.gender}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            ) : (
                <Typography>No patients found</Typography>
            )}
        </Container>
    );
}