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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;


@Path("/metrics")
public class MetricsResource {


    private final Logger LOGGER = LoggerFactory.getLogger(MetricsResource.class);
    @Inject
    KpiService kpiService;

    @Inject
    @RestClient
    PrometheusClient prometheusService;

    @GET
    @Path("/kpis")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getKpis(@QueryParam("start") String startTime,
                              @QueryParam("end") String endTime,
                              @QueryParam("gatewayId") String gatewayId) {
        return kpiService.calculateKpis(startTime, endTime, gatewayId, null);
    }


    @GET
    @Path("/today")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getTodayMetrics(@QueryParam("gatewayId") String gatewayId) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        String startTime = now.withHour(0).withMinute(0).withSecond(0).withNano(0).format(formatter) + "Z";
        String endTime = now.withHour(23).withMinute(59).withSecond(59).withNano(0).format(formatter) + "Z";


        return kpiService.calculateKpis(startTime, endTime, gatewayId, "5s");
    }

}

