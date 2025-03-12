package edu.ntnu.idi.bidata.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class JsonWriterTest {

  private static final JsonWriter testJsonWriter = new JsonWriter(Player.class, true);

  @Test
  void testWriteFile() {
    Board board = new Board();
    var players =
        List.of(
            new Player("Player1", board, "red"),
            new Player("Player2", board, "blue"),
            new Player("Player3", board, "pink"));
    try {
      testJsonWriter.writeJsonFile(players);
    } catch (IOException e) {
      fail(e);
    }
  }
}
