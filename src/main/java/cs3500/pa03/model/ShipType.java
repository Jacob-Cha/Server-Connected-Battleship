package cs3500.pa03.model;

/**
 * this enumeration represents the different types of ships
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int size;

  /**
   * Constructs a ShipType with specific size
   *
   * @param size the size of the ship
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Gets the size of the ship
   *
   * @return the size of the ship
   */
  public int getSize() {
    return this.size;
  }
}