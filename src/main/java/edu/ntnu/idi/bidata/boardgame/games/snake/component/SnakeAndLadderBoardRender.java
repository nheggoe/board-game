package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.ui.Component;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.List;
import java.util.function.Supplier;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * JavaFX component responsible for rendering a static Snake & Ladder board. The class paints the
 * tiles (normal, snake, ladder) and tile numbers.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class SnakeAndLadderBoardRender extends Component {

  /** Width/height of a single tile in pixels. */
  private static final int TILE_SIZE = 60;

  /** Side length (in tiles). Read from the model instead of hard-coding “10”. */
  private final int boardDimension;

  private final Supplier<List<SnakeAndLadderTile>> tilesSupplier;
  private final GridPane tileGrid = new GridPane();

  /**
   * Creates the board renderer and immediately draws the static tiles.
   *
   * @param eventBus global event bus (not used here but required by {@link
   *     EventListeningComponent})
   * @param board the game board model
   */
  public SnakeAndLadderBoardRender(
      EventBus eventBus, Supplier<List<SnakeAndLadderTile>> tilesSupplier) {
    setAlignment(Pos.CENTER);
    this.tilesSupplier = tilesSupplier;
    this.boardDimension = 10;

    buildStaticBoard();
    getChildren().add(new StackPane(tileGrid));
  }

  /** Returns the {@link GridPane} that contains all tile panes. */
  public GridPane getTileGrid() {
    return tileGrid;
  }

  /** Returns the length of one board side in tiles (board is assumed square). */
  public int getGridSize() {
    return boardDimension;
  }

  /** Returns the pixel size of a single tile. */
  public int getTileSize() {
    return TILE_SIZE;
  }

  /** Draws every tile pane and adds it to {@link #tileGrid}. */
  private void buildStaticBoard() {
    for (int tile = 1; tile <= 100; tile++) {
      Point2D p = SnakeBoardLayout.toGrid(tile, boardDimension);
      StackPane tilePane = createTileVisual(tile);
      tileGrid.add(tilePane, (int) p.getX(), (int) p.getY());
    }
  }

  /** Builds a single tile pane with number and colouring. */
  private StackPane createTileVisual(int tileNumber) {
    var tiles = tilesSupplier.get();

    SnakeAndLadderTile modelTile = (tileNumber <= tiles.size()) ? tiles.get(tileNumber - 1) : null;

    StackPane pane = new StackPane();
    pane.setPrefSize(TILE_SIZE, TILE_SIZE);

    var label = new Label(String.valueOf(tileNumber));
    label.setStyle("-fx-font-size: 11;");
    StackPane.setAlignment(label, javafx.geometry.Pos.TOP_LEFT);
    pane.getChildren().add(label);

    pane.setStyle(tileStyle(modelTile));

    return pane;
  }

  /** Returns a CSS style string that reflects the visual type of the tile. */
  private String tileStyle(SnakeAndLadderTile tile) {
    if (tile instanceof SnakeTile) {
      return "-fx-border-color: black; -fx-background-color: lightcoral;";
    } else if (tile instanceof LadderTile) {
      return "-fx-border-color: black; -fx-background-color: lightgreen;";
    } else {
      return "-fx-border-color: black; -fx-background-color: lightyellow;";
    }
  }
}
