package edu.ntnu.idi.bidata.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  static final JsonService testPlayerJsonService = new JsonService(Player.class, true);

  @BeforeAll
  static void setUp() throws IOException {
    Board board = new Board();
    var players =
        List.of(
            new Player("Player1", board, "red"),
            new Player("Player2", board, "blue"),
            new Player("Player3", board, "pink"));
    testPlayerJsonService.writeCollection(players);
  }

  @Test
  @Disabled("TODO")
  void testReadPlayers() {
    List<Player> players;
    try {
      players = testPlayerJsonService.loadCollection();
      assertFalse(players.isEmpty());
      assertEquals(3, players.size());
    } catch (IOException e) {
      fail(e);
    }
  }
}
