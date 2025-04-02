package edu.ntnu.idi.bidata.boardgame.frontend.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {

  private final Stage primaryStage;

  public enum AppScene {
    MAIN_MENU,
    GAME_VIEW,
    DEMO_VIEW;
  }

  public SceneSwitcher(Stage primaryStage) {
    if (primaryStage == null) {
      throw new IllegalArgumentException("primaryStage must not be null");
    }
    this.primaryStage = primaryStage;
  }

  public void switchTo(AppScene scene) {
    primaryStage.setScene(createScene(scene));
    primaryStage.show();
  }

  private Scene createScene(AppScene scene) {
    return switch (scene) {
      case MAIN_MENU, GAME_VIEW -> throw new UnsupportedOperationException("Not yet implemented");
      case DEMO_VIEW -> new DemoView();
    };
  }
}
