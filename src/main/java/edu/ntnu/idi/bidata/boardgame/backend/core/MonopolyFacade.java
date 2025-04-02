package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.core.event.EventManager;
import edu.ntnu.idi.bidata.boardgame.backend.io.InputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.io.OutputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.model.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import java.util.List;

/**
 * The {@link MonopolyFacade} class serves as a facade for managing game setup and execution. It
 * simplifies interaction by providing high-level methods for UI layers.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.03.14
 */
public class MonopolyFacade {

  private final PlayerManager playerManager;
  private final GameEngine gameEngine;
  private final EventManager eventManager;

  /** Constructs a new BoardGame instance with required components. */
  public MonopolyFacade() {
    InputHandler inputHandler = new InputHandler();
    OutputHandler outputHandler = new OutputHandler();
    Board board = new Board();
    Dice dice = Dice.getInstance();
    this.playerManager = new PlayerManager(inputHandler, outputHandler, board);
    this.gameEngine = new GameEngine(outputHandler, board, dice);
    this.eventManager = new EventManager();
    List<Player> players = playerManager.initializePlayers();
    eventManager.addListener(() -> gameEngine.run(players));
  }

  /** Starts the game setup and execution. */
  public void start() {
    eventManager.update();
  }
}
