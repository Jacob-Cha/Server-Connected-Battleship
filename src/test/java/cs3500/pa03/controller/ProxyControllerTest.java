package cs3500.pa03.controller;



import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Player;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * Test class for proxy controller
 */
class ProxyControllerTest {
  private ProxyController proxyController;
  private ByteArrayOutputStream outputStream;

  @BeforeEach
  void setUp() throws Exception {
    List<String> messages = new ArrayList<>(Arrays.asList(
        "{\"method-name\":\"join\",\"arguments\":{}}",
        "{\"method-name\":\"setup\",\"arguments\":{\"width\":10,\"height\":10,\"fleet-spec\":"
            + "{\"CARRIER\":2,\"BATTLESHIP\":4,\"DESTROYER\":1,\"SUBMARINE\":3}}}",
        "{\"method-name\":\"take-shots\",\"arguments\":{}}",
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":"
            + "[{\"x\":0,\"y\":1},{\"x\":3,\"y\":2}]}}",
        "{\"method-name\":\"successful-hits\",\"arguments\":{\"coordinates\":"
            + "[{\"x\":0,\"y\":1},{\"x\":3,\"y\":2}]}}",
        "{\"method-name\":\"end-game\",\"arguments\":{\"result\":\"WIN\",\"reason\":\""
            + "Player 1 sank all of Player 2's ships\"}}"
    ));
    this.outputStream = new ByteArrayOutputStream();
    MockSocket mockSocket = new MockSocket(outputStream, messages);
    Player player = new AiPlayer();
    this.proxyController = new ProxyController(mockSocket, player);
  }

  @Test
  void run() {
    proxyController.run();

    assertTrue(outputStream.toString().trim().contains("dylanbenak"));
  }
}