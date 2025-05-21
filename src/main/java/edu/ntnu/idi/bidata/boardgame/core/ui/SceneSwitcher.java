package edu.ntnu.idi.bidata.boardgame.core.ui;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.PlayerSetupController;
import edu.ntnu.idi.bidata.boardgame.common.ui.controller.MainController;
import edu.ntnu.idi.bidata.boardgame.common.util.GameFactory;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.controller.MonopolyGameController;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.controller.SnakeGameController;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class SceneSwitcher {

  private static final Logger LOGGER = Logger.getLogger(SceneSwitcher.class.getName());

  private final EventBus eventBus = new EventBus();
  private final Scene scene;
  private Controller controller;

  public SceneSwitcher(Stage primaryStage) {
    requireNonNull(primaryStage, "primaryStage must not be null");
    this.scene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.centerOnScreen();
  }

  public void switchTo(View.Name name) {
    if (controller != null) {
      try {
        controller.getView().close();
      } catch (Exception e) {
        LOGGER.severe(e::getMessage);
        LOGGER.severe(() -> "Failed to close controller: " + controller.getClass().getName());
      }
    }
    this.controller = createController(name);
    setRoot(controller.getView());
  }

  public void reset() {
    switch (controller) {
      case SnakeGameController s -> switchTo(View.Name.SNAKE_GAME_VIEW);
      case MonopolyGameController m -> switchTo(View.Name.MONOPOLY_GAME_VIEW);
      default -> throw new IllegalStateException("Unexpected value: " + controller);
    }
  }

  private Controller createController(View.Name name) {
    return switch (name) {
      case MAIN_VIEW -> createMainController();
      case SNAKE_GAME_VIEW -> createSnakeGameController();
      case PLAYER_SETUP_VIEW -> createPlayerSetupController();
      case MONOPOLY_GAME_VIEW -> createMonopolyGameController();
    };
  }

  private PlayerSetupController createPlayerSetupController() {
    return new PlayerSetupController(this, eventBus);
  }

  private MainController createMainController() {
    return new MainController(this);
  }

  private SnakeGameController createSnakeGameController() {
    var players =
        List.of(
            new SnakeAndLadderPlayer("Nick", Player.Figure.HAT),
            new SnakeAndLadderPlayer("Misha", Player.Figure.BATTLE_SHIP));
    var game = GameFactory.createSnakeGame(eventBus, players);
    return new SnakeGameController(this, eventBus, new GameEngine<>(game), game.getBoard());
  }

  private MonopolyGameController createMonopolyGameController() {
    var players =
        List.of(
            new MonopolyPlayer("Nick", Player.Figure.HAT),
            new MonopolyPlayer("Misha", Player.Figure.BATTLE_SHIP));
    var game = GameFactory.createMonopolyGame(eventBus, players);
    return new MonopolyGameController(this, eventBus, new GameEngine<>(game));
  }

  public Scene getScene() {
    return scene;
  }

  public void setRoot(Parent root) {
    scene.setRoot(requireNonNull(root, "root must not be null"));
  }
}
