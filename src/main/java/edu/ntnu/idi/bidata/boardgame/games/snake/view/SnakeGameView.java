package edu.ntnu.idi.bidata.boardgame.games.snake.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessagePanel;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.RollDiceSnakeButton;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.SnakeAndLadderBoardRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SnakeGameView extends View {

  public SnakeGameView(EventBus eventBus, SnakeAndLadderBoard board) {
    // Root VBox for vertically stacking components
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setSpacing(10); // Add spacing between the board, roll button, and message log
    getChildren().add(root);

    // Center area: The board render
    var render = new SnakeAndLadderBoardRender(eventBus, board);
    render.prefWidthProperty().bind(widthProperty().multiply(0.8));
    render.prefHeightProperty().bind(heightProperty().multiply(0.6));
    root.getChildren().add(render);
    addComponents(render);

    // Below the board: Roll Dice Button
    var rollDiceButton =
        new RollDiceSnakeButton(event -> {}); // Event handler will be added in the controller
    rollDiceButton.setAlignment(Pos.CENTER); // Align the button
    root.getChildren().add(rollDiceButton);

    // Bottom area: The message log (Message Panel)
    var messageBox = new MessagePanel(eventBus);
    messageBox.prefWidthProperty().bind(widthProperty());
    messageBox
        .prefHeightProperty()
        .bind(heightProperty().multiply(0.2)); // Reduce height for log panel
    root.getChildren().add(messageBox);
    addComponents(messageBox);
  }
}