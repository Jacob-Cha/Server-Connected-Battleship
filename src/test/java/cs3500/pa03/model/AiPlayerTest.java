package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * AiPlayer testing class
 */
public class AiPlayerTest {
  private AiPlayer aiPlayer;

  @BeforeEach
  public void setup() {
    aiPlayer = new AiPlayer();
  }

  @Test
  public void testSetup() {
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.SUBMARINE, 2);
    specs.put(ShipType.DESTROYER, 1);
    List<Ship> ships = aiPlayer.setup(6, 6, specs);
    assertEquals(3, ships.size());
    assertEquals(6, aiPlayer.aiBoard.length);

  }

  @Test
  public void testEndGame() {
    aiPlayer.endGame(GameResult.TIE, "Both ships sunk");
  }



}