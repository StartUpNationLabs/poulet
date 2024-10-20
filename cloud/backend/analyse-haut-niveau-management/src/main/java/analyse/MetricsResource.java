package analyse;

import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Path("/metrics")
public class MetricsResource {


    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsResource.class);
    @Inject
    KpiService kpiService;

    @Inject
    @RestClient
    PrometheusClient prometheusService;

    @GET
    @Path("/kpis")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getKpis(@QueryParam("start") String startTime,
                              @QueryParam("end") String endTime) {
        return kpiService.calculateKpis(startTime, endTime);
    }

}

