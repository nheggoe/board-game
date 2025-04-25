package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.CornerTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.OwnableTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TaxTile;
import edu.ntnu.idi.bidata.boardgame.frontend.controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

    int size = (controller.getBoardSize() + 4) / 4;

    // Position tiles in clockwise order, starting with 00 at bottom right

    // Bottom right corner - position 00
    board.add(createTile(0), size - 1, size - 1);

    // Right column (going up, skip bottom right)
    int pos = 1;
    for (int row = size - 2; row >= 0; row--) {
      board.add(createTile(pos++), row, size - 1);
    }

    // Top row (going left, skip top right)
    for (int col = size - 2; col >= 0; col--) {
      board.add(createTile(pos++), 0, col);
    }

    // Left column (going down, skip top left)
    for (int row = 1; row < size; row++) {
      board.add(createTile(pos++), row, 0);
    }

    // Bottom row (going right, skip bottom left)
    for (int col = 1; col < size - 1; col++) {
      board.add(createTile(pos++), size - 1, col);
    }
  }

  public void bindSizeProperty() {
    board.prefWidthProperty().bind(this.getScene().widthProperty());
    board.prefHeightProperty().bind(this.getScene().heightProperty());
  }

  private Pane createTile(int pos) {
    StackPane tile = new StackPane();
    int tileSize = 80;
    tile.setPrefSize(tileSize, tileSize);

    switch (controller.getTileAtPosition(pos)) {
      case CornerTile cornerTile ->
          tile.setBackground(
              new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
      case OwnableTile(Ownable ownable) -> {
        switch (ownable) {
          case Property(String name, Property.Color color, int price) -> {
            tile.setBackground(
                new Background(
                    new BackgroundFill(colorAdapter(color), CornerRadii.EMPTY, Insets.EMPTY)));

            var image = new Image("icons/propertyv2.png", tileSize, tileSize, true, true);
            var imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            tile.getChildren().add(imageView);
            var label = new Label(name);
            tile.getChildren().add(label);
            StackPane.setAlignment(imageView, Pos.CENTER);
            StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
          }
          case Railroad railroad -> {
            tile.setBackground(
                new Background(
                    new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            tile.getChildren()
                .add(
                    new ImageView(
                        new Image("icons/railroad.png", tileSize, tileSize, true, false)));
          }
          case Utility utility -> {
            tile.setBackground(
                new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
            tile.getChildren()
                .add(
                    new ImageView(new Image("icons/utility.png", tileSize, tileSize, true, false)));
          }
        }
      }
      case TaxTile taxTile -> {
        tile.setBackground(
            new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        tile.getChildren()
            .add(new ImageView(new Image("icons/propertyv2.png", 60, 60, true, false)));
      }
    }
    tile.setStyle("-fx-border-color: black;");

    return tile;
  }

  private Color colorAdapter(Property.Color color) {
    return switch (color) {
      case BROWN -> Color.BROWN;
      case DARK_BLUE -> Color.DARKBLUE;
      case GREEN -> Color.GREEN;
      case LIGHT_BLUE -> Color.LIGHTBLUE;
      case ORANGE -> Color.ORANGE;
      case PINK -> Color.PINK;
      case RED -> Color.RED;
      case YELLOW -> Color.YELLOW;
    };
  }
}
