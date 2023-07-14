package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a singular ship
 */
public class Ship {
  private final ShipType type;
  private boolean isSunk;
  private final List<Coord> shipCoords = new ArrayList<>();
  private String direction;


  Ship(ShipType type) {
    this.type = type;
    this.isSunk = false;
  }


  public int getSize() {
    return this.type.getSize();
  }

  public ShipType getType() {
    return type;
  }


  /**
   * Gets the symbol of the ship based on the ShipType
   */
  public char getSymbol() {
    if (this.type.equals(ShipType.CARRIER)) {
      return 'C';
    } else if (this.type.equals(ShipType.BATTLESHIP)) {
      return 'B';
    } else if (this.type.equals(ShipType.DESTROYER)) {
      return 'D';
    } else {
      return 'S';
    }
  }

  public void addCoords(int x, int y) {
    shipCoords.add(new Coord(x, y));
  }

  /**
   * Checks if all the coordinates of the ship have been hit
   *
   * @return a boolean on whether it has been sunk
   */
  public boolean isShipSunk() {
    boolean over = false;
    for (Coord coords : this.shipCoords) {
      if (coords.getIsHit()) {
        over = true;
      } else {
        over = false;
        break;
      }
    }
    return over;
  }

  public List<Coord> getShipCoords() {
    return this.shipCoords;
  }


  /**
   * Sets the direction for the direction variable based off of a boolean value
   */
  public void setDirection(boolean dir) {
    if (dir) {
      this.direction = "HORIZONTAL";
    } else {
      this.direction = "VERTICAL";
    }
  }

  public String getDirection() {
    return this.direction;
  }
}


