package edu.ntnu.idi.bidata.boardgame.core.ui;

import javafx.application.Application;
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
    new SceneSwitcher(primaryStage).switchTo(View.Name.MAIN_VIEW);
  }

  private static void setup(Stage primaryStage) {
    primaryStage.setTitle("Board Game");
    primaryStage.centerOnScreen();
    primaryStage.setMinWidth(1200);
    primaryStage.setMinHeight(900);
  }
}
