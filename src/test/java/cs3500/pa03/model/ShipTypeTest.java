package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test class for the ShipType enumeration
 */
public class ShipTypeTest {

  @Test
  public void testGetSize() {
    assertEquals(6, ShipType.CARRIER.getSize());
    assertEquals(5, ShipType.BATTLESHIP.getSize());
    assertEquals(4, ShipType.DESTROYER.getSize());
    assertEquals(3, ShipType.SUBMARINE.getSize());
  }
}