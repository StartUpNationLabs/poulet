package analyse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/api/v1/query_range")
@RegisterRestClient(configKey = "prometheus-api")
public interface PrometheusClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response queryMetric(@QueryParam("query") String metricName,
                            @QueryParam("start") String startTime,
                            @QueryParam("end") String endTime,
                            @QueryParam("step") String step);
}
