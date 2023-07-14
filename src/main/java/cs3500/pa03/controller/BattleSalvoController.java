package cs3500.pa03.controller;

import cs3500.pa03.model.BattleSalvoModel;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.view.BattleSalvoView;
import java.util.Scanner;

/**
 * Represents the controller for BattleSalvo
 */
public class BattleSalvoController {
  private final BattleSalvoView view;
  public final BattleSalvoModel model;
  private final Scanner input;



  /**
   * Constructor for BattleSalvo Controller
   */
  public BattleSalvoController(BattleSalvoView view, BattleSalvoModel model, Scanner input) {
    this.view = view;
    this.model = model;
    this.input = input;
  }



  /**
   * Sets up the game by asking the user for a board size and amount of ships
   */
  public void gameSetup() {
    int height;
    int width;
    int maxFleet;
    view.showPrompt("""
        Hello! Welcome to OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------""");
    while (true) {
      height = input.nextInt();
      width = input.nextInt();
      if (width > 15 || height > 15 || height < 6 || width < 6) {
        view.showPrompt("Uh Oh! You've entered invalid dimensions. Please remember "
            + "that height and width of the game must be in the range of [6, 15]. Try again!\n"
            + "------------------------------------");
      } else {
        break;
      }
    }
    maxFleet = Math.min(width, height);
    int carrier;
    int battleship;
    int destroyer;
    int submarine;
    while (true) {
      view.showPrompt("Please enter your fleet in the order [Carrier, Battleship, Destroyer, "
          + "Submarine].\nRemember, your fleet may not exceed size " + maxFleet
          + "\n\"------------------------------------");
      carrier = input.nextInt();
      battleship = input.nextInt();
      destroyer = input.nextInt();
      submarine = input.nextInt();
      boolean fleetSize = (carrier + battleship + destroyer + submarine) > maxFleet;
      if (carrier < 1 || battleship < 1 || destroyer < 1 || submarine < 1 || fleetSize) {
        view.showPrompt("Uh Oh! You've entered invalid fleet sizes");
      } else {
        break;
      }
    }
    model.setShips(height, width, carrier, battleship, destroyer, submarine);
    System.out.println("AI:");
    view.printBoard(model.getBoard("B"));
    System.out.println("PLAYER:");
    view.printBoard(model.getBoard("A"));
  }

  /**
   * Method that has the main game loop asking for player to insert coordinates until someone wins
   */
  public void gameLoop() {
    boolean gameOver = false;;
    while (!gameOver) {
      view.showPrompt("Please enter " + model.getPlayerList().size() + " shots: ");
      view.showPrompt("-------------------------------------------------");
      model.shooting();
      if (!model.getValidity()) {
        view.showPrompt("Invalid shots inputted, please try again...");
        continue;
      }
      System.out.println("AI:");
      view.printBoard(model.getBoard("B"));
      System.out.println("PLAYER:");
      view.printBoard(model.getBoard("A"));
      model.removeSunkShips();
      gameOver = model.isGameOver();
    }
    GameResult end = model.getGameResult();
    if (end.equals(GameResult.TIE)) {
      view.showPrompt("Game ended in a tie");
    } else if (end.equals(GameResult.WIN)) {
      view.showPrompt("You win!");
    } else if (end.equals(GameResult.LOSE)) {
      view.showPrompt("You lose!");
    }
  }


}
