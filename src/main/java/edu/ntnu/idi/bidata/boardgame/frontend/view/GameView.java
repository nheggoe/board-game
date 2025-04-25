package edu.ntnu.idi.bidata.boardgame.frontend.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameView extends Scene {

  public GameView(double width, double height) {
    super(new Pane(), width, height);
    var root = new BorderPane();
    var center = new StackPane();
    setRoot(root);

    root.setCenter(center);
    root.setRight(new UIPane());
    root.setBottom(new MessageLog());

    center.getChildren().add(new GamePane());
    center.getChildren().add(new DiceView(2));
  }
}
