package edu.ntnu.idi.bidata.boardgame.games.snake.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.model.Tile;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.view.SnakeGameView;

public class SnakeGameController extends Controller {
  public <P extends Player, T extends Tile> SnakeGameController(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      GameEngine<P, T> gameEngine,
      SnakeAndLadderBoard board) {
    super(sceneSwitcher, new SnakeGameView(eventBus, board));
  }
}
