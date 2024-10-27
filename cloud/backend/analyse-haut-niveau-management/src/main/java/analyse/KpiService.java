package analyse;


import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;



@ApplicationScoped
public class KpiService {

    private static final Logger log = Logger.getLogger(KpiService.class);

    @Inject
    @RestClient
    PrometheusClient prometheusService;



    public JsonObject calculateKpis(String start, String end, String gatewayId, String step) {
        JsonObject kpis = new JsonObject();
        log.info("Calculating KPIs for gateway " + gatewayId + " between" + start + " and " + end + " with step " + step);

        kpis.put("averageAcceleration",calculateMetricsForRange("acceleration", start, end, gatewayId, step));
        kpis.put("averageHeartRate", calculateMetricsForRange("heartrate", start, end, gatewayId, step));
        kpis.put("averageTemperature", calculateMetricsForRange("temperature", start, end, gatewayId, step));
        kpis.put("averageGlucose", calculateMetricsForRange("glucose", start, end, gatewayId, step));

        return kpis;
    }

    private JsonObject  calculateMetricsForRange(String metricName, String start, String end, String gatewayId, String step) {
        if (step != null) {
            step = "120s";
        }

        String query = metricName + "{gateway_id=\"" + gatewayId + "\"}";

        log.info("Querying Prometheus for metric " + query + " between " + start + " and " + end + " with step " + step);

        Response response = prometheusService.queryMetric(query, start, end, step);
        JsonObject result = new JsonObject(response.readEntity(String.class));



        List<Double> values = new ArrayList<>();
        if (response.getStatus() == 200 && result.getJsonObject("data") != null) {
            result.getJsonObject("data").getJsonArray("result").forEach(entry -> {
                ((JsonObject) entry).getJsonArray("values").forEach(value -> {
                    double valueToInsert = Double.parseDouble(value.toString().split(",")[1].split("\"")[1]);
                    values.add(valueToInsert);
                });
            });
        }
        OptionalDouble avg = values.stream().mapToDouble(v -> v).average();
        double max = values.stream().mapToDouble(v -> v).max().orElse(0.0);
        double min = values.stream().mapToDouble(v -> v).min().orElse(0.0);
        double variance = values.stream().mapToDouble(v -> Math.pow(v - avg.orElse(0.0), 2)).average().orElse(0.0);
        double stdDev = Math.sqrt(variance);

        JsonObject metricsStats = new JsonObject();
        metricsStats.put("average", avg.orElse(0.0));
        metricsStats.put("max", max);
        metricsStats.put("min", min);
        metricsStats.put("stdDev", stdDev);

        return metricsStats;
    }
}
