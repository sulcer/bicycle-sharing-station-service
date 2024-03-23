package si.feri.jms.producer;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import si.feri.station.StationRepository;
import si.feri.station.entity.Station;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import jakarta.jms.*;

@ApplicationScoped
public class Producer implements Runnable {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    StationRepository stationRepository;

    private final Logger log = Logger.getLogger(Producer.class.getName());

    private final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    void onStart(@Observes StartupEvent ev) {
        scheduler.scheduleWithFixedDelay(this, 0L, 1L, TimeUnit.SECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }
    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            Station station = stationRepository.listAll().await().indefinitely().get(random.nextInt(3));

            //context.createProducer().send(context.createQueue("stationRelease"), station.getStation_number() + " " + station.getLocation());
            context.createProducer().send(context.createQueue("stationRelease"), station.getStation_number());
            log.info("Station with station_number: " + station.getStation_number() + " requested release");
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
