package analyse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/api/v1")
@RegisterRestClient(configKey = "prometheus-api")
public interface PrometheusClient {

    @GET
    @Path("/query")
    @Produces("application/json")
    String queryPrometheus(@QueryParam("query") String query);
}
