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
 * Wires the Snake & Ladder model to the JavaFX view.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class SnakeGameController extends Controller {

  public SnakeGameController(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      GameEngine<SnakeAndLadderTile, SnakeAndLadderPlayer> engine,
      SnakeAndLadderBoard board) {

    super(sceneSwitcher, createView(sceneSwitcher, eventBus, engine, board));
  }

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
