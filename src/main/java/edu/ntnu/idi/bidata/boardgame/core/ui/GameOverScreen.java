package edu.ntnu.idi.bidata.boardgame.core.ui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Simple Game Over screen for Snake and Ladder. Offers restart or exit.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.05.20
 */
public final class GameOverScreen {

  private GameOverScreen() {}

  public static void show(String winnerName) {
    Platform.runLater(
        () -> {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Game Over");
          alert.setHeaderText("Winner: " + winnerName);
          alert.setContentText("Thank you for playing!");

          ButtonType exit = new ButtonType("Exit");
          alert.getButtonTypes().setAll(exit);

          alert.showAndWait();
          Platform.exit();
        });
  }
}
