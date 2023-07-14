package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON record for the name of the user and the game type they are wanting to play
 */
public record JoinArguments(@JsonProperty("name") String name,
                            @JsonProperty("game-type") String gameType) {
}
