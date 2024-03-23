package si.feri.jms.consumer;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.*;
import lombok.Getter;
import si.feri.jms.producer.Producer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@ApplicationScoped
public class Consumer implements Runnable {

    @Inject
    ConnectionFactory connectionFactory;

    private final Logger log = Logger.getLogger(Producer.class.getName());

   @Getter
   private volatile String requestedStationRelease;

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue("stationRelease"));
            while (true) {
                Message message = consumer.receive();
                if (message == null) return;
                requestedStationRelease = message.getBody(String.class);
                log.info("requestedStationRelease: " + requestedStationRelease);
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }
}
