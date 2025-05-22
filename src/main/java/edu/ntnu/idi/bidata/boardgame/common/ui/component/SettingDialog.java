package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class SettingDialog extends Dialog<ButtonType> {

  public SettingDialog(SceneSwitcher sceneSwitcher, boolean inGame) {
    var root = getDialogPane();
    root.setPrefWidth(300);
    root.setPrefHeight(200);
    root.getButtonTypes().addAll(ButtonType.OK);
    root.setContent(createContentPane(sceneSwitcher, inGame));
  }

  private VBox createContentPane(SceneSwitcher sceneSwitcher, boolean inGame) {
    var navigation = new VBox();
    navigation.setAlignment(Pos.CENTER);
    navigation.setSpacing(10);

    var editSavedPlayerButton = new Button("Edit Saved Players");
    editSavedPlayerButton.setOnAction(
        event -> {
          try {
            sceneSwitcher.switchTo(SceneSwitcher.SceneName.PLAYER_SETUP_VIEW);
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }
          this.close();
        });
    editSavedPlayerButton.setDisable(inGame);

    var backToMainMenuButton = new Button("Back to Main Menu");
    backToMainMenuButton.setOnAction(
        event -> {
          try {
            sceneSwitcher.switchTo(SceneSwitcher.SceneName.MAIN_VIEW);
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }
          this.close();
        });

    var exitButton = new Button("Exit");
    exitButton.setOnAction(event -> Platform.exit());

    navigation.getChildren().addAll(editSavedPlayerButton, backToMainMenuButton, exitButton);
    return navigation;
  }
}
