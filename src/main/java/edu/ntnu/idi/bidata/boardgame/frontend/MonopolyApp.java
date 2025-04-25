package edu.ntnu.idi.bidata.boardgame.frontend;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;
import edu.ntnu.idi.bidata.boardgame.frontend.view.SceneSwitcher;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

public class MonopolyApp extends Application {

  private static final SceneSwitcher sceneSwitcher = new SceneSwitcher();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    // temporary solution, currently we are skipping setups and goes straight to GameView
    var game =
        new Game(
            BoardFactory.generateBoard(BoardFactory.Layout.NORMAL),
            List.of(
                new Player("Nick", Player.Figure.HAT),
                new Player("Misha", Player.Figure.BATTLE_SHIP)));
    GameEngine.getInstance().setup(game);
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
