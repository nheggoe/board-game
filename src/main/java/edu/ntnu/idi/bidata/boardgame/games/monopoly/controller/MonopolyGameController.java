package edu.ntnu.idi.bidata.boardgame.games.monopoly.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.EndDialog;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.view.MonopolyGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Nick Heggø
 * @version 2025.05.20
 */
public class MonopolyGameController extends Controller {

  public MonopolyGameController(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      GameEngine<MonopolyTile, MonopolyPlayer> gameEngine) {
    super(sceneSwitcher, createGameView(sceneSwitcher, eventBus, gameEngine));
  }

  private static MonopolyGameView createGameView(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      GameEngine<MonopolyTile, MonopolyPlayer> gameEngine) {
    return new MonopolyGameView(
        eventBus,
        gameEngine.getPlayers(),
        gameEngine.getTiles(),
        nextTurnEventHandler(sceneSwitcher, gameEngine));
  }

  private static EventHandler<ActionEvent> nextTurnEventHandler(
      SceneSwitcher sceneSwitcher, GameEngine<MonopolyTile, MonopolyPlayer> gameEngine) {
    return unused -> {
      gameEngine.nextTurn();
      if (gameEngine.isEnded()) {
        new EndDialog(sceneSwitcher).showAndWait();
      }
    };
  }
}
