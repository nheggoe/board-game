package edu.ntnu.idi.bidata.boardgame.games.snake.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessagePanel;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.PlayerRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.RollDiceSnakeButton;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.SnakeAndLadderBoardRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoard;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/** JavaFX view for the Snake & Ladder game. */
public class SnakeGameView extends View {

  private final PlayerRender playerRender;
  private final RollDiceSnakeButton rollDiceButton;

  /*
  Constructor
   */
  public SnakeGameView(SceneSwitcher sceneSwitcher, EventBus eventBus, SnakeAndLadderBoard board) {

    var root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setSpacing(10);
    getChildren().add(root);
    root.getChildren().add(new SettingButton(sceneSwitcher));

    /*  Fields exposed to the controller */
    SnakeAndLadderBoardRender boardRender = new SnakeAndLadderBoardRender(eventBus, board);

    this.playerRender = new PlayerRender(boardRender.getTileGrid(), boardRender.getGridSize());

    var centre = new HBox();
    centre.setAlignment(Pos.CENTER);
    centre.getChildren().add(boardRender);
    root.getChildren().add(centre);

    /* Roll-dice button */
    rollDiceButton = new RollDiceSnakeButton(e -> {});
    rollDiceButton.setAlignment(Pos.CENTER);
    root.getChildren().add(rollDiceButton);

    /* Message panel (console/log) */
    var messagePanel = new MessagePanel(eventBus);
    messagePanel.prefWidthProperty().bind(widthProperty());
    messagePanel.prefHeightProperty().bind(heightProperty().multiply(0.2));
    root.getChildren().add(messagePanel);
    addComponents(messagePanel);
  }

  /** Exposes the board renderer. */
  public PlayerRender getPlayerRender() {
    return playerRender;
  }

  /** Exposes the custom roll-dice component */
  public RollDiceSnakeButton getRollDiceButton() {
    return rollDiceButton;
  }
}
