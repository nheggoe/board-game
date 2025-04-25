package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.frontend.controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GamePane extends VBox {

  // child
  private final GridPane board;

  // controller
  private final GameController controller;

  public GamePane() {
    super();
    board = new GridPane();
    controller = new GameController();
    getChildren().add(board);
    setAlignment(Pos.CENTER);
    initialize();
  }

  public void initialize() {
    board.setGridLinesVisible(true); // optional: show grid lines
    board.setAlignment(Pos.CENTER);

    int size = controller.getBoardSize() / 4;
    int pos = 0;

    // Bottom row (right to left)
    for (int col = size - 1; col >= 0; col--) {
      board.add(createTile(pos++), size - 1, col);
    }

    // Left column (bottom to top, skip corner)
    for (int row = size - 2; row >= 0; row--) {
      board.add(createTile(pos++), row, 0);
    }

    // Top row (left to right, skip corner)
    for (int col = 1; col < size; col++) {
      board.add(createTile(pos++), 0, col);
    }

    // Right column (top to bottom, skip corners)
    for (int row = 1; row < size - 1; row++) {
      board.add(createTile(pos++), row, size - 1);
    }
  }

  public void bindSizeProperty() {

    board.prefWidthProperty().bind(this.getScene().widthProperty());
    board.prefHeightProperty().bind(this.getScene().heightProperty());
  }

  private Pane createTile(int pos) {
    StackPane tile = new StackPane();
    tile.setPrefSize(60, 60);
    tile.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

    Label label = new Label(String.format("%02d", pos));
    tile.getChildren().add(label);

    return tile;
  }
}
