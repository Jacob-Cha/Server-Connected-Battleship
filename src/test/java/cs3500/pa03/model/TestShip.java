package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the ship class
 */
public class TestShip {

  @Test
  public void testGetType() {
    Ship ship = new Ship(ShipType.BATTLESHIP);

    assertEquals(ShipType.BATTLESHIP, ship.getType());
  }
}
