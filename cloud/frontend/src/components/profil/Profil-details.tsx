import {Patient} from "../../clients/patient-management/src";
import {Avatar, Box, Typography} from "@mui/material";
import {Face, Face2, Person, Phone} from "@mui/icons-material";

interface ProfilDetailsProps {
   patient: Patient;
}

export default function ProfilDetails(props: ProfilDetailsProps) {
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

    return  (
        <>
            {props.patient !== undefined ? (
                <Box sx={{
                    border: '1px solid #ccc',
                    borderRadius: '8px',
                    p: 3,
                    mt: 2,
                    display: 'flex',
                    alignItems: 'center'
                }}>
                    <Avatar sx={{width: 100, height: 100, mr: 3}}>
                        {getAvatarIcon(props.patient.gender ?? "")}
                    </Avatar>
                    <Box>
                        <Typography variant="h6" gutterBottom>
                            {formatName(props.patient.firstname ?? "")} {formatName(props.patient.lastname ?? "")}
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                            Gender: {props.patient.gender}
                        </Typography>
                        <Typography variant="body1" gutterBottom sx={{ display: 'flex', alignItems: 'center' }}>
                            <Phone sx={{ mr: 1 }} /> {props.patient.emergencyContactPhoneNumber}
                        </Typography>
                    </Box>
                </Box>
            ) : <></>}
        </>
    )
}