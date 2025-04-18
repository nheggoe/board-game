package edu.ntnu.idi.bidata.boardgame.backend;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.core.PlayerManager;
import edu.ntnu.idi.bidata.boardgame.backend.core.event.EventManager;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardGameFactory;
import edu.ntnu.idi.bidata.boardgame.backend.model.game.Game;

/**
 * The {@link MonopolyFacade} class serves as a facade for managing game setup and execution. It
 * simplifies interaction by providing high-level methods for UI layers.
 *
 * @author Nick Heggø and Mihailo Hranisavljevic
 * @version 2025.04.15
 */
public class MonopolyFacade {

  private final EventManager eventManager;

  /** Constructs a new BoardGame instance with required components. */
  public MonopolyFacade() {
    this.eventManager = new EventManager();
    GameEngine engine = GameEngine.getInstance();
    engine.setup(
        new Game(
            BoardGameFactory.generateBoard(BoardGameFactory.Layout.UNFAIR),
            new PlayerManager().initializePlayers()));
    eventManager.addListener(engine::run);
  }

  /** Starts the game setup and execution. */
  public void start() {
    eventManager.update();
  }
}
