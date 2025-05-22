package edu.ntnu.idi.bidata.boardgame.core.ui;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.io.FileUtil;
import edu.ntnu.idi.bidata.boardgame.common.io.csv.CSVHandler;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.PlayerSetupController;
import edu.ntnu.idi.bidata.boardgame.common.ui.controller.MainController;
import edu.ntnu.idi.bidata.boardgame.common.util.AlertFactory;
import edu.ntnu.idi.bidata.boardgame.common.util.GameFactory;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.PlayerManager;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.controller.MonopolyGameController;
import edu.ntnu.idi.bidata.boardgame.games.snake.controller.SnakeGameController;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

  public enum SceneName {
    MAIN_VIEW,
    PLAYER_SETUP_VIEW,
    MONOPOLY_GAME_VIEW,
    SNAKE_GAME_VIEW,
  }

  public void switchTo(SceneName name) {
    if (controller != null) {
      try {
        controller.getView().close();
      } catch (Exception e) {
        LOGGER.severe(e::getMessage);
        LOGGER.severe(() -> "Failed to close controller: " + controller.getClass().getName());
      }
    }
    try {
      this.controller = createController(name);
      setRoot(controller.getView());
    } catch (IOException e) {
      LOGGER.severe(e::getMessage);
      LOGGER.severe(() -> "Failed to create controller: " + controller.getClass().getName());
      AlertFactory.createAlert(Alert.AlertType.ERROR, "Failed to create controller: " + name)
          .showAndWait();
    }
  }

  public void reset() {
    switch (controller) {
      case SnakeGameController s -> switchTo(SceneName.SNAKE_GAME_VIEW);
      case MonopolyGameController m -> switchTo(SceneName.MONOPOLY_GAME_VIEW);
      default -> throw new IllegalStateException("Unexpected value: " + controller);
    }
  }

  private Controller createController(SceneName name) throws IOException {
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

  private SnakeGameController createSnakeGameController() throws IOException {
    var csvFile = FileUtil.generateFilePath("players", "csv");
    var playerManager = new PlayerManager(eventBus, new CSVHandler(csvFile));
    var game = GameFactory.createSnakeGame(eventBus, playerManager);
    return new SnakeGameController(this, eventBus, new GameEngine<>(game));
  }

  private MonopolyGameController createMonopolyGameController() throws IOException {
    var csvFile = FileUtil.generateFilePath("players", "csv");
    var playerManager = new PlayerManager(eventBus, new CSVHandler(csvFile));
    var game = GameFactory.createMonopolyGame(eventBus, playerManager);
    return new MonopolyGameController(this, eventBus, new GameEngine<>(game));
  }

  public javafx.scene.Scene getScene() {
    return scene;
  }

  public void setRoot(Parent root) {
    scene.setRoot(requireNonNull(root, "root must not be null"));
  }
}
