package edu.ntnu.idi.bidata.boardgame.games.monopoly.component;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.CornerMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.OwnableMonopolyTile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.TaxMonopolyTile;
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

public class GameBoard extends VBox {

  private final GridPane board;

  public GameBoard(List<MonopolyTile> tiles) {
    super();
    board = new GridPane();
    getChildren().add(board);
    setAlignment(Pos.CENTER);
    initialize(tiles);
  }

  /**
   * Updates the visual representation of a player's position on the game board. This method is
   * called when a player moves to a new position on the board.
   *
   * @param player the player who has moved
   * @param position the new position of the player on the board
   */
  public void playerMoved(Player player, int position) {
    // Clear any existing player figure from the board
    clearPlayerFigures(player);

    // Calculate the grid position for the new tile
    int size = (board.getChildren().size() + 4) / 4;
    int row = 0;
    int col = 0;

    // Position logic matches the initialize method's tile placement
    if (position == 0) {
      // Bottom right corner
      row = size - 1;
      col = size - 1;
    } else if (position < size) {
      // Right column (going up)
      row = size - 1 - position;
      col = size - 1;
    } else if (position < size * 2 - 1) {
      // Top row (going left)
      row = 0;
      col = size - 1 - (position - (size - 1));
    } else if (position < size * 3 - 2) {
      // Left column (going down)
      row = position - (size * 2 - 2);
      col = 0;
    } else {
      // Bottom row (going right)
      row = size - 1;
      col = position - (size * 3 - 3);
    }

    // Get the tile at the calculated position
    StackPane tilePane = getTileAtPosition(row, col);
    if (tilePane != null) {
      // Create and add player figure to the tile
      addPlayerFigure(tilePane, player);
    }
  }

  /**
   * Removes all visual representations of the specified player from the board.
   *
   * @param player the player whose figures should be removed
   */
  private void clearPlayerFigures(Player player) {
    // Iterate through all tiles on the board
    for (javafx.scene.Node node : board.getChildren()) {
      if (node instanceof StackPane tilePane) {
        // Remove any player figure matching this player
        tilePane
            .getChildren()
            .removeIf(
                child ->
                    child instanceof ImageView imageView
                        && imageView.getUserData() != null
                        && imageView.getUserData().equals(player.getId()));
      }
    }
  }

  /**
   * Gets the tile StackPane at the specified grid position.
   *
   * @param row the row in the grid
   * @param col the column in the grid
   * @return the StackPane at the specified position, or null if not found
   */
  private StackPane getTileAtPosition(int row, int col) {
    for (javafx.scene.Node node : board.getChildren()) {
      if (node instanceof StackPane
          && GridPane.getRowIndex(node) == row
          && GridPane.getColumnIndex(node) == col) {
        return (StackPane) node;
      }
    }
    return null;
  }

  /**
   * Adds a visual representation of the player to the specified tile.
   *
   * @param tilePane the tile where the player figure should be added
   * @param player the player whose figure should be added
   */
  private void addPlayerFigure(StackPane tilePane, Player player) {
    String imagePath = getFigureImagePath(player.getFigure());
    ImageView playerFigure = new ImageView(new Image(imagePath, 30, 30, true, true));
    playerFigure.setUserData(player.getId()); // Store player ID for identification

    // Position the figure appropriately on the tile
    StackPane.setAlignment(playerFigure, Pos.CENTER);

    // If there are other players on this tile, adjust positioning
    int playerCount =
        (int)
            tilePane.getChildren().stream()
                .filter(child -> child instanceof ImageView && child.getUserData() != null)
                .count();

    // Adjust position based on number of players already on this tile
    switch (playerCount) {
      case 0 -> StackPane.setAlignment(playerFigure, Pos.TOP_LEFT);
      case 1 -> StackPane.setAlignment(playerFigure, Pos.TOP_RIGHT);
      case 2 -> StackPane.setAlignment(playerFigure, Pos.BOTTOM_LEFT);
      case 3 -> StackPane.setAlignment(playerFigure, Pos.BOTTOM_RIGHT);
      default -> StackPane.setAlignment(playerFigure, Pos.CENTER); // Fallback
    }

    // Add the player figure to the tile
    tilePane.getChildren().add(playerFigure);
  }

  /**
   * Gets the image path for a player figure.
   *
   * @param figure the player's figure type
   * @return the path to the corresponding image resource
   */
  private String getFigureImagePath(Player.Figure figure) {
    return switch (figure) {
      case BATTLE_SHIP -> "images/battleship.png";
      case CAR -> "images/car.png";
      case CAT -> "images/cat.png";
      case DUCK -> "images/duck.png";
      case HAT -> "images/hat.png";
    };
  }

  private void initialize(List<MonopolyTile> tiles) {
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

  private Pane createTile(MonopolyTile tile) {
    StackPane tilePane = new StackPane();
    int tileSize = 80;
    tilePane.setPrefSize(tileSize, tileSize);

    switch (tile) {
      case CornerMonopolyTile cornerTile ->
          tilePane.setBackground(
              new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
      case OwnableMonopolyTile(Ownable ownable) -> {
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
      case TaxMonopolyTile taxTile -> {
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
