package edu.ntnu.idi.bidata.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.io.FileUtil;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class JsonPathUtilTest {

  @Test
  void testGeneratedUrlFromClass() {
    Path expectedTestPath = Path.of("src/test/resources/json/Player.json");
    Path expectedMainPath = Path.of("data/json/Player.json");
    assertEquals(
        expectedTestPath, FileUtil.generateFilePath(Player.class.getSimpleName(), "json", true));
    assertEquals(
        expectedMainPath, FileUtil.generateFilePath(Player.class.getSimpleName(), "json", false));
  }
}
