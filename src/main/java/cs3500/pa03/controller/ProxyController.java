package cs3500.pa03.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.json.CoordAdapter;
import cs3500.pa03.json.FleetJson;
import cs3500.pa03.json.JoinArguments;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;
import cs3500.pa03.json.ShipAdapter;
import cs3500.pa03.json.VolleyJson;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProxyController class that helps deal with connecting to the server
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Constructor for the proxy controller
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }


  /**
   * Runs the JSON classes in order to run with the server
   */
  public void run() {

    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
    }
  }


  /**
   * Determines the type of request the server has sent and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    System.out.println("Setup complete");
    System.out.println("Processing message: " + message);
    String name = message.messageName();
    JsonNode arguments = message.arguments();


    switch (name) {
      case "join" -> {
        handleJoin();
      }
      case "setup" -> {
        handleSetup(arguments);
      }
      case "take-shots" -> {
        handleTakeShots(arguments);
      }
      case "report-damage" -> {
        handleReportDamage(arguments);
      }
      case "successful-hits" -> {
        handleSuccessfulHits(arguments);
      }
      case "end-game" -> {
        handleEndGame();
      }
      default -> System.out.println("debug test");



    }
  }

  private void handleEndGame() {
    MessageJson endGameMessage = new MessageJson("end-game", null);
    JsonNode jsonResponse = JsonUtils.serializeRecord(endGameMessage);
    this.out.println(jsonResponse.toString());
  }


  private void handleJoin() {
    JoinArguments joinArgs = new JoinArguments("dylanbenak", "SINGLE");

    JsonNode jsonArgs = JsonUtils.serializeRecord(joinArgs);

    MessageJson response = new MessageJson("join", jsonArgs);

    JsonNode jsonResponse = JsonUtils.serializeRecord(response);


    this.out.println(jsonResponse.toString());


  }

  private void handleSuccessfulHits(JsonNode arguments) {
    JsonNode hits = arguments.get("coordinates");

    List<Coord> shotsThatHitOpponentShips = new ArrayList<>();
    for (JsonNode hit : hits) {
      int x = hit.get("x").asInt();
      int y = hit.get("y").asInt();
      shotsThatHitOpponentShips.add(new Coord(y, x));
    }

    this.player.successfulHits(shotsThatHitOpponentShips);

    MessageJson successfulHitsJson = new MessageJson("successful-hits", null);
    JsonNode jsonResponse = JsonUtils.serializeRecord(successfulHitsJson);
    this.out.println(jsonResponse.toString());
  }








  private void handleSetup(JsonNode arguments) {

    int width = arguments.get("width").asInt();
    int height = arguments.get("height").asInt();

    Map<ShipType, Integer> fleetSpec = new HashMap<>();
    JsonNode fleetSpecNode = arguments.get("fleet-spec");
    fleetSpecNode.fieldNames().forEachRemaining(name -> {
      fleetSpec.put(ShipType.valueOf(name), fleetSpecNode.get(name).asInt());
    });

    List<Ship> fleet = this.player.setup(height, width, fleetSpec);
    List<ShipAdapter> fleetAdapter = new ArrayList<>();
    for (Ship ship : fleet) {
      fleetAdapter.add(new ShipAdapter(new CoordAdapter(ship.getShipCoords().get(0)),
          ship.getSize(), ship.getDirection()));

    }


    FleetJson shipsJson = new FleetJson(fleetAdapter);

    JsonNode serializedShips = JsonUtils.serializeRecord(shipsJson);

    MessageJson setupJson = new MessageJson("setup", serializedShips);
    JsonNode jsonResponse = JsonUtils.serializeRecord(setupJson);
    this.out.println(jsonResponse.toString());
  }

  private void handleTakeShots(JsonNode arguments) {

    List<Coord> shots = this.player.takeShots();


    List<CoordAdapter> shotsAdapter = new ArrayList<>();
    for (Coord coord : shots) {
      shotsAdapter.add(new CoordAdapter(coord.getY(), coord.getX()));

    }
    VolleyJson response = new VolleyJson(shotsAdapter);

    JsonNode jsonVolley = JsonUtils.serializeRecord(response);

    MessageJson shotJson = new MessageJson("take-shots", jsonVolley);
    JsonNode jsonResponse = JsonUtils.serializeRecord(shotJson);
    this.out.println(jsonResponse.toString());

  }

  private void handleReportDamage(JsonNode arguments) {
    JsonNode hits = arguments.get("coordinates");

    List<Coord> opponentShotsOnBoard = new ArrayList<>();
    for (JsonNode hit : hits) {
      int x = hit.get("x").asInt();
      int y = hit.get("y").asInt();
      opponentShotsOnBoard.add(new Coord(y, x));
    }

    List<Coord> damage = this.player.reportDamage(opponentShotsOnBoard);



    List<CoordAdapter> shotsAdapter = new ArrayList<>();
    for (Coord coord : damage) {
      shotsAdapter.add(new CoordAdapter(coord.getY(), coord.getX()));
    }
    VolleyJson response = new VolleyJson(shotsAdapter);

    JsonNode jsonVolley = JsonUtils.serializeRecord(response);
    MessageJson shotJson = new MessageJson("report-damage", jsonVolley);
    JsonNode jsonResponse = JsonUtils.serializeRecord(shotJson);
    this.out.println(jsonResponse.toString());
  }

}
