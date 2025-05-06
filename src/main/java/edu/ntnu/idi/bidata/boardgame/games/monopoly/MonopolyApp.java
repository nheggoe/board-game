package edu.ntnu.idi.bidata.boardgame.games.monopoly;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.controller.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class MonopolyApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    setup(primaryStage);
    new SceneSwitcher(primaryStage);
  }

  private static void setup(Stage primaryStage) {
    primaryStage.setTitle("Board Game");
    primaryStage.centerOnScreen();
    primaryStage.setMinWidth(800);
    primaryStage.setMinHeight(600);
  }
}
