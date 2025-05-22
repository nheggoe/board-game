package edu.ntnu.idi.bidata.boardgame.common.ui.view;

import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainView extends View {

  public MainView(
      SceneSwitcher sceneSwitcher,
      EventHandler<ActionEvent> snake,
      EventHandler<ActionEvent> monopoly) {
    var root = new BorderPane();
    setRoot(root);

    root.setCenter(createCenterPane(snake, monopoly));
    root.setRight(new SettingButton(sceneSwitcher));
  }

  private VBox createCenterPane(
      EventHandler<ActionEvent> snake, EventHandler<ActionEvent> monopoly) {
    var center = new VBox();
    center.setAlignment(Pos.CENTER);
    center.setSpacing(10);
    var monopolyButton = new Button("Monopoly");
    var snakeAndeLadderButton = new Button("Snake and Ladder");
    snakeAndeLadderButton.setOnAction(snake);
    monopolyButton.setOnAction(monopoly);
    center.getChildren().addAll(monopolyButton, snakeAndeLadderButton);
    return center;
  }
}
