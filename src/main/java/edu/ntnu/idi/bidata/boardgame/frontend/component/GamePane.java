package edu.ntnu.idi.bidata.boardgame.frontend.component;

import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.CornerTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.OwnableTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TaxTile;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import java.util.List;
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

  private final GridPane board;

  public GamePane(List<Tile> tiles) {
    super();
    board = new GridPane();
    getChildren().add(board);
    setAlignment(Pos.CENTER);
    initialize(tiles);
  }

  public void initialize(List<Tile> tiles) {
    board.setGridLinesVisible(true); // optional: show grid lines
    board.setAlignment(Pos.CENTER);

    int size = (tiles.size() + 4) / 4;

    // Position tiles in clockwise order, starting with 00 at bottom right

    // Bottom right corner - position 00
    board.add(createTile(tiles.getFirst()), size - 1, size - 1);

    // Right column (going up, skip bottom right)
    int pos = 1;
    for (int row = size - 2; row >= 0; row--) {
      board.add(createTile(tiles.get(pos++)), row, size - 1);
    }

    // Top row (going left, skip top right)
    for (int col = size - 2; col >= 0; col--) {
      board.add(createTile(tiles.get(pos++)), 0, col);
    }

    // Left column (going down, skip top left)
    for (int row = 1; row < size; row++) {
      board.add(createTile(tiles.get(pos++)), row, 0);
    }

    // Bottom row (going right, skip bottom left)
    for (int col = 1; col < size - 1; col++) {
      board.add(createTile(tiles.get(pos++)), size - 1, col);
    }
  }

  public void bindSizeProperty() {
    board.prefWidthProperty().bind(this.getScene().widthProperty());
    board.prefHeightProperty().bind(this.getScene().heightProperty());
  }

  private Pane createTile(Tile tile) {
    StackPane tilePane = new StackPane();
    int tileSize = 80;
    tilePane.setPrefSize(tileSize, tileSize);

    switch (tile) {
      case CornerTile cornerTile ->
          tilePane.setBackground(
              new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
      case OwnableTile(Ownable ownable) -> {
        switch (ownable) {
          case Property property -> {
            tilePane.setBackground(
                new Background(
                    new BackgroundFill(
                        colorAdapter(property.getColor()), CornerRadii.EMPTY, Insets.EMPTY)));

            var image = new Image("icons/propertyv2.png", tileSize, tileSize, true, true);
            var imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            tilePane.getChildren().add(imageView);
            var label = new Label(property.getName());
            tilePane.getChildren().add(label);
            StackPane.setAlignment(imageView, Pos.CENTER);
            StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
          }
          case Railroad railroad -> {
            tilePane.setBackground(
                new Background(
                    new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            tilePane
                .getChildren()
                .add(
                    new ImageView(
                        new Image("icons/railroad.png", tileSize, tileSize, true, false)));
          }
          case Utility utility -> {
            tilePane.setBackground(
                new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
            tilePane
                .getChildren()
                .add(
                    new ImageView(new Image("icons/utility.png", tileSize, tileSize, true, false)));
          }
        }
      }
      case TaxTile taxTile -> {
        tilePane.setBackground(
            new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        tilePane
            .getChildren()
            .add(new ImageView(new Image("icons/propertyv2.png", 60, 60, true, false)));
      }
    }
    tilePane.setStyle("-fx-border-color: black;");

    return tilePane;
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
