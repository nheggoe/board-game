package edu.ntnu.idi.bidata.util.json;

import edu.ntnu.idi.bidata.boardgame.backend.core.Board;
import edu.ntnu.idi.bidata.boardgame.backend.core.Player;
import edu.ntnu.idi.bidata.boardgame.backend.util.json.JsonWriter;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JsonWriterTest {

  private static final JsonWriter testJsonWriter = new JsonWriter(Player.class, true);

  @Test
  void testWriteFile() {
    Board board = new Board();
    var players =
        Set.of(
            new Player("Player1", board, "red"),
            new Player("Player2", board, "blue"),
            new Player("Player3", board, "pink"));
    testJsonWriter.writeJsonFile(players);
  }
}
