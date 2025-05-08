package edu.ntnu.idi.bidata.boardgame.games.monopoly.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.Controller;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.view.MonopolyGameView;
import java.util.Objects;

public class GameController extends Controller {

  public GameController(SceneSwitcher sceneSwitcher, EventBus eventBus, GameEngine gameEngine) {
    super(sceneSwitcher);
    Objects.requireNonNull(gameEngine, "GameEngine cannot be null!");
    MonopolyGameView view =
        new MonopolyGameView(
            eventBus,
            gameEngine.getPlayers(),
            gameEngine.getTiles(),
            unused -> gameEngine.nextTurn());
    getSceneSwitcher().setRoot(view);
  }
}
