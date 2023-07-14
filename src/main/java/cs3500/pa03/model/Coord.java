package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


/**
 * Represents a ships coordinates
 */
public class Coord {
  private int xval;
  private int yval;
  private boolean isHit;

  /**
   * Coord class the represents the coordinates on the board
   */
  public Coord(int x, int y) {
    this.xval = x;
    this.yval = y;
  }

  /**
   * Checks if a given set of coordinates hit a ships coordinates
   *
   * @param x checks the x coordinate
   * @param y checks the y coordinate
   */
  public void doesShotHit(int x, int y) {
    if (x == this.xval && y == this.yval) {
      this.isHit = true;
    }
  }

  public boolean getIsHit() {
    return this.isHit;
  }

  /**
   * Gets the x or y value of a coordinate based on the parameter input
   *
   * @param num gives the x or y coordinate depending on the number
   *
   * @return the coordinate desired
   */
  public int getVal(int num) {
    if (num == 0) {
      return xval;
    } else {
      return yval;
    }
  }


  public int getX() {
    return xval;
  }

  public int getY() {
    return yval;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return xval == coord.xval && yval == coord.yval;
  }

  @Override
  public int hashCode() {
    return Objects.hash(xval, yval);
  }




}
