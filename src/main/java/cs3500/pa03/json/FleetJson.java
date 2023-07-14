package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON record to create a list of ships for the server
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapter> ships) {

}
