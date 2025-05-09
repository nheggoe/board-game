package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerMovedEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * @author Nick Heggø
 * @version 2025.05.09
 */
public class SnakeAndLadderBoardRender extends EventListeningComponent {

  private final StackPane root = new StackPane();
  private final GridPane tileGrid = new GridPane();
  private final Pane drawLayer = new Pane();

  public SnakeAndLadderBoardRender(EventBus eventBus, SnakeAndLadderBoard board) {
    super(eventBus);
    getEventBus().addListener(PlayerMovedEvent.class, this);
    initializeBoard(board);
    getChildren().add(root);
    root.getChildren().add(tileGrid);
  }

  private void initializeBoard(SnakeAndLadderBoard board) {
    int tileSize = board.size();

    // FIXME beta testing
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        Pane tile = new Pane();
        tile.setPrefSize(tileSize * 10, tileSize * 10);
        tile.setStyle("-fx-border-color: black; -fx-background-color: lightyellow;");
        tileGrid.add(tile, j, i);
      }
    }
  }

  @Override
  public void onEvent(Event event) {
    if (event instanceof PlayerMovedEvent(Player player)) {}
  }

  @Override
  public void close() {
    getEventBus().removeListener(PlayerMovedEvent.class, this);
  }

  // ChatGPT generated
  public Point2D getTilePosition(int tileNumber, int tileSize) {
    int index = tileNumber - 1;
    int row = index / 10;
    int col = index % 10;

    // If row is even (0, 2, 4...), we go RIGHT to LEFT
    if (row % 2 == 0) {
      col = 9 - col;
    }

    int x = col * tileSize;
    int y = (9 - row) * tileSize; // invert to start from bottom

    // Center of tile (optional)
    return new Point2D(x + tileSize / 2, y + tileSize / 2);
  }
}
