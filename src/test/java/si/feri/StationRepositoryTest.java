package si.feri;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import si.feri.station.StationRepository;

@QuarkusTest
public class StationRepositoryTest {
    @Inject
    StationRepository stationRepository;

    @AfterEach
    public void cleanUp() {
        stationRepository.deleteAll().await().indefinitely();
    }

    @Test
    public void testAddStation() {
        stationRepository.add("1", "Station 1", "Location 1").await().indefinitely();

        assert stationRepository.listAll().await().indefinitely().size() == 1;
    }

    @Test
    public void testGetStation() {
        stationRepository.add("1", "Station 1", "Location 1").await().indefinitely();

        assert stationRepository.get("1", "Location 1").await().indefinitely().getName().equals("Station 1");
    }

    @Test
    public void testGetAllStations() {
        stationRepository.add("1", "Station 1", "Location 1").await().indefinitely();
        stationRepository.add("2", "Station 2", "Location 2").await().indefinitely();

        assert stationRepository.listAll().await().indefinitely().size() == 2;
    }

//    @Test
//    public void testUpdateStation() {
//        stationRepository.add("1", "Station 1", "Location 1").await().indefinitely();
//        stationRepository.update("1", "123").await().indefinitely();
//
//        assert stationRepository.get("1", "Location 1").await().indefinitely().getBike_id().equals("1");
//    }

    @Test
    public void testDeleteStation() {
        stationRepository.add("1", "Station 1", "Location 1").await().indefinitely();
        stationRepository.delete("1").await().indefinitely();

        assert stationRepository.listAll().await().indefinitely().isEmpty();
    }
}
