package si.feri.station;
import io.smallrye.mutiny.Uni;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> createStation(CreateStationDto createStationDto) {
        log.info("Creating station with station_number: " + createStationDto.station_number);
        stationRepository.add(createStationDto.station_number, createStationDto.name, createStationDto.location).await().indefinitely();
        return Uni.createFrom().nullItem();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllStations() {
        log.info("Getting all stations");
        return stationRepository.listAll().await().indefinitely().stream().map(Station::getName).collect(Collectors.toList());
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Station>> getAll() {
        log.info("Getting all stations");
        return stationRepository.listAll();
    }

    @GET
    @Path("/{station_number}/{location}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Station> getStation(String station_number, String location) {
        log.info("Getting station with station_number: " + station_number + " and location: " + location);
        return stationRepository.get(station_number, location);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> deleteStation(String station_number) {
        log.info("Deleting station with station_number: " + station_number);
        stationRepository.delete(station_number).await().indefinitely();
        return Uni.createFrom().nullItem();
    }

    //delete all
    @DELETE
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> deleteAll() {
        log.info("Deleting all stations");
        stationRepository.deleteAll().await().indefinitely();
        return Uni.createFrom().nullItem();
    }
}

