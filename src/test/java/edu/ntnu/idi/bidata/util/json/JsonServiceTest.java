package edu.ntnu.idi.bidata.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.io.json.JsonService;
import edu.ntnu.idi.bidata.boardgame.backend.model.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  private static final JsonService<Player> testPlayerJsonService =
      new JsonService<>(Player.class, true);

  @BeforeAll
  static void setUp() {
    Board board = new Board();
    var players =
        Set.of(
            new Player("Player1", board, "red"),
            new Player("Player2", board, "blue"),
            new Player("Player3", board, "pink"));
    testPlayerJsonService.writeCollection(players);
  }

  @Test
  void testReadPlayers() {
    assertFalse(testPlayerJsonService.loadJsonAsStream().findAny().isEmpty());
    assertEquals(3, testPlayerJsonService.loadJsonAsStream().count());
  }
}
