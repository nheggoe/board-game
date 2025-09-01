package dev.nheggoe.boardgame.games.monopoly.repository;

import dev.nheggoe.boardgame.common.io.json.JsonService;
import dev.nheggoe.boardgame.common.repository.JsonRepository;
import dev.nheggoe.boardgame.games.monopoly.model.MonopolyGame;

/**
 * A repository for persisting and managing MonopolyGame entities in a JSON-based storage medium.
 *
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class JsonMonopolyGameRepository extends JsonRepository<MonopolyGame> {

  /**
   * Default constructor for the JsonMonopolyGameRepository class. Initializes the repository with a
   * JSON service configured for {@link MonopolyGame} entities and a unique identifier extractor
   * function.
   *
   * <p>This constructor leverages the parent class, {@link JsonRepository}, to enable JSON-based
   * persistence for MonopolyGame entities. By default, it sets up the repository to use JSON files
   * for storage and retrieval of data.
   *
   * @throws NullPointerException if either the internal {@link JsonService} or the identifier
   *     extractor function is null
   */
  public JsonMonopolyGameRepository() {
    super(new JsonService<>(MonopolyGame.class), MonopolyGame::getId);
  }
}
