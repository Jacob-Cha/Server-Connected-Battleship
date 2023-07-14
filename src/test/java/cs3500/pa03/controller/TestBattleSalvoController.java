package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.BattleSalvoModel;
import cs3500.pa03.model.ConsolePlayer;
import cs3500.pa03.model.PlayerModel;
import cs3500.pa03.view.BattleSalvoView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the BattleSalvoController
 */
class TestBattleSalvoController {

  BattleSalvoController bsc;

  @BeforeEach


  @Test
  void testGameSetup() {
    BattleSalvoModel bsm = new BattleSalvoModel(new Scanner(System.in));
    BattleSalvoView bsv = new BattleSalvoView(System.out);
    bsc = new BattleSalvoController(bsv, bsm, new Scanner(System.in));
    ConsolePlayer humanPlayer = new ConsolePlayer(new PlayerModel(new Scanner(System.in)));
    AiPlayer aiPlayer = new AiPlayer();

    StringBuilder mockInput =
        new StringBuilder("99 99\n6\n6\n3 3 1 1\n1 1 1 1\nj 1\n2 10\n33\n4 4");
    for (int x = 0; x < 6; x++) {
      for (int y = 0; y < 6; y++) {
        mockInput.append("\n").append(x).append(" ").append(y);
      }
    }
    InputStream input = new ByteArrayInputStream(mockInput.toString().getBytes());
    Scanner scanner = new Scanner(input);

    BattleSalvoController controller = new BattleSalvoController(bsv, bsm, scanner);

    controller.gameSetup();

  }


}
