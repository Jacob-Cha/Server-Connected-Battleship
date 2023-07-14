package cs3500.pa03.json;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;

/**
 * Adapter for the Coord class so it can work with JSON
 */
public class CoordAdapter {

  private final int x;
  private final int y;

  public CoordAdapter(Coord coord) {
    this.x = coord.getX();
    this.y = coord.getY();
  }

  @JsonCreator
  public CoordAdapter(
      @JsonProperty("x") int y,
      @JsonProperty("y") int x) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.y;
  }

  public int getY() {
    return this.x;
  }

}
