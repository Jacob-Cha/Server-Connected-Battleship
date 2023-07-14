package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a player that can give inputs to the ConsolePlayer class
 */
public class PlayerModel {
  private int numShots;
  private Scanner scanner;

  public PlayerModel(Scanner scanner) {
    this.scanner = scanner;
  }

  public void setParams(int size) {
    numShots = size;
  }

  /**
   * Gets a list of coordinates that input by the player
   *
   * @return The list of coordinates input by the user
   */
  public List<Coord> accumulateShots() {
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < numShots; i++) {
      int xval = this.scanner.nextInt();
      int yval = this.scanner.nextInt();
      Coord curInput = new Coord(xval, yval);
      shots.add(curInput);
    }
    return shots;
  }
}
