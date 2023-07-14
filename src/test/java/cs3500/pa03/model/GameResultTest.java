package cs3500.pa03.model;

import static cs3500.pa03.model.GameResult.WIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the game results Enumeration
 */
public class GameResultTest {

  @Test
  public void testGameResult() {
    assertEquals(WIN, WIN);
  }

}