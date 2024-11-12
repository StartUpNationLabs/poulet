import {useQuery} from "@tanstack/react-query";
import {MetricsResourceApi} from "../../clients/analyse-haut-niveau-management/src";
import {Paper, Typography, Box} from "@mui/material";
import GaugeComponent  from 'react-gauge-component';


interface ProfilKpiProps {
    gatewayId: string;
}

interface KPIData {
    average: number;
    max: number;
    min: number;
    stdDev: number;
}

export default function ProfilKpi(props: ProfilKpiProps) {
    const {
        data: kpi,
        isLoading: isLoading
    } = useQuery({
        queryKey: ['getMetricsByPatientId', props.gatewayId],
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
                           <Box>
                           {kpi !== undefined && typeof kpi?.data === 'object' ? (
                               <Box display="flex" justifyContent="space-around" alignItems="center" mt={2}>
                                   {Object.keys(kpi.data).map((key) => (
                                       <Paper key={key} style={{ padding: '20px', borderRadius: '10px', textAlign: 'center' }}>
                                           <Typography variant="h6">{key}</Typography>
                                           <GaugeComponent 
                                               type="semicircle"
                                               value={(kpi.data[key as keyof typeof kpi.data] as KPIData).average}
                                               maxValue={(kpi.data[key as keyof typeof kpi.data] as KPIData).max || 1}
                                               minValue={(kpi.data[key as keyof typeof kpi.data] as KPIData).min || 0}
                                                arc={{
                                                    gradient: true,
                                                    colorArray: ['#00FF15', '#FF2121'],
                                                    width: 0.15,
                                                    padding: 0,
                                                    subArcs:
                                                        (kpi.data[key as keyof typeof kpi.data] as KPIData).max === 0 && (kpi.data[key as keyof typeof kpi.data] as KPIData).min === 0
                                                            ? [
                                                                { limit: 0.1 },
                                                                { limit: 0.5 },
                                                                { limit: 0.9 }
                                                            ]
                                                            : [
                                                                { limit: (kpi.data[key as keyof typeof kpi.data] as KPIData).min + ((kpi.data[key as keyof typeof kpi.data] as KPIData).max - (kpi.data[key as keyof typeof kpi.data] as KPIData).min) / 4 },
                                                                { limit: (kpi.data[key as keyof typeof kpi.data] as KPIData).min + ((kpi.data[key as keyof typeof kpi.data] as KPIData).max - (kpi.data[key as keyof typeof kpi.data] as KPIData).min) / 2 },
                                                                { limit: (kpi.data[key as keyof typeof kpi.data] as KPIData).min + ((kpi.data[key as keyof typeof kpi.data] as KPIData).max - (kpi.data[key as keyof typeof kpi.data] as KPIData).min) / 1.2 }
                                                            ].sort((a, b) => a.limit - b.limit)
                                                }}
                                                pointer={{type: "arrow", elastic: true}}
                                           />
                                       </Paper>
                                   ))}
                               </Box>
                           ) : (
                               <Typography>No metrics available</Typography>
                           )}
                       </Box>
                    ) : <Typography>No metrics available</Typography>}
                </>
            )}
        </>
    );
}

