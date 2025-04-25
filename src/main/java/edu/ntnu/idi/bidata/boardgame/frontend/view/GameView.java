package edu.ntnu.idi.bidata.boardgame.frontend.view;


import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameView extends Scene {

  public GameView(double width, double height) {
    super(new Pane(), width, height);
    setRoot(new GamePane());
  }

}
