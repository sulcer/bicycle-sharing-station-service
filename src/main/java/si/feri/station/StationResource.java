package si.feri.station;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import si.feri.station.dto.CreateStationDto;
import si.feri.station.entity.Station;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/station")
public class StationResource {
    private final Logger log = Logger.getLogger(StationResource.class.getName());

    @Inject
    StationRepository stationRepository;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public void createStation(CreateStationDto createStationDto) {
        log.info("Creating station with station_number: " + createStationDto.station_number);
        stationRepository.add(createStationDto.station_number, createStationDto.name, createStationDto.location);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllStations() {
        log.info("Getting all stations");
        return stationRepository.listAll().await().indefinitely().stream().map(Station::getName).collect(Collectors.toList());
    }

    @GET
    @Path("/{station_number}/{location}")
    @Produces(MediaType.APPLICATION_JSON)
    public Station getStation(String station_number, String location) {
        log.info("Getting station with station_number: " + station_number + " and location: " + location);
        return stationRepository.get(station_number, location).await().indefinitely();
    }

//    @PUT
//    @Path("/{station_number}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public void updateStation(String station_number, String bike_id) {
//        log.info("Updating station with station_number: " + station_number + " and bike_id: " + bike_id);
//        stationRepository.update(station_number, bike_id);
//    }

    @DELETE
    @Path("/{station_number}")
    @Produces(MediaType.TEXT_PLAIN)
    public void deleteStation(String station_number) {
        log.info("Deleting station with station_number: " + station_number);
        stationRepository.delete(station_number).await().indefinitely();
    }
}

