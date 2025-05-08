package edu.ntnu.idi.bidata.boardgame.games.monopoly.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.view.MonopolyGameView;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class GameController extends Controller {

  public GameController(SceneSwitcher sceneSwitcher, EventBus eventBus, GameEngine gameEngine) {
    super(
        sceneSwitcher,
        new MonopolyGameView(
            eventBus,
            gameEngine.getPlayers(),
            gameEngine.getTiles(),
            unused -> gameEngine.nextTurn()));
  }
}
