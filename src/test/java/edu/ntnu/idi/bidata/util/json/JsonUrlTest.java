package edu.ntnu.idi.bidata.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class JsonUrlTest {

  @Test
  void testGeneratedUrlFromClass() {
    Path expectedTestPath = Path.of("src/test/resources/json/Player.json");
    Path expectedMainPath = Path.of("src/main/resources/json/Player.json");
    assertEquals(expectedTestPath, JsonUrl.getJsonFilePath(Player.class, true));
    assertEquals(expectedMainPath, JsonUrl.getJsonFilePath(Player.class, false));
  }

  @Test
  void testGeneratedUrlFromClassInstance() {
    Player player = new Player("Player1", new Board(), "red");
    Path expectedTestPath = Path.of("src/test/resources/json/Player.json");
    Path expectedMainPath = Path.of("src/main/resources/json/Player.json");
    assertEquals(expectedTestPath, JsonUrl.getJsonFilePath(player, true));
    assertEquals(expectedMainPath, JsonUrl.getJsonFilePath(player, false));
  }
}
