import {useQuery} from "@tanstack/react-query";
import {MetricsResourceApi} from "../../clients/analyse-haut-niveau-management/src";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography} from "@mui/material";

interface ProfilKpiProps {
    gatewayId: string;
}

export default function ProfilKpi(props: ProfilKpiProps) {
    const {
        data: kpi,
        isLoading: isLoading
    } = useQuery({
        queryKey: ['getAlertsByPatientId', props.gatewayId],
        queryFn: async () => {
            const metricsResourceApi: MetricsResourceApi = new MetricsResourceApi();
            return metricsResourceApi.metricsTodayGet({
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

    return (
        <>

            {isLoading ? (
                <Typography>Loading kpi's...</Typography>
            ) : (
                <>
                    {kpi !== undefined && Array.isArray(kpi.data) && kpi.data.length > 0 ? (
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="center">Label</TableCell>
                                        <TableCell align="center">Value</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {kpi.data.map((metric, index) => (
                                        <TableRow key={index}>
                                            <TableCell align="center">{metric.key}</TableCell>
                                            <TableCell align="center">{metric.value}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    ) : <Typography>No metrics available</Typography>}
                </>
            )}
        </>
    );
}

