package edu.ntnu.idi.bidata.boardgame.games.snake.repository;

import edu.ntnu.idi.bidata.boardgame.common.io.json.JsonService;
import edu.ntnu.idi.bidata.boardgame.common.repository.JsonRepository;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderGame;
import java.util.UUID;
import java.util.function.Function;

public class JsonSALRepository extends JsonRepository<SnakeAndLadderGame> {
  protected JsonSALRepository(
      JsonService<SnakeAndLadderGame> jsonService, Function<SnakeAndLadderGame, UUID> idExtractor) {
    super(jsonService, idExtractor);
  }
}
