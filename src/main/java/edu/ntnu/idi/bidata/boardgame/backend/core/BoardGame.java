package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.io.InputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.io.OutputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.model.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import java.util.List;

/**
 * The {@code BoardGame} class serves as a facade for managing game setup and execution. It
 * simplifies interaction by providing high-level methods for UI layers.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.03.14
 */
public class BoardGame {

  private final PlayerManager playerManager;
  private final GameEngine gameEngine;

  /** Constructs a new BoardGame instance with required components. */
  public BoardGame() {
    InputHandler inputHandler = new InputHandler();
    OutputHandler outputHandler = new OutputHandler();
    Board board = new Board();
    Dice dice = Dice.getInstance();
    this.playerManager = new PlayerManager(inputHandler, outputHandler, board);
    this.gameEngine = new GameEngine(outputHandler, board, dice);
  }

  /** Starts the game setup and execution. */
  public void start() {
    List<Player> players = playerManager.initializePlayers();
    gameEngine.run(players);
  }
}
