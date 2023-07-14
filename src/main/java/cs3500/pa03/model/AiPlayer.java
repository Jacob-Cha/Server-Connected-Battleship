package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents the AI player in BattleSalvo
 */
public class AiPlayer implements Player {

  List<Ship> aiShips;
  char[][] aiBoard;
  List<Coord> shotsTaken = new ArrayList<>();

  @Override
  public String name() {
    return null;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    aiBoard = new char[height][width];
    List<Ship> ships = new ArrayList<>();
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        ships.add(new Ship(entry.getKey()));
      }
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        aiBoard[i][j] = '0';
      }
    }
    Random rand = new Random();
    for (Ship ship : ships) {
      boolean isValid = false;

      do {
        int row = rand.nextInt(height);
        int col = rand.nextInt(width);

        // Randomly choose a direction: 0 for horizontal, 1 for vertical
        boolean isHorizontal = rand.nextBoolean();

        if (isHorizontal) {
          // Check if ship fits horizontally
          if (col + ship.getSize() <= width) {
            isValid = true;
            for (int i = 0; i < ship.getSize(); i++) {
              if (aiBoard[row][col + i] != '0') {
                isValid = false;
                break;
              }
            }
            if (isValid) {
              for (int i = 0; i < ship.getSize(); i++) {
                aiBoard[row][col + i] = ship.getSymbol();
                ship.addCoords(row, col + i);
                ship.setDirection(true);
              }
            }
          }
        } else {
          // Check if ship fits vertically
          if (row + ship.getSize() <= height) {
            isValid = true;
            for (int i = 0; i < ship.getSize(); i++) {
              if (aiBoard[row + i][col] != '0') {
                isValid = false;
                break;
              }
            }
            if (isValid) {
              for (int i = 0; i < ship.getSize(); i++) {
                aiBoard[row + i][col] = ship.getSymbol();
                ship.addCoords(row + i, col);
                ship.setDirection(false);
              }
            }
          }
        }
      } while (!isValid);
    }
    aiShips = ships;
    return ships;
  }


  @Override
  public List<Coord> takeShots() {
    Random random = new Random();
    List<Coord> shots = new ArrayList<>();
    while (shots.size() < aiShips.size()) {
      int x = random.nextInt(aiBoard.length);
      int y = random.nextInt(aiBoard[0].length);
      Coord shot = new Coord(x, y);
      if (!shotsTaken.contains(shot)) {
        shots.add(shot);
        shotsTaken.add(shot);
      }
    }
    return shots;
  }



  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hits = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      if (aiBoard[shot.getVal(0)][shot.getVal(1)] != '0'
          && aiBoard[shot.getVal(0)][shot.getVal(1)] != 'M') {
        hits.add(shot);
      }
    }
    for (Coord shot : hits) {
      for (int i = 0; i < aiShips.size(); i++) {

        for (Coord curCoord : aiShips.get(i).getShipCoords()) {
          curCoord.doesShotHit(shot.getVal(0), shot.getVal(1));
        }
        if (aiShips.get(i).isShipSunk()) {
          aiShips.remove(i);
        }
      }
    }
    return hits;

  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    List<Coord> hits = shotsThatHitOpponentShips;
    for (Coord shot : hits) {
      System.out.println(shot.toString());
    }
  }

  @Override
  public void endGame(GameResult result, String reason) {
    System.out.println(result + reason);
  }
}
