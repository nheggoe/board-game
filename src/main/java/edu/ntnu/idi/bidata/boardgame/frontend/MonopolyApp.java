package edu.ntnu.idi.bidata.boardgame.frontend;

import edu.ntnu.idi.bidata.boardgame.frontend.view.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class MonopolyApp extends Application {

  private SceneSwitcher sceneSwitcher;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    SceneSwitcher sceneSwitcher = new SceneSwitcher(primaryStage);
    sceneSwitcher.switchTo(SceneSwitcher.AppScene.DEMO_VIEW);
  }

  public SceneSwitcher getSceneSwitcher() {
    return sceneSwitcher;
  }
}
