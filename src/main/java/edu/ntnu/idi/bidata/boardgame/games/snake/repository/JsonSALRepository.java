package edu.ntnu.idi.bidata.boardgame.games.snake.repository;

import edu.ntnu.idi.bidata.boardgame.common.io.json.JsonService;
import edu.ntnu.idi.bidata.boardgame.common.repository.JsonRepository;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderGame;
import java.util.UUID;
import java.util.function.Function;

/**
 * Repository for storing and retrieving {@link SnakeAndLadderGame} instances using JSON
 * serialization.Extends the generic {@link JsonRepository} with type binding to SnakeAndLadderGame.
 *
 * @author Nick Heggø
 * @version 2025.05.21
 */
public class JsonSALRepository extends JsonRepository<SnakeAndLadderGame> {

  /**
   * Constructs a new JSON repository for SnakeAndLadderGame entities.
   *
   * @param jsonService the JSON service for serialization and deserialization
   * @param idExtractor function to extract a unique identifier from a game instance
   */
  protected JsonSALRepository(
      JsonService<SnakeAndLadderGame> jsonService, Function<SnakeAndLadderGame, UUID> idExtractor) {
    super(jsonService, idExtractor);
  }
}
