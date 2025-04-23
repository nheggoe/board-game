package edu.ntnu.idi.bidata.boardgame.frontend;

import edu.ntnu.idi.bidata.boardgame.frontend.view.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class MonopolyApp extends Application {

  private static final SceneSwitcher sceneSwitcher = new SceneSwitcher();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    setup(primaryStage);
    switchScene(SceneSwitcher.AppScene.GAME_VIEW);
  }

  private static void setup(Stage primaryStage) {
    primaryStage.setTitle("Board Game");
    primaryStage.centerOnScreen();
    primaryStage.setMinWidth(800);
    primaryStage.setMinHeight(600);
    sceneSwitcher.setup(primaryStage);
  }

  public static void switchScene(SceneSwitcher.AppScene scene) {
    sceneSwitcher.switchTo(scene);
  }
}
