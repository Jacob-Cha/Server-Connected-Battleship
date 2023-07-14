package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.BattleSalvoView;
import java.beans.Transient;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the BattleSalvoModel class
 */
public class TestBattleSalvoModel {

  BattleSalvoModel bsm;

  @BeforeEach
  public void setup() {
    bsm = new BattleSalvoModel(new Scanner(System.in));
    bsm.setShips(6, 6, 1, 1, 1, 1);
  }

  @Test
  public void testSetShips() {
    bsm.setShips(6, 6, 1, 1, 1, 1);
    assertEquals(4, bsm.playerList.size());
    assertEquals(6, bsm.getBoard("A").length);
    assertEquals(6, bsm.getBoard("B").length);
  }

  @Test
  public void testIsGameOver() {
    assertFalse(bsm.isGameOver());
    bsm.playerList = new ArrayList<>();
    assertTrue(bsm.isGameOver());
    bsm.aiList = new ArrayList<>();
    assertTrue(bsm.isGameOver());
    bsm.setShips(6, 6, 1, 1, 1, 1);
    bsm.aiList = new ArrayList<>();
    assertTrue(bsm.isGameOver());

  }

  @Test
  public void testRemoveSunkShips() {
    Ship ship = bsm.playerList.get(0);
    for (Coord coord : ship.getShipCoords()) {
      coord.doesShotHit(coord.getX(), coord.getY());
    }
    Ship aiShip = bsm.aiList.get(0);
    for (Coord coord : aiShip.getShipCoords()) {
      coord.doesShotHit(coord.getX(), coord.getY());
    }
    bsm.removeSunkShips();
  }


  @Test
  public void testShooting() {
    StringBuilder mockInput =
        new StringBuilder("0 0\n1 1\n2 2\n3 3");
    InputStream input = new ByteArrayInputStream(mockInput.toString().getBytes());
    Scanner scanner = new Scanner(input);
    bsm = new BattleSalvoModel(scanner);
    bsm.setShips(6, 6, 1, 1, 1, 1);
    PlayerModel pm = new PlayerModel(scanner);
    bsm.shooting();



  }

}
