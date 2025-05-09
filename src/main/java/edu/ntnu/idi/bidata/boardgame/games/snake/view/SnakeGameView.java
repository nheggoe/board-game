package edu.ntnu.idi.bidata.boardgame.games.snake.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.SnakeAndLadderBoardRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;

public class SnakeGameView extends View {

  public SnakeGameView(EventBus eventBus, SnakeAndLadderBoard board) {
    var render = new SnakeAndLadderBoardRender(eventBus, board);
    render.prefWidthProperty().bind(widthProperty());
    render.prefHeightProperty().bind(heightProperty());
    getChildren().add(render);
    addComponents(render);
  }
}
