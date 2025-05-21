package edu.ntnu.idi.bidata.boardgame.common.ui.view;

import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainView extends View {

  public MainView(SceneSwitcher sceneSwitcher) {
    var root = new BorderPane();
    setRoot(root);

    root.setCenter(createCenterPane(sceneSwitcher));
    root.setRight(new SettingButton(sceneSwitcher));
  }

  private VBox createCenterPane(SceneSwitcher sceneSwitcher) {
    var center = new VBox();
    center.setAlignment(Pos.CENTER);
    center.setSpacing(10);
    var monopolyButton = new Button("Monopoly");
    var snakeAndeLadderButton = new Button("Snake and Ladder");
    monopolyButton.setOnAction(
        ignored -> sceneSwitcher.switchTo(SceneSwitcher.SceneName.MONOPOLY_GAME_VIEW));
    snakeAndeLadderButton.setOnAction(
        ignored -> sceneSwitcher.switchTo(SceneSwitcher.SceneName.SNAKE_GAME_VIEW));
    center.getChildren().addAll(monopolyButton, snakeAndeLadderButton);
    return center;
  }
}
