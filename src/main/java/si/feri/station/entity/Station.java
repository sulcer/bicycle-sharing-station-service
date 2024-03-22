package si.feri.station.entity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station extends ReactivePanacheMongoEntity {
    public String station_number;
    public String name;
    public String location;
    public String rating;
    public String bike_id;
    public boolean available;
}
