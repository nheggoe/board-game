package edu.ntnu.idi.bidata.boardgame.games.snake.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.PlayerRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.view.SnakeGameView;
import java.util.List;
import javafx.application.Platform;

/**
 * Wires the Snake & Ladder model to the JavaFX view.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class SnakeGameController extends Controller {

  private final List<SnakeAndLadderPlayer> players;
  private final PlayerRender playerRender;

  public SnakeGameController(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      GameEngine<SnakeAndLadderTile, SnakeAndLadderPlayer> engine,
      SnakeAndLadderBoard board) {

    /* Initialise superclass with the prepared view */
    super(sceneSwitcher, new SnakeGameView(eventBus, board));
    this.players = engine.getPlayers();

    var view = (SnakeGameView) getView();
    this.playerRender = view.getPlayerRender();

    /* Keep GUI in sync with the model */
    eventBus.addListener(
        PlayerMovedEvent.class,
        new EventListener() {
          @Override
          public void onEvent(Event event) {
            Platform.runLater(() -> playerRender.renderPlayers(players));
          }

          @Override
          public void close() {
            /* nothing to release */
          }
        });

    /* User action: roll dice -> advance game */
    view.getRollDiceButton().setOnAction(__ -> engine.nextTurn());

    /* Initial render (before the first move) */
    playerRender.renderPlayers(players);
  }
}
