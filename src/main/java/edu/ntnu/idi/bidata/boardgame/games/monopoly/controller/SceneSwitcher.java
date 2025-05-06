package edu.ntnu.idi.bidata.boardgame.games.monopoly.controller;

import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.TurnManager;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Game;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.BoardFactory;
import java.util.List;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneSwitcher {

  private final Scene scene;
  private Controller controller;

  public SceneSwitcher(Stage primaryStage) {
    Objects.requireNonNull(primaryStage, "primaryStage must not be null");
    this.scene = new Scene(new Pane());
    primaryStage.setScene(scene);
    switchTo(View.GAME_VIEW);
    primaryStage.show();
  }

  public void switchTo(View view) {
    createScene(view);
  }

  public void setRoot(Parent root) {
    scene.setRoot(Objects.requireNonNull(root, "root must not be null"));
  }

  private void createScene(View view) {
    switch (view) {
      case GAME_VIEW -> {
        var game =
            new Game(
                BoardFactory.generateBoard(BoardFactory.Layout.NORMAL),
                List.of(
                    new Player("Nick", Player.Figure.HAT),
                    new Player("Misha", Player.Figure.BATTLE_SHIP)));
        controller =
            new GameController(this, new GameEngine(game, new TurnManager(game.getPlayerIds())));
      }
      case MAIN_MENU -> throw new UnsupportedOperationException("Not yet implemented");
    }
  }

  public enum View {
    MAIN_MENU,
    GAME_VIEW
  }
}
