package edu.ntnu.idi.bidata.boardgame.games.snake.view;

import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * View to set up a Snake and Ladder game. Players input their names, configure settings, and start
 * the game.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class SnakeSetupView extends View {

  private final List<TextField> playerInputs;
  private final Button startGameButton;

  public SnakeSetupView() {
    this.playerInputs = new ArrayList<>();
    this.startGameButton = new Button("Start Game");

    var root = new BorderPane();
    root.setCenter(createPlayerInputForm());
    root.setBottom(startGameButton);

    getChildren().add(root);
  }

  private GridPane createPlayerInputForm() {
    var grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    for (int i = 0; i < 4; i++) {
      var label = new Label("Player " + (i + 1) + ":");
      var inputField = new TextField();
      inputField.setPromptText("Enter player name");
      grid.add(label, 0, i);
      grid.add(inputField, 1, i);
      playerInputs.add(inputField);
    }

    return grid;
  }

  public List<String> getPlayerNames() {
    List<String> playerNames = new ArrayList<>();
    for (var textField : playerInputs) {
      String name = textField.getText().trim();
      if (!name.isEmpty()) {
        playerNames.add(name);
      }
    }
    return playerNames;
  }

  public Button getStartGameButton() {
    return startGameButton;
  }
}
