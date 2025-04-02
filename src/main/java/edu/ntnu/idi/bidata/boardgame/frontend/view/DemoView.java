package edu.ntnu.idi.bidata.boardgame.frontend.view;

import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

public class DemoView extends Scene {

  public DemoView() {
    super(createContent());
  }

  private static FlowPane createContent() {
    return new FlowPane();
  }
}
