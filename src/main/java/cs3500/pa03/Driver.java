package cs3500.pa03;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa03.controller.BattleSalvoController;
import cs3500.pa03.controller.ProxyController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.BattleSalvoModel;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.BattleSalvoView;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) throws JsonProcessingException {
    if (args.length == 0) {
      BattleSalvoController cont = new BattleSalvoController(new BattleSalvoView(System.out),
          new BattleSalvoModel(new Scanner(System.in)), new Scanner(System.in));
      cont.gameSetup();
      cont.gameLoop();
    } else {
      String host = args[0];
      String port = args[1];

      try {

        Socket socket = new Socket(host, Integer.parseInt(port));

        Player aiPlayer = new AiPlayer();

        ProxyController proxyController = new ProxyController(socket, aiPlayer);
        proxyController.run();
      } catch (IOException e) {
        System.out.println("Error creating socket: " + e.getMessage());
      }
    }
  }
}

