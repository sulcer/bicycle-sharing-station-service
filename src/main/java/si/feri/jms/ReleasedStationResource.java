package si.feri.jms;
import java.util.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import si.feri.jms.consumer.Consumer;

@Path("/release-station")
public class ReleasedStationResource {

    @Inject
    Consumer consumer;

    private final Logger log = Logger.getLogger(ReleasedStationResource.class.getName());

    @GET
    @Path("last")
    @Produces(MediaType.TEXT_PLAIN)
    public String last() {
        log.info("Getting last requested station release");
        return consumer.getRequestedStationRelease();
    }

}
