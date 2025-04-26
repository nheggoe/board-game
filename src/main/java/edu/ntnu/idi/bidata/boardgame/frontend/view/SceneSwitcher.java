package edu.ntnu.idi.bidata.boardgame.frontend.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {

  private Stage primaryStage;

  public enum AppScene {
    MAIN_MENU,
    GAME_VIEW
  }

  public void setup(Stage primaryStage) {
    if (primaryStage == null) {
      throw new IllegalArgumentException("primaryStage must not be null");
    }
    this.primaryStage = primaryStage;
  }

  public void switchTo(AppScene scene) {
    Scene oldScene = primaryStage.getScene();
    double width = (oldScene != null) ? oldScene.getWidth() : primaryStage.getWidth();
    double height = (oldScene != null) ? oldScene.getHeight() : primaryStage.getHeight();
    primaryStage.setScene(createScene(scene, width, height));
    primaryStage.show();
  }

  private Scene createScene(AppScene scene, double width, double height) {
    return switch (scene) {
      case GAME_VIEW -> new GameView(width, height);
      case MAIN_MENU -> throw new UnsupportedOperationException("Not yet implemented");
    };
  }
}
