package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the Actual player for the BattleSalvo game
 */
public class ConsolePlayer implements Player {
  List<Ship> playerShips;
  char[][] playerBoard;
  PlayerModel pm;

  public ConsolePlayer(PlayerModel pm) {
    this.pm = pm;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> ships = new ArrayList<>();
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        ships.add(new Ship(entry.getKey()));
      }
    }
    playerShips = ships;
    return ships;
  }

  @Override
  public List<Coord> takeShots() {
    return pm.accumulateShots();
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return null;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  @Override
  public void endGame(GameResult result, String reason) {

  }
}
