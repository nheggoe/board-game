package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;
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
public class PlayerRender {

  private final GridPane tileGrid;
  private final int gridSize;

  /**
   * @param tileGrid the pane returned by SnakeAndLadderBoardRender#getTileGrid().
   */
  public PlayerRender(GridPane tileGrid) {
    this.tileGrid = tileGrid;
    this.gridSize = (int) Math.sqrt(tileGrid.getChildren().size());
  }

  /**
   * Renders the players on the board.
   *
   * <p>This method clears any existing player icons and then draws the new ones based on the
   * provided list of players.
   *
   * @param players a list of players to render
   */
  public void renderPlayers(List<SnakeAndLadderPlayer> players) {
    clearPlayerIcons();

    for (SnakeAndLadderPlayer player : players) {
      Point2D pos = toGrid(player.getPosition()); // column, row
      StackPane tile = tileAt((int) pos.getY(), (int) pos.getX());

      ImageView icon = createFigureVisual(player.getFigure());
      icon.setUserData(player.getId());
      tile.getChildren().add(icon);
    }
  }

  /** Converts a 1-based tile number to (column, row) in the grid. */
  private Point2D toGrid(int tileNumber) {
    int index = tileNumber - 1;
    int rowBase = gridSize - 1 - index / gridSize;
    int colBase = index % gridSize;

    boolean reversed = (gridSize - 1 - rowBase) % 2 == 1;
    int col = reversed ? (gridSize - 1 - colBase) : colBase;

    return new Point2D(col, rowBase);
  }

  private StackPane tileAt(int row, int col) {
    return (StackPane) tileGrid.getChildren().get(row * gridSize + col);
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
