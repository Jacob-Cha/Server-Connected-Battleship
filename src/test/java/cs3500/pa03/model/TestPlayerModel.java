package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests the player model class
 */
public class TestPlayerModel {

  @Test
  public void testAccumulateShots() {
    StringBuilder mockInput =
        new StringBuilder("0 0\n1 1\n2 2\n3 3");
    InputStream input = new ByteArrayInputStream(mockInput.toString().getBytes());
    Scanner scanner = new Scanner(input);
    PlayerModel pm = new PlayerModel(scanner);
    pm.setParams(4);
    pm.accumulateShots();

  }
}
