package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.common.util.AlertFactory;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public class BoardGame extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    setup(primaryStage);
    new SceneSwitcher(primaryStage).switchTo(SceneSwitcher.SceneName.MAIN_VIEW);
  }

  private static void setup(Stage primaryStage) {
    primaryStage.setTitle("Board Game");
    primaryStage.setMinWidth(1200);
    primaryStage.setMinHeight(900);
    primaryStage.setOnCloseRequest(
        closeEvent -> {
          var result =
              AlertFactory.createAlert(
                      Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the game?")
                  .showAndWait();
          if (result.isEmpty() || !result.get().getButtonData().isDefaultButton()) {
            closeEvent.consume();
          }
        });
  }
}
