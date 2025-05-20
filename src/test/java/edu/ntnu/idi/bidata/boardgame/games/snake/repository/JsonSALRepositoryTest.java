package edu.ntnu.idi.bidata.boardgame.games.snake.repository;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import edu.ntnu.idi.bidata.boardgame.common.io.json.JsonService;
import edu.ntnu.idi.bidata.boardgame.core.model.Game;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderGame;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JsonSALRepositoryTest {

  private JsonSALRepository jsonSALRepository;
  private SnakeAndLadderGame game;

  @BeforeEach
  void setUp() {
    JsonService<SnakeAndLadderGame> jsonService = new JsonService<>(SnakeAndLadderGame.class, true);
    jsonSALRepository = new JsonSALRepository(jsonService, Game::getId);

    var eventBus = new EventBus();
    var board =
        new SnakeAndLadderBoard(List.of(new NormalTile(), new NormalTile(), new SnakeTile(1)));
    var players = List.of(new SnakeAndLadderPlayer("Nick", Player.Figure.HAT));
    game = new SnakeAndLadderGame(eventBus, board, players);
  }

  @AfterEach
  void tearDown() {
    var file = FileUtil.generateFilePath("SnakeAndLadderGame", "json", true).toFile();
    file.delete();
    file.getParentFile().delete();
  }

  @Test
  void testAdd() {
    jsonSALRepository.add(game);
    assertThat(jsonSALRepository.getAll()).containsExactly(game);
  }

  @Test
  void testGetById() {

    // Add to repository
    jsonSALRepository.add(game);

    // Get by ID
    UUID gameId = game.getId();
    Optional<SnakeAndLadderGame> retrievedGame = jsonSALRepository.getById(gameId);

    // Verify
    assertThat(retrievedGame).isPresent();
    assertThat(retrievedGame.get()).isEqualTo(game);
  }

  @Test
  void testGetByIdWithNonExistentId() {
    // Try to get a game with a non-existent ID
    UUID nonExistentId = UUID.randomUUID();
    Optional<SnakeAndLadderGame> retrievedGame = jsonSALRepository.getById(nonExistentId);

    // Verify
    assertThat(retrievedGame).isEmpty();
  }

  @Test
  void testUpdate() {

    // Add to repository
    jsonSALRepository.add(game);

    // Update game state
    game.endGame();
    jsonSALRepository.update(game);

    // Get the updated game
    Optional<SnakeAndLadderGame> updatedGame = jsonSALRepository.getById(game.getId());

    // Verify
    assertThat(updatedGame).isPresent();
    assertThat(updatedGame.get().isEnded()).isTrue();
  }

  @Test
  void testRemove() {
    // Create a game

    // Add to repository
    jsonSALRepository.add(game);

    // Verify game is in repository
    assertThat(jsonSALRepository.getAll()).containsExactly(game);

    // Remove the game
    boolean removed = jsonSALRepository.remove(game.getId());

    // Verify game was removed
    assertThat(removed).isTrue();
    assertThat(jsonSALRepository.getAll()).isEmpty();
  }

  @Test
  void testSaveToSource() {

    // Add to repository
    jsonSALRepository.add(game);

    // Save to source
    Set<SnakeAndLadderGame> games = Set.of(game);
    jsonSALRepository.saveToSource(games);
  }

  @Test
  @Disabled()
  @DisplayName("Debugging Output")
  void testPrintJson() throws IOException {
    jsonSALRepository.saveToSource(Set.of(game));
    var file = FileUtil.generateFilePath("SnakeAndLadderGame", "json", true).toFile();
    try (var reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }
    System.out.println("File path: " + file);
  }
}
