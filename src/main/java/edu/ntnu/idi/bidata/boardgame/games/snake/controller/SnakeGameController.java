package edu.ntnu.idi.bidata.boardgame.games.snake.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.EndDialog;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.view.SnakeGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Controller that connects the Snake and Ladder game model to the JavaFX view.
 *
 * <p>It initialises the {@link SnakeGameView}, wires user actions to the {@link GameEngine}, and
 * triggers the end-game dialogue when appropriate.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.21
 */
public class SnakeGameController extends Controller {

  /**
   * Constructs a new SnakeGameController and sets up the game view.
   *
   * @param sceneSwitcher global scene switcher
   * @param eventBus event bus for event publishing
   * @param engine the core game engine
   * @param board the game board model
   */
  public SnakeGameController(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      GameEngine<SnakeAndLadderTile, SnakeAndLadderPlayer> engine,
      SnakeAndLadderBoard board) {

    super(sceneSwitcher, createView(sceneSwitcher, eventBus, engine, board));
  }

  /**
   * Constructs and returns the JavaFX view for the game.
   *
   * @param sceneSwitcher scene switcher for navigation
   * @param eventBus event bus for communication
   * @param engine the core game engine
   * @param board the board model
   * @return fully constructed game view
   */
  private static SnakeGameView createView(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      GameEngine<SnakeAndLadderTile, SnakeAndLadderPlayer> engine,
      SnakeAndLadderBoard board) {
    return new SnakeGameView(
        sceneSwitcher,
        eventBus,
        engine::getTiles,
        engine::getPlayers,
        nextTurnEventHandler(sceneSwitcher, engine));
  }

  /**
   * Creates the event handler responsible for rolling the dice and checking for the game end.
   *
   * @param sceneSwitcher scene switcher for UI transitions
   * @param gameEngine the game engine to invoke next turns
   * @return JavaFX action event handler
   */
  private static EventHandler<ActionEvent> nextTurnEventHandler(
      SceneSwitcher sceneSwitcher,
      GameEngine<SnakeAndLadderTile, SnakeAndLadderPlayer> gameEngine) {
    return unused -> {
      gameEngine.nextTurn();
      if (gameEngine.isEnded()) {
        new EndDialog(sceneSwitcher).showAndWait();
      }
    };
  }
}
