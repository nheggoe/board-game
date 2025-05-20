package edu.ntnu.idi.bidata.boardgame.common.ui.view;

import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PlayerSetupView extends View {

  public PlayerSetupView() {
    super();
    var root = new BorderPane();
    setRoot(root);
  }

  private VBox createCenterPane() {
    var center = new VBox();
    center.setAlignment(Pos.CENTER);
    center.setSpacing(10);

    return center;
  }
}
