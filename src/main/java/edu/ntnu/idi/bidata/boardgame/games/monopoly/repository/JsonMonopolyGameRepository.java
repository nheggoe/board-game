package edu.ntnu.idi.bidata.boardgame.games.monopoly.repository;

import edu.ntnu.idi.bidata.boardgame.common.repository.JsonRepository;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;

/**
 * A repository for persisting and managing MonopolyGame entities in a JSON-based storage medium.
 *
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class JsonMonopolyGameRepository extends JsonRepository<MonopolyGame> {

  public JsonMonopolyGameRepository() {
    super(MonopolyGame.class, MonopolyGame::getId);
  }
}
