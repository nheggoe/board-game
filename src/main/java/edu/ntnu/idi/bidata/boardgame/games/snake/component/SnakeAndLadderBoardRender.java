package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import javafx.geometry.Point2D;
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
public class SnakeAndLadderBoardRender extends EventListeningComponent {

  /** Width/height of a single tile in pixels. */
  private static final int TILE_SIZE = 50;

  private final SnakeAndLadderBoard board;
  private final int gridSize;
  private final GridPane tileGrid = new GridPane();

  /**
   * Creates the board renderer and immediately draws the static tiles.
   *
   * @param eventBus global event bus (not used here but required by {@link
   *     EventListeningComponent})
   * @param board the game board model
   */
  public SnakeAndLadderBoardRender(EventBus eventBus, SnakeAndLadderBoard board) {
    super(eventBus);
    this.board = board;
    this.gridSize = (int) Math.sqrt(board.size());

    buildStaticBoard();
    getChildren().add(new StackPane(tileGrid));
  }

  /** Returns the {@link GridPane} that contains all tile panes. */
  public GridPane getTileGrid() {
    return tileGrid;
  }

  /** Returns the length of one board side in tiles (board is assumed square). */
  public int getGridSize() {
    return gridSize;
  }

  /** Returns the pixel size of a single tile. */
  public int getTileSize() {
    return TILE_SIZE;
  }

  /** Draws every tile pane and adds it to {@link #tileGrid}. */
  private void buildStaticBoard() {
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        int tileIndex = computeTileIndex(row, col);
        SnakeAndLadderTile modelTile = board.getTileAtIndex(tileIndex);

        StackPane tilePane = new StackPane();
        tilePane.setPrefSize(TILE_SIZE, TILE_SIZE);
        tilePane.setStyle(tileStyle(modelTile));

        Label nr = new Label(String.valueOf(tileIndex + 1));
        nr.setStyle("-fx-font-size: 13; -fx-font-weight: bold;");
        tilePane.getChildren().add(nr);

        tileGrid.add(tilePane, col, row);
      }
    }
  }

  /** Converts a grid coordinate to the underlying tile index. */
  private int computeTileIndex(int row, int col) {
    int invertedRow = gridSize - 1 - row;
    return invertedRow * gridSize + col;
  }

  /** Returns a CSS string that reflects the visual type of tile. */
  private String tileStyle(SnakeAndLadderTile tile) {
    return switch (tile) {
      case SnakeTile __ -> "-fx-border-color: black; -fx-background-color: lightcoral;";
      case LadderTile __ -> "-fx-border-color: black; -fx-background-color: lightgreen;";
      default -> "-fx-border-color: black; -fx-background-color: lightyellow;";
    };
  }

  @Override
  public void onEvent(Event event) {
    // This renderer draws only static content; no event handling required.
  }

  @Override
  public void close() {
    // Nothing to clean up.
  }

  /**
   * Calculates the centre point of a tile.
   *
   * @param tileNumber 1-based tile number
   * @return pixel coordinate of the tile centre within the board pane
   */
  public Point2D getTileCentre(int tileNumber) {
    int index = tileNumber - 1;
    int row = gridSize - 1 - index / gridSize;
    int col = index % gridSize;
    double x = col * TILE_SIZE + TILE_SIZE / 2.0;
    double y = row * TILE_SIZE + TILE_SIZE / 2.0;
    return new Point2D(x, y);
  }
}
