package edu.ntnu.idi.bidata.boardgame.core.ui;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.TurnManager;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.controller.GameController;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoardFactory;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
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
    scene.setRoot(controller.getView());
  }

  public Scene getScene() {
    return scene;
  }

  public void setRoot(Parent root) {
    scene.setRoot(Objects.requireNonNull(root, "root must not be null"));
  }

  private Controller createController(View.Name name) {
    return switch (name) {
      case GAME_VIEW -> {
        var game =
            new MonopolyGame(
                eventBus,
                MonopolyBoardFactory.generateBoard(MonopolyBoardFactory.Layout.NORMAL),
                List.of(
                    new MonopolyPlayer("Nick", Player.Figure.HAT),
                    new MonopolyPlayer("Misha", Player.Figure.BATTLE_SHIP)));
        yield new GameController(
            this, eventBus, new GameEngine(game, new TurnManager(eventBus, game.getPlayerIds())));
      }
      case MAIN_VIEW -> throw new UnsupportedOperationException("Not yet implemented");
    };
  }
}
