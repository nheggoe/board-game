package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class SettingDialog extends Dialog<ButtonType> {

  public SettingDialog(SceneSwitcher sceneSwitcher) {
    var root = getDialogPane();
    root.getButtonTypes().addAll(ButtonType.OK);
    root.setContent(createContentPane(sceneSwitcher));
  }

  private VBox createContentPane(SceneSwitcher sceneSwitcher) {
    var navigation = new VBox();
    navigation.setAlignment(Pos.CENTER);
    navigation.setSpacing(10);

    var editSavedPlayerButton = new Button("Edit Saved Players");
    editSavedPlayerButton.setOnAction(event -> sceneSwitcher.switchTo(View.Name.PLAYER_SETUP_VIEW));

    var exitButton = new Button("Exit");
    exitButton.setOnAction(event -> Platform.exit());

    navigation.getChildren().add(editSavedPlayerButton);
    return navigation;
  }
}
