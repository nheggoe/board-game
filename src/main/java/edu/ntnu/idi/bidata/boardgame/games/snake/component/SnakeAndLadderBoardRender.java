package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * This class is responsible for rendering the visual grid-based board of the Snake and Ladder game.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class SnakeAndLadderBoardRender extends EventListeningComponent {

  private final GridPane tileGrid = new GridPane();

  /**
   * Constructs a SnakeAndLadderBoardRender instance and initialises the board.
   *
   * @param eventBus the event bus to handle game events
   * @param board the Snake and Ladder board model
   */
  public SnakeAndLadderBoardRender(EventBus eventBus, SnakeAndLadderBoard board) {
    super(eventBus);
    getEventBus().addListener(PlayerMovedEvent.class, this);
    initializeBoard(board);
    StackPane root = new StackPane();
    getChildren().add(root);
    root.getChildren().add(tileGrid);
  }

  /**
   * Initialises the visual representation of the board.
   *
   * <p>This method sets up a grid of tiles using the given Snake and Ladder board model. Tiles are
   * styled according to their type (Snake, Ladder, or Normal) and numbered from 1 to 90.
   *
   * @param board the Snake and Ladder board model
   */
  private void initializeBoard(SnakeAndLadderBoard board) {
    int boardSize = board.size();
    int gridSize = (int) Math.sqrt(boardSize);
    int tileSize = 50;

    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {

        int tileIndex = computeTileIndex(row, col, gridSize);
        SnakeAndLadderTile boardTile = board.getTileAtIndex(tileIndex);

        Pane tile = new Pane();
        tile.setPrefSize(tileSize, tileSize);
        tile.setStyle(getTileStyle(boardTile));

        Label label = new Label(String.valueOf(tileIndex + 1));
        label.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        label.setLayoutX(tileSize / 2.0 - 8);
        label.setLayoutY(tileSize / 2.0 - 8);
        tile.getChildren().add(label);

        tileGrid.add(tile, col, row);
      }
    }
  }

  /**
   * Computes the tile index for the given row and column on a Snake and Ladder board.
   *
   * <p>Tiles are numbered left-to-right, starting in the bottom-left corner.
   *
   * @param row the row index of the tile
   * @param col the column index of the tile
   * @param gridSize the total size of the grid (assumes a square grid)
   * @return the computed tile index
   */
  private int computeTileIndex(int row, int col, int gridSize) {
    int invertedRow = gridSize - row - 1;
    return (invertedRow * gridSize) + col;
  }

  /**
   * Determines the background style of a tile based on its type.
   *
   * @param tile the Snake and Ladder tile to style
   * @return the style string for the tile's background
   */
  private String getTileStyle(SnakeAndLadderTile tile) {
    if (tile instanceof SnakeTile) {
      return "-fx-border-color: black; -fx-background-color: lightcoral;"; // Red for Snake
    } else if (tile instanceof LadderTile) {
      return "-fx-border-color: black; -fx-background-color: lightgreen;"; // Green for Ladder
    } else {
      return "-fx-border-color: black; -fx-background-color: lightyellow;"; // Yellow for Normal
    }
  }

  /**
   * Handles the event when a player moves.
   *
   * <p>This method listens for {@link PlayerMovedEvent}. Logic to update the player's graphical
   * position can be added here when needed.
   *
   * @param event the event to handle
   */
  @Override
  public void onEvent(Event event) {
    if (event instanceof PlayerMovedEvent(Player player)) {
      // Optionally handle player movement updates
    }
  }

  /** Removes the {@link PlayerMovedEvent} listener and cleans up resources. */
  @Override
  public void close() {
    getEventBus().removeListener(PlayerMovedEvent.class, this);
  }

  /**
   * Calculates the graphical position of a tile based on its number.
   *
   * @param tileNumber the number of the tile (1-based)
   * @param tileSize the size of each tile in pixels
   * @return the position of the centre of the tile as a {@link Point2D}
   */
  public Point2D getTilePosition(int tileNumber, int tileSize) {
    int index = tileNumber - 1;
    int row = index / 10;
    int col = index % 10;

    if (row % 2 == 0) {
      col = 9 - col;
    }

    int x = col * tileSize;
    int y = (9 - row) * tileSize;
    return new Point2D(x + tileSize / 2.0, y + tileSize / 2.0);
  }
}
