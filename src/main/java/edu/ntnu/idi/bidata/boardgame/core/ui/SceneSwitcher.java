package edu.ntnu.idi.bidata.boardgame.core.ui;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.controller.MonopolyGameController;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoardFactory;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.controller.SnakeGameController;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderGame;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.List;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class SceneSwitcher {

  private final EventBus eventBus = new EventBus();
  private final Scene scene;
  private Controller controller;

  public SceneSwitcher(Stage primaryStage) {
    Objects.requireNonNull(primaryStage, "primaryStage must not be null");
    this.scene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void switchTo(View.Name name) {
    if (controller != null) {
      try {
        controller.getView().close();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    this.controller = createController(name);
    setRoot(controller.getView());
  }

  private Controller createController(View.Name name) {
    return switch (name) {
      case MAIN_VIEW -> throw new UnsupportedOperationException("Not yet implemented");
      case SNAKE_GAME_VIEW -> {
        var tiles =
            List.<SnakeAndLadderTile>of(
                new LadderTile(2),
                new NormalTile(),
                new NormalTile(),
                new NormalTile(),
                new SnakeTile(2));
        var board = new SnakeAndLadderBoard(tiles);
        var players =
            List.of(
                new SnakeAndLadderPlayer("Nick", Player.Figure.HAT),
                new SnakeAndLadderPlayer("Misha", Player.Figure.BATTLE_SHIP));
        var game = new SnakeAndLadderGame(eventBus, board, players);
        yield new SnakeGameController(this, eventBus, new GameEngine<>(game), board);
      }
      case MONOPOLY_GAME_VIEW -> {
        var game =
            new MonopolyGame(
                eventBus,
                MonopolyBoardFactory.generateBoard(MonopolyBoardFactory.Layout.NORMAL),
                List.of(
                    new MonopolyPlayer("Nick", Player.Figure.HAT),
                    new MonopolyPlayer("Misha", Player.Figure.BATTLE_SHIP)));
        yield new MonopolyGameController(this, eventBus, new GameEngine<>(game));
      }
    };
  }

  public Scene getScene() {
    return scene;
  }

  public void setRoot(Parent root) {
    scene.setRoot(Objects.requireNonNull(root, "root must not be null"));
  }
}
