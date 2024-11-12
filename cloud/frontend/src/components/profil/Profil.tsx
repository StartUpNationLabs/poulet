import {Box, Container, Typography} from "@mui/material";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect} from "react";
import {useQuery} from "@tanstack/react-query";
import {PatientResourceApi} from "../../clients/patient-management/src";
import ProfilDetails from "./Profil-details.tsx";
import ProfilAlerts from "./Profil-alerts.tsx";
import ProfilKpi from "./Profil-kpi.tsx";

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

    if (isLoading) {
        return <Typography>Loading...</Typography>;
    }

    return (
        <Container sx={{pt: 4}}>
            <Typography variant={"h4"} gutterBottom>
                Profil
            </Typography>
            {profil !== undefined ? (
                <>
                    <ProfilDetails patient={profil.data}/>
                    <Box sx={{width: '100%', pt: 4}}>
                        <Typography variant="h6" gutterBottom>
                            Latest metrics:
                        </Typography>
                        <ProfilKpi gatewayId={gatewayId}/>
                    </Box>
                    
                    <Box sx={{width: '100%', pt: 4}}>
                        <Typography variant="h6" gutterBottom>
                            Alerts
                        </Typography>
                        <ProfilAlerts gatewayId={gatewayId}/>
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