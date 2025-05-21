package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.CoreEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Renders the players on the Snake and Ladder board.
 *
 * <p>This class is responsible for rendering player icons on the game board. It converts the
 * player's position to grid coordinates and updates the UI accordingly.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class PlayerRender extends EventListeningComponent {

  private final Supplier<List<SnakeAndLadderPlayer>> players;
  private final GridPane tileGrid;
  private final int gridSize;

  /**
   * @param tileGrid the pane returned by SnakeAndLadderBoardRender#getTileGrid().
   */
  public PlayerRender(
      EventBus eventBus,
      GridPane tileGrid,
      int boardDimension,
      Supplier<List<SnakeAndLadderPlayer>> players) {
    super(eventBus);
    getEventBus().addListener(CoreEvent.PlayerMoved.class, this);
    this.tileGrid = tileGrid;
    this.gridSize = boardDimension;
    this.players = requireNonNull(players, "players must not be null");
    renderPlayers();
  }

  @Override
  public void onEvent(Event event) {
    if (event instanceof CoreEvent.PlayerMoved) {
      Platform.runLater(this::renderPlayers);
    }
  }

  @Override
  public void close() {
    getEventBus().removeListener(CoreEvent.PlayerMoved.class, this);
  }

  /**
   * Renders the players on the board.
   *
   * <p>This method clears any existing player icons and then draws the new ones based on the
   * provided list of players.
   *
   * @param players a list of players to render
   */
  public void renderPlayers() {
    clearPlayerIcons();

    for (SnakeAndLadderPlayer player : players.get()) {
      Point2D pos = toGrid(player.getPosition()); // column, row
      StackPane tile = tileAt((int) pos.getY(), (int) pos.getX());

      ImageView icon = createFigureVisual(player.getFigure());
      icon.setUserData(player.getId());
      tile.getChildren().add(icon);
    }
  }

  /**
   * Converts a tile number to grid coordinates.
   *
   * <p>This method ensures that the tile number is within the valid range (1 to 100) and then
   * converts it to grid coordinates using the SnakeBoardLayout class.
   *
   * @param tileNumber the tile number to convert
   * @return the grid coordinates as a Point2D object
   */
  private Point2D toGrid(int tileNumber) {
    int safeNumber = tileNumber < 1 ? 1 : Math.min(tileNumber, 100);
    return SnakeBoardLayout.toGrid(safeNumber, gridSize);
  }

  private void clearPlayerIcons() {
    tileGrid
        .getChildren()
        .forEach(
            n -> {
              if (n instanceof StackPane pane)
                pane.getChildren().removeIf(ImageView.class::isInstance);
            });
  }

  /**
   * Returns the {@code StackPane} that sits at the requested grid position. Because panes are added
   * in tile-number order, we search by the stored column/row indices.
   */
  private StackPane tileAt(int row, int col) {
    for (var node : tileGrid.getChildren()) {
      int c = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);
      int r = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
      if (c == col && r == row) {
        return (StackPane) node;
      }
    }
    throw new IllegalArgumentException("Tile not found at (" + row + ',' + col + ')');
  }

  /** Returns a simple coloured square that serves as a temporary figure icon. */
  private ImageView createFigureVisual(Object __ /* ignored for now */) {

    WritableImage tiny = new WritableImage(1, 1);
    PixelWriter pw = tiny.getPixelWriter();
    pw.setColor(0, 0, Color.DODGERBLUE);

    ImageView img = new ImageView(tiny);
    img.setFitWidth(18);
    img.setFitHeight(18);
    img.setPreserveRatio(false);

    return img;
  }
}
