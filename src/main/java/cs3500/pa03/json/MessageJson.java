package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Record for a method name and the arguments it takes
 */
public record MessageJson(

    @JsonProperty("method-name") String messageName,
    @JsonProperty("arguments") JsonNode arguments
) {
}
