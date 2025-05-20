package edu.ntnu.idi.bidata.boardgame.common.ui;

import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class EndDialog extends Dialog<ButtonType> {

  public EndDialog(SceneSwitcher sceneSwitcher) {
    super();
    setTitle("Game Over");
    setHeaderText("Please select an option to continue.");
    var dialogPane = getDialogPane();
    dialogPane.setContent(setNavigation(sceneSwitcher));
    setDialogPane(dialogPane);
    getDialogPane().getButtonTypes().addAll(ButtonType.OK);
  }

  private VBox setNavigation(SceneSwitcher sceneSwitcher) {
    var vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(10);
    var backToMainMenuButton = new Button("Back to Main Menu");
    backToMainMenuButton.setOnAction(event -> sceneSwitcher.switchTo(View.Name.MAIN_VIEW));
    var newGameButton = new Button("Play Again");
    newGameButton.setOnAction(event -> sceneSwitcher.reset());
    var exitButton = new Button("Exit");
    exitButton.setOnAction(event -> Platform.exit());
    vBox.getChildren().addAll(backToMainMenuButton, exitButton);
    return vBox;
  }
}
