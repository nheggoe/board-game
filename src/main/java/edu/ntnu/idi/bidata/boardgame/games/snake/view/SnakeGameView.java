package edu.ntnu.idi.bidata.boardgame.games.snake.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessagePanel;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.SnakeAndLadderBoardRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import javafx.scene.layout.BorderPane;

public class SnakeGameView extends View {

  private final BorderPane root = new BorderPane();

  public SnakeGameView(EventBus eventBus, SnakeAndLadderBoard board) {
    getChildren().add(root);

    // component
    var render = new SnakeAndLadderBoardRender(eventBus, board);
    render.prefWidthProperty().bind(widthProperty().multiply(0.8));
    render.prefHeightProperty().bind(heightProperty().multiply(0.6));
    root.setCenter(render);
    addComponents(render);

    // text box
    var messageBox = new MessagePanel(eventBus);
    messageBox.prefWidthProperty().bind(widthProperty());
    messageBox.prefHeightProperty().bind(heightProperty().multiply(0.3));
    root.setBottom(messageBox);
    addComponents(messageBox);
  }
}
