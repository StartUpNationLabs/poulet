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
                    {kpi !== undefined  && typeof kpi?.data === 'object' ? (
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="center">Label</TableCell>
                                        <TableCell align="center">Value</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {Object.keys(kpi?.data).map((key) => (
                                        <TableRow key={key}>
                                            <TableCell align="center">{key}</TableCell>
                                            // @ts-ignore
                                            <TableCell align="center">{Number(kpi?.data[key as keyof typeof kpi.data]?["average"]:-1).toFixed(2)}</TableCell>
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

