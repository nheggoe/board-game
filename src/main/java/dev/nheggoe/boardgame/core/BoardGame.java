package dev.nheggoe.boardgame.core;

import dev.nheggoe.boardgame.common.util.AlertFactory;
import dev.nheggoe.boardgame.core.ui.SceneSwitcher;
import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * The BoardGame class serves as the entry point for the application. It extends the JavaFX
 * Application class, providing the necessary functionality to initialize and start the user
 * interface for the board game. This class primarily sets up the main application window and
 * handles scene switching.
 *
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class BoardGame extends Application {

  /**
   * The main method serves as the entry point for the application. It initializes and launches the
   * JavaFX application, invoking the {@link Application#launch(String...)} method.
   *
   * @param args command-line arguments passed during the application start. These arguments are
   *     forwarded to the JavaFX application.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    setup(primaryStage);
    try {
      new SceneSwitcher(primaryStage).switchTo(SceneSwitcher.SceneName.MAIN_VIEW);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static void setup(Stage primaryStage) {
    primaryStage.setTitle("Board Game");
    primaryStage.setMinWidth(1200);
    primaryStage.setMinHeight(940);
    primaryStage.setOnCloseRequest(
        closeEvent -> {
          if (!isExitConfirmed()) {
            closeEvent.consume();
          }
        });
  }

  private static boolean isExitConfirmed() {
    var result =
        AlertFactory.createAlert(
                Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the game?")
            .showAndWait();
    return result.isPresent() && result.get().getButtonData().isDefaultButton();
  }
}
