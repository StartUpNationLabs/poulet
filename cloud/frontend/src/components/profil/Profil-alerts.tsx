import {useQuery} from "@tanstack/react-query";
import {AlertResourceApi} from "../../clients/alert-management/src";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography} from "@mui/material";

interface ProfilAlertsProps {
    gatewayId: string;
}

export default function ProfilAlerts(props: ProfilAlertsProps) {
    const {
        data: alerts,
        isLoading: isLoading
    } = useQuery({
        queryKey: ['getAlertsByPatientId', props.gatewayId],
        queryFn: async () => {
            const alertManagementService: AlertResourceApi = new AlertResourceApi();
            return alertManagementService.alertGatewayGatewayIdGet({
                gatewayId: props.gatewayId ?? ""
            });
        },
        enabled: props.gatewayId !== undefined && props.gatewayId !== "",
        refetchOnWindowFocus: 'always',
        retry: 0
    });

    if (isLoading) {
        return <Typography>Loading...</Typography>;
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
        <>
        {isLoading ? (
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
                                        <TableCell align="center">{formatTimestamp(alert.timestamp ?? "")}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                ) :
                    <Typography>No alerts detected for this patient</Typography>
        )}
        </>
    );
}