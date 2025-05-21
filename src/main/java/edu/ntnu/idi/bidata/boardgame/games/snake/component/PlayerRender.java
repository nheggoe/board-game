package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.UnhandledEventException;
import edu.ntnu.idi.bidata.boardgame.common.event.type.CoreEvent;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * @version 2025.05.21
 */
public class PlayerRender extends EventListeningComponent {

  /** Supplier of the list of players. */
  private final Supplier<List<SnakeAndLadderPlayer>> players;

  /** The GridPane containing the board tiles. */
  private final GridPane tileGrid;

  /** Number of tiles along one side of the board. */
  private final int gridSize;

  /**
   * Creates a new {@code PlayerRender}.
   *
   * @param eventBus the global event bus for listening to player movement events
   * @param tileGrid the pane returned by {@code SnakeAndLadderBoardRender#getTileGrid()}
   * @param boardDimension the length of one board side in tiles
   * @param players supplier of the current list of {@code SnakeAndLadderPlayer}
   */
  public PlayerRender(
      EventBus eventBus,
      GridPane tileGrid,
      int boardDimension,
      Supplier<List<SnakeAndLadderPlayer>> players) {
    super(eventBus, CoreEvent.PlayerMoved.class);
    this.tileGrid = tileGrid;
    this.gridSize = boardDimension;
    this.players = requireNonNull(players, "players must not be null");
    renderPlayers();
  }

  /**
   * Receives events from the {@code EventBus} and triggers re-rendering when a player moves.
   *
   * @param event the event published on the bus
   */
  @Override
  public void onEvent(Event event) {
    switch (event) {
      case CoreEvent.PlayerMoved ignored -> Platform.runLater(this::renderPlayers);
      default -> throw new UnhandledEventException(event);
    }
  }

  /** Clears existing icons and draws each player's figure on the correct tile. */
  public void renderPlayers() {
    clearPlayerIcons();

    for (SnakeAndLadderPlayer player : players.get()) {
      Point2D pos = toGrid(player.getPosition());
      StackPane tile = tileAt((int) pos.getY(), (int) pos.getX());

      ImageView icon = createFigureVisual(player.getFigure());
      icon.setUserData(player.getId());

      int playerCount =
          (int)
              tile.getChildren().stream()
                  .filter(n -> n instanceof ImageView && n.getUserData() != null)
                  .count();

      StackPane.setAlignment(icon, iconAlignmentForIndex(playerCount));
      tile.getChildren().add(icon);
    }
  }


  /**
   * Converts a tile number to grid coordinates.
   *
   * @param tileNumber the tile number to convert
   * @return the grid coordinates as a {@code Point2D} object
   */
  private Point2D toGrid(int tileNumber) {
    int safeNumber = tileNumber < 1 ? 1 : Math.min(tileNumber, gridSize * gridSize);
    return SnakeBoardLayout.toGrid(safeNumber, gridSize);
  }

  /** Removes all existing player icons from each tile. */
  private void clearPlayerIcons() {
    tileGrid
        .getChildren()
        .forEach(
            node -> {
              if (node instanceof StackPane pane) {
                pane.getChildren().removeIf(ImageView.class::isInstance);
              }
            });
  }

  /**
   * Finds the {@code StackPane} at the specified row and column.
   *
   * @param row the row index
   * @param col the column index
   * @return the matching {@code StackPane}
   * @throws IllegalArgumentException if no pane exists at the given coordinates
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

  /**
   * Loads the player's figure image or falls back to a coloured square.
   *
   * @param figure the player's figure enum
   * @return an {@code ImageView} containing the figure icon
   */
  private ImageView createFigureVisual(Player.Figure figure) {
    String fileName = figure.name().toLowerCase().replace("_", "");
    String resourcePath = "images/" + fileName + ".png";
    InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
    if (is != null) {
      Image img = new Image(is, 24, 24, true, true);
      ImageView view = new ImageView(img);
      view.setUserData(figure);
      return view;
    }

    WritableImage tiny = new WritableImage(1, 1);
    tiny.getPixelWriter().setColor(0, 0, Color.DODGERBLUE);
    ImageView fallback = new ImageView(tiny);
    fallback.setFitWidth(18);
    fallback.setFitHeight(18);
    fallback.setPreserveRatio(false);
    fallback.setUserData(figure);
    return fallback;
  }

  /**
   * Returns a grid alignment position based on a player's index on a tile. Used to visually offset
   * multiple player icons occupying the same tile to avoid overlap.
   *
   * @param index the number of player icons already placed on the tile
   * @return the {@link Pos} value representing the placement of the new player icon
   */
  private Pos iconAlignmentForIndex(int index) {
    return switch (index) {
      case 0 -> Pos.TOP_LEFT;
      case 1 -> Pos.TOP_RIGHT;
      case 2 -> Pos.BOTTOM_LEFT;
      case 3 -> Pos.BOTTOM_RIGHT;
      default -> Pos.CENTER;
    };
  }
}
