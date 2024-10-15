import {Avatar, Box, Container, Typography} from "@mui/material";
import {Face, Face2, Female, Male, Person} from "@mui/icons-material";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect} from "react";
import {useQuery} from "@tanstack/react-query";
import {PatientResourceApi} from "../clients/patient-management/src";

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

    return (
        <Container sx={{pt: 4}}>
            <Typography variant={"h4"} gutterBottom>
                Profil
            </Typography>
            {profil !== undefined ? (
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
                            {profil.data.firstname} {profil.data.lastname}
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                            Gender: {profil.data.gender}
                        </Typography>
                    </Box>
                </Box>
            ) : (
                <Typography variant="body1" gutterBottom>
                    No data found
                </Typography>
            )}
        </Container>
    );
}