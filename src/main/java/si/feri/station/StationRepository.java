package si.feri.station;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import si.feri.station.entity.Station;
import java.util.List;

@ApplicationScoped
public class StationRepository implements ReactivePanacheMongoRepository<Station> {

    public Uni<Void> add(String station_number, String name, String location) {
        Station station = new Station();
        station.station_number = station_number;
        station.name = name;
        station.location = location;
        station.available = false;

        return persist(station).replaceWithVoid();
    }

    public Uni<Station> get(String station_number, String location) {
        return find("station_number = ?1 and location = ?2", station_number, location).firstResult();
    }

    public Uni<List<Station>> listAll() {
        return findAll().list();
    }

    public Uni<Void> update(String station_number, String bike_id) {
        Station station = (Station) find("station_number", station_number).firstResult();
        if (station == null) {
            return Uni.createFrom().nullItem();
        }
        return persist(station).replaceWithVoid();
    }

    public Uni<Void> delete(String station_number) {
        return delete("station_number", station_number).replaceWithVoid();
    }
}
