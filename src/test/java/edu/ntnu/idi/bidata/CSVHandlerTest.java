package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.core.Board;
import edu.ntnu.idi.bidata.boardgame.backend.core.Player;
import edu.ntnu.idi.bidata.boardgame.backend.util.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.backend.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;

class CSVHandlerTest {

  private static final Path TEST_CSV_FILE_PATH = FileUtil.generateFilePath("test.csv", "csv", true);
  private CSVHandler csvHandler;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    csvHandler = new CSVHandler(TEST_CSV_FILE_PATH);
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(TEST_CSV_FILE_PATH);
  }

  @Test
  void testFileCreation() {
    File file = TEST_CSV_FILE_PATH.toFile();
    assertTrue(file.exists());
  }

  @Test
  void testSavePlayers() {
    List<Player> players =
        List.of(new Player("Misha", board, "Figure1"), new Player("Nick", board, "Figure2"));

    csvHandler.savePlayers(players.stream());

    try {
      List<String> lines = Files.readAllLines(TEST_CSV_FILE_PATH);
      assertEquals(3, lines.size());
      assertEquals("Name,Figure", lines.get(0));
      assertEquals("Misha,Figure1", lines.get(1));
      assertEquals("Nick,Figure2", lines.get(2));
    } catch (IOException e) {
      fail("Failed to read CSV file.");
    }
  }

  @Test
  void testLoadPlayers() {
    try {
      Files.write(TEST_CSV_FILE_PATH, List.of("Name,Figure", "Misha,Figure1", "Nick,Figure2"));
    } catch (IOException e) {
      fail("Failed to set up test CSV file.");
    }
    Stream<Player> playerStream = csvHandler.loadPlayers(board);
    List<Player> loadedPlayers = playerStream.toList();

    assertEquals(2, loadedPlayers.size());
    assertEquals("Misha", loadedPlayers.get(0).getName());
    assertEquals("Figure1", loadedPlayers.get(0).getFigure());
    assertEquals("Nick", loadedPlayers.get(1).getName());
    assertEquals("Figure2", loadedPlayers.get(1).getFigure());
  }
}
