package edu.ntnu.idi.bidata.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class JsonPathUtilTest {

  @Test
  void testGeneratedUrlFromClass() {
    Path expectedTestPath = Path.of("src/test/resources/json/Player.json");
    Path expectedMainPath = Path.of("data/json/Player.json");
    assertEquals(expectedTestPath, JsonPathUtil.generateJsonPath(Player.class, true));
    assertEquals(expectedMainPath, JsonPathUtil.generateJsonPath(Player.class, false));
  }

  @Test
  void testGeneratedUrlFromClassInstance() {
    Player player = new Player("Player1", new Board(), "red");
    Path expectedTestPath = Path.of("src/test/resources/json/Player.json");
    Path expectedMainPath = Path.of("data/json/Player.json");
    assertEquals(expectedTestPath, JsonPathUtil.generateJsonPath(player, true));
    assertEquals(expectedMainPath, JsonPathUtil.generateJsonPath(player, false));
  }
}
