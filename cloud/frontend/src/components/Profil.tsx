import {
    Avatar,
    Box,
    Container,
    Paper,
    Table,
    TableBody,
    TableCell, TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import {Face, Face2, Person, Phone} from "@mui/icons-material";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect} from "react";
import {useQuery} from "@tanstack/react-query";
import {PatientResourceApi} from "../clients/patient-management/src";
import {AlertResourceApi} from "../clients/alert-management/src";

export default function Profil() {
    const {patientId} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        if (!patientId || patientId === "") {
            navigate('/');
        }
    }, [patientId, navigate]);

    const {
        data: profil,
        isLoading
    } = useQuery({
        queryKey: ['getPatientById', patientId],
        queryFn: async () => {
            const patientManagementService: PatientResourceApi = new PatientResourceApi();
            return patientManagementService.patientPatientIdGet({
                patientId: patientId ?? ""
            });
        },
        enabled: patientId !== undefined && patientId !== "",
        refetchOnWindowFocus: 'always',
        retry: 0
    });

    const gatewayId = profil?.data.gatewayId ?? "";

    const {
        data: alerts,
        isLoading: isLoading2
    } = useQuery({
        queryKey: ['getAlertsByPatientId', gatewayId],
        queryFn: async () => {
            const alertManagementService: AlertResourceApi = new AlertResourceApi();
            return alertManagementService.alertGatewayGatewayIdGet({
                gatewayId: gatewayId ?? ""
            });
        },
        enabled: gatewayId !== undefined && gatewayId !== "",
        refetchOnWindowFocus: 'always',
        retry: 0
    });

    if (isLoading) {
        return <Typography>Loading...</Typography>;
    }

    const getAvatarIcon = (gender: string) => {
        const iconProps = { fontSize: "50px" };
        switch (gender) {
            case 'MALE':
                return <Face sx={iconProps} />;
            case 'FEMALE':
                return <Face2 sx={iconProps} />;
            default:
                return <Person sx={iconProps} />;
        }
    };

    function formatName(name: string): string {
        if (!name) return '';
        return name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
    }

    function formatTimestamp(timestamp: string): string {
        const date = new Date(timestamp);
        return new Intl.DateTimeFormat('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            second: 'numeric',
            hour12: true
        }).format(date);
    }

    return (
        <Container sx={{pt: 4}}>
            <Typography variant={"h4"} gutterBottom>
                Profil
            </Typography>
            {profil !== undefined ? (
                <>
                    <Box sx={{
                        border: '1px solid #ccc',
                        borderRadius: '8px',
                        p: 3,
                        mt: 2,
                        display: 'flex',
                        alignItems: 'center'
                    }}>
                        <Avatar sx={{width: 100, height: 100, mr: 3}}>
                            {getAvatarIcon(profil.data.gender ?? "")}
                        </Avatar>
                        <Box>
                            <Typography variant="h6" gutterBottom>
                                {formatName(profil.data.firstname ?? "")} {formatName(profil.data.lastname ?? "")}
                            </Typography>
                            <Typography variant="body1" gutterBottom>
                                Gender: {profil.data.gender}
                            </Typography>
                            <Typography variant="body1" gutterBottom sx={{ display: 'flex', alignItems: 'center' }}>
                                <Phone sx={{ mr: 1 }} /> {profil.data.emergencyContactPhoneNumber}
                            </Typography>
                        </Box>
                    </Box>
                    <Box sx={{ width: '100%', pt: 4 }} >
                        <Typography variant="h6" gutterBottom>
                            Alerts
                        </Typography>
                        {isLoading2 ? (
                            <Typography>Loading alerts...</Typography>
                        ) : (
                            alerts !== undefined && alerts.data.length > 0 ? (
                                <TableContainer component={Paper}>
                                    <Table>
                                        <TableHead>
                                            <TableRow>
                                                <TableCell align="center">Severity</TableCell>
                                                <TableCell align="center">Message</TableCell>
                                                <TableCell align="center">Date</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {Array.from(alerts.data).map((alert) => (
                                                <TableRow key={alert.id}>
                                                    <TableCell align="center">{alert.severity}</TableCell>
                                                    <TableCell align="center">{alert.message}</TableCell>
                                                    <TableCell align="center">{formatTimestamp(alert.timestamp) ?? ""}</TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                            ) : (
                                <Typography>No alerts detected for this patient</Typography>
                            )
                        )}
                    </Box>
                </>

            ) : (
                <Typography variant="body1" gutterBottom>
                    No data found
                </Typography>
            )}
        </Container>
    );
}