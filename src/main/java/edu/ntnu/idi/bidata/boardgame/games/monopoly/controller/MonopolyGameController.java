package edu.ntnu.idi.bidata.boardgame.games.monopoly.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.EndDialog;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.view.MonopolyGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * MonopolyGameController is responsible for orchestrating the gameplay flow of a Monopoly game
 * within the application's architecture. It acts as a concrete implementation of the abstract
 * {@link Controller} class, integrating the game logic, view updates, and scene transitions for the
 * Monopoly game.
 *
 * <p>The controller manages the game's lifecycle, including handling player turns and determining
 * when the game has ended. Upon game completion, a dialog is displayed to notify players.
 *
 * <p>The MonopolyGameController constructs its associated view, {@link MonopolyGameView}, by
 * providing the necessary components such as the game engine, player data, and tile data. It also
 * defines behavior-specific event handling for progressing the game state.
 */
public class MonopolyGameController extends Controller {

  /**
   * Constructs a new instance of the MonopolyGameController.
   *
   * @param sceneSwitcher the {@link SceneSwitcher} responsible for managing scene transitions in
   *     the application; must not be null
   * @param eventBus the {@link EventBus} used for handling and dispatching events across different
   *     components; must not be null
   * @param gameEngine the {@link GameEngine} managing the core logic for the Monopoly game,
   *     including tiles and players; must not be null
   * @throws NullPointerException if any of the provided arguments are null
   */
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
        sceneSwitcher,
        eventBus,
        gameEngine::getTiles,
        gameEngine::getPlayers,
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
