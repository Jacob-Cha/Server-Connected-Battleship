package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the model for the BattleSalvo
 */
public class BattleSalvoModel {
  private final Player p1 = new AiPlayer();
  PlayerModel playerModel = new PlayerModel(new Scanner(System.in));
  private final Player p2 = new ConsolePlayer(playerModel);
  List<Ship> playerList = new ArrayList<>();
  List<Ship> aiList = new ArrayList<>();
  char[][] playerBoard;
  char[][] aiBoard;
  char[][] shownAiBoard;
  private int height;
  private int width;
  private boolean valid;
  private GameResult gameResult;
  private Scanner scanner;

  public BattleSalvoModel(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Sets up the ships and the board for the player and ai
   *
   * @param height the height of the board
   * @param width  the width of the board
   * @param ship1  amount of the first ship to be on the board
   * @param ship2  amount of the second ship to be on the board
   * @param ship3  amount of the third ship on the board
   * @param ship4  amount of the fourth ship on the board
   */
  public void setShips(int height, int width, int ship1, int ship2, int ship3, int ship4) {
    this.height = height;
    this.width = width;
    this.playerBoard = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        playerBoard[i][j] = '0';
      }
    }
    this.aiBoard = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        aiBoard[i][j] = '0';
      }
    }
    this.shownAiBoard = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        shownAiBoard[i][j] = '0';
      }
    }
    Map<ShipType, Integer> shipMap = new HashMap<>();
    shipMap.put(ShipType.CARRIER, ship1);
    shipMap.put(ShipType.BATTLESHIP, ship2);
    shipMap.put(ShipType.DESTROYER, ship3);
    shipMap.put(ShipType.SUBMARINE, ship4);
    playerList = p1.setup(height, width, shipMap);
    aiList = p2.setup(height, width, shipMap);
    makeBoards(playerList, aiList);
  }

  /**
   * Makes the board for the ai and the player
   *
   * @param playerShips player ships that are going to be placed
   * @param aiShips     the ai ships that are going to be placed on the board
   */
  public void makeBoards(List<Ship> playerShips, List<Ship> aiShips) {
    placeShips(playerShips, this.playerBoard);
    placeShips(aiShips, this.aiBoard);

  }

  /**
   * Places the ships onto the board in the correct manner
   *
   * @param ships the ships that are going to be placed on the board
   * @param board the board that the ships will be placed onto
   */
  public void placeShips(List<Ship> ships, char[][] board) {
    Random rand = new Random();
    for (Ship ship : ships) {
      boolean isValid = false;

      do {
        int row = rand.nextInt(this.height);
        int col = rand.nextInt(this.width);

        // Randomly choose a direction: 0 for horizontal, 1 for vertical
        boolean isHorizontal = rand.nextBoolean();

        if (isHorizontal) {
          // Check if ship fits horizontally
          if (col + ship.getSize() <= this.width) {
            isValid = true;
            for (int i = 0; i < ship.getSize(); i++) {
              if (board[row][col + i] != '0') {
                isValid = false;
                break;
              }
            }
            if (isValid) {
              for (int i = 0; i < ship.getSize(); i++) {
                board[row][col + i] = ship.getSymbol();
                ship.addCoords(row, col + i);
                ship.setDirection(true);
              }
            }
          }
        } else {
          // Check if ship fits vertically
          if (row + ship.getSize() <= this.height) {
            isValid = true;
            for (int i = 0; i < ship.getSize(); i++) {
              if (board[row + i][col] != '0') {
                isValid = false;
                break;
              }
            }
            if (isValid) {
              for (int i = 0; i < ship.getSize(); i++) {
                board[row + i][col] = ship.getSymbol();
                ship.addCoords(row + i, col);
                ship.setDirection(false);
              }
            }
          }
        }
      } while (!isValid);
    }
  }

  /**
   * Gives the board that is needed per string request
   *
   * @param type the board that will be returned
   * @return the board that is requested
   */
  public char[][] getBoard(String type) {
    if (type.equals("A")) {
      return this.playerBoard;
    } else {
      return this.shownAiBoard;
    }
  }

  /**
   * takes coordinates and places the shots onto the board following the correct requirements
   */
  public void shooting() {
    valid = true;
    List<Coord> aiShots = p1.takeShots();
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < playerList.size(); i++) {
      int xval = this.scanner.nextInt();
      int yval = this.scanner.nextInt();
      Coord curInput = new Coord(xval, yval);
      shots.add(curInput);
    }
    List<Coord> playerHits = new ArrayList<>();
    List<Coord> aiHits = new ArrayList<>();

    for (Coord playerShot : shots) {
      if (playerShot.getVal(0) < 0 || playerShot.getVal(0) >= height
          || playerShot.getVal(1) < 0 || playerShot.getVal(1) >= width) {
        valid = false;
        return;
      }
      if (aiBoard[playerShot.getVal(0)][playerShot.getVal(1)] == '0') {
        shownAiBoard[playerShot.getVal(0)][playerShot.getVal(1)] = 'M';
      } else {
        shownAiBoard[playerShot.getVal(0)][playerShot.getVal(1)] = 'H';
        playerHits.add(new Coord(playerShot.getVal(0), playerShot.getVal(1)));
        for (Ship curShip : aiList) {
          for (Coord curCoord : curShip.getShipCoords()) {
            curCoord.doesShotHit(playerShot.getVal(0), playerShot.getVal(1));
          }
        }
      }
    }
    for (Coord aiShot : aiShots) {
      if (playerBoard[aiShot.getVal(0)][aiShot.getVal(1)] == '0'
          || playerBoard[aiShot.getVal(0)][aiShot.getVal(1)] == 'M') {
        playerBoard[aiShot.getVal(0)][aiShot.getVal(1)] = 'M';
      } else {
        playerBoard[aiShot.getVal(0)][aiShot.getVal(1)] = 'H';
        aiHits.add(new Coord(aiShot.getVal(0), aiShot.getVal(1)));
        for (Ship curShip : playerList) {
          for (Coord curCoord : curShip.getShipCoords()) {
            curCoord.doesShotHit(aiShot.getVal(0), aiShot.getVal(1));
          }
        }
      }
    }
    p1.reportDamage(playerHits);
    p2.reportDamage(aiHits);
  }

  /**
   * Checks if a ship has been sunk and will remove it from the list of ships
   */
  public void removeSunkShips() {
    for (int i = 0; i < playerList.size(); i++) {
      if (playerList.get(i).isShipSunk()) {
        playerList.remove(i);
        i--;
      }
    }
    for (int i = 0; i < aiList.size(); i++) {
      if (aiList.get(i).isShipSunk()) {
        aiList.remove(i);
        i--;
      }
    }
  }

  /**
   * Checks to see if a list of ships are empty to end the game
   */
  public boolean isGameOver() {
    if (playerList.size() == 0 && aiList.size() == 0) {
      gameResult = GameResult.TIE;
      return true;
    } else if (playerList.size() == 0) {
      gameResult = GameResult.LOSE;
      return true;
    } else if (aiList.size() == 0) {
      gameResult = GameResult.WIN;
      return true;
    } else {
      return false;
    }
  }

  public GameResult getGameResult() {
    return gameResult;
  }

  public boolean getValidity() {
    return this.valid;
  }

  public List<Ship> getPlayerList() {
    return this.playerList;
  }

  public List<Ship> getAiList() {
    return this.aiList;
  }


}
