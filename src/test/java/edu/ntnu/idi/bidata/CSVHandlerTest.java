package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import edu.ntnu.idi.bidata.util.CSVHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;

class CSVHandlerTest {

  private static final String TEST_CSV_FILE = "test_players.csv";
  private CSVHandler csvHandler;
  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    csvHandler = new CSVHandler(TEST_CSV_FILE);
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(new File(TEST_CSV_FILE).toPath());
  }

  @Test
  void testFileCreation() {
    File file = new File(TEST_CSV_FILE);
    assertTrue(file.exists());
  }

  @Test
  void testSavePlayers() {
    List<Player> players =
        List.of(new Player("Misha", board, "Figure1"), new Player("Nick", board, "Figure2"));

    csvHandler.savePlayers(players.stream());

    try {
      List<String> lines = Files.readAllLines(new File(TEST_CSV_FILE).toPath());
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
      Files.write(
          new File(TEST_CSV_FILE).toPath(),
          List.of("Name,Figure", "Misha,Figure1", "Nick,Figure2"));
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
