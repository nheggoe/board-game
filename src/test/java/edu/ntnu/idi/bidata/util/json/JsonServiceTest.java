package edu.ntnu.idi.bidata.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  private static final JsonService testPlayerJsonService = new JsonService(Player.class, true);

  @BeforeAll
  static void setUp() throws IOException {
    Board board = new Board();
    var players =
        Stream.of(
            new Player("Player1", board, "red"),
            new Player("Player2", board, "blue"),
            new Player("Player3", board, "pink"));
    testPlayerJsonService.writeCollection(players);
  }

  @Test
  void testReadPlayers() {
    try {
      assertFalse(testPlayerJsonService.loadJsonAsStream().findAny().isEmpty());
      assertEquals(3, testPlayerJsonService.loadJsonAsStream().count());
    } catch (IOException e) {
      fail(e);
    }
  }
}
