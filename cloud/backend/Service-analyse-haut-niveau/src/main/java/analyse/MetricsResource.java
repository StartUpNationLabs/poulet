package analyse;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@Path("/metrics")
public class MetricsResource {

    @Inject
    @RestClient
    PrometheusClient prometheusClient;

    @GET
    @Path("/up")
    public String getUpMetrics() {
        String query = "up";
        return prometheusClient.queryPrometheus(query);
    }
}

