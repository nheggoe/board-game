package edu.ntnu.idi.bidata.boardgame.games.snake.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessagePanel;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.PlayerDashboard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.RollDiceButton;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.PlayerRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.SnakeAndLadderBoardRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;
import java.util.function.Supplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/** JavaFX view for the Snake & Ladder game. */
public class SnakeGameView extends View {

  public SnakeGameView(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      SnakeAndLadderBoard board,
      Supplier<List<SnakeAndLadderPlayer>> playersSupplier,
      EventHandler<ActionEvent> rollDiceAction) {
    var root = new BorderPane();
    setRoot(root);

    // board and player render
    root.setCenter(createCenterPane(eventBus, board, playersSupplier));

    // roll-dice button
    root.setRight(createRightPane(sceneSwitcher, eventBus, playersSupplier, rollDiceAction));

    // Message panel (console/log)
    root.setBottom(createBottomPane(eventBus));
  }

  private Pane createRightPane(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      Supplier<List<SnakeAndLadderPlayer>> playersSupplier,
      EventHandler<ActionEvent> rollDiceAction) {
    var right = new VBox();
    right.setAlignment(Pos.TOP_CENTER);
    right.prefWidthProperty().bind(this.widthProperty().multiply(0.3));

    var settingButton = new SettingButton(sceneSwitcher);
    var playerUi = new PlayerDashboard<>(eventBus, playersSupplier);
    var rollDiceButton = new RollDiceButton(rollDiceAction);

    addComponents(settingButton, playerUi, rollDiceButton);
    right.getChildren().addAll(settingButton, playerUi, rollDiceButton);

    return right;
  }

  private MessagePanel createBottomPane(EventBus eventBus) {
    var messagePanel = new MessagePanel(eventBus);
    addComponents(messagePanel);
    messagePanel.prefWidthProperty().bind(widthProperty());
    messagePanel.prefHeightProperty().bind(heightProperty().multiply(0.2));
    return messagePanel;
  }

  private HBox createCenterPane(
      EventBus eventBus,
      SnakeAndLadderBoard board,
      Supplier<List<SnakeAndLadderPlayer>> playersSupplier) {
    var centre = new HBox();
    centre.setAlignment(Pos.CENTER);

    var boardRender = new SnakeAndLadderBoardRender(eventBus, board);
    var playerRender =
        new PlayerRender(
            eventBus, boardRender.getTileGrid(), boardRender.getGridSize(), playersSupplier);

    addComponents(boardRender, playerRender);
    centre.getChildren().addAll(boardRender, playerRender);

    return centre;
  }
}
