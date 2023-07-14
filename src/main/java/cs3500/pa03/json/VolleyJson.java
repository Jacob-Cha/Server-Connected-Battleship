package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Record for a volley that is shot by the AI
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<CoordAdapter> shots) {
}
