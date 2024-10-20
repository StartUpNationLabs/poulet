package analyse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;


@Path("/hello")
public class GreetingResource {

    //logger
    private static final Logger log = Logger.getLogger(GreetingResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/kpis")
    @Produces(MediaType.APPLICATION_JSON)
    public String getKpis() {
        log.info("This is an info message.");
        return "KPIs";
    }
}
