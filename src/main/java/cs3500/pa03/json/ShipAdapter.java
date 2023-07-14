package cs3500.pa03.json;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Ship;

/**
 * Ship adapter that makes the ship class applicable for the JSON and server
 */
public class ShipAdapter {

  private final CoordAdapter coord;
  private final int length;
  private final String direction;

  /**
   * Constructor for the ShipAdapter
   */
  public ShipAdapter(Ship ship) {
    this.coord = new CoordAdapter(ship.getShipCoords().get(0));
    this.length = ship.getType().getSize();
    this.direction = ship.getDirection();
  }

  /**
   * Creates the JSON for the ShipAdapter
   */
  @JsonCreator
  public ShipAdapter(
      @JsonProperty("coord") CoordAdapter coord,
      @JsonProperty("length") int length,
      @JsonProperty("direction") String direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  public CoordAdapter getCoord() {
    return this.coord;
  }

  public int getLength() {
    return this.length;
  }

  public String getDirection() {
    return this.direction;
  }

}
