package edu.ntnu.idi.bidata.boardgame.games.monopoly.controller;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.frontend.view.GameView;
import java.util.Objects;

public class GameController extends Controller {

  private final GameEngine gameEngine;
  private final GameView view;

  public GameController(SceneSwitcher sceneSwitcher, GameEngine gameEngine) {
    super(sceneSwitcher);
    this.gameEngine = Objects.requireNonNull(gameEngine, "GameEngine cannot be null!");
    this.view =
        new GameView(
            gameEngine.getPlayers(), gameEngine.getTiles(), unused -> gameEngine.nextTurn());
    getSceneSwitcher().setRoot(view);
    gameEngine.addListener(view);
  }

  @Override
  public void close() {
    gameEngine.removeListener(view);
  }
}
