package edu.ntnu.idi.bidata.boardgame.games.monopoly.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.View;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.DiceView;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.GameBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessageLog;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.RollDiceButton;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.UIPane;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameView extends View {

  public GameView(
      EventBus eventBus,
      List<Player> players,
      List<MonopolyTile> tiles,
      EventHandler<ActionEvent> rollDiceHandler) {

    BorderPane root = new BorderPane();
    getChildren().add(root);

    // Center
    StackPane center = new StackPane();
    root.setCenter(center);
    GameBoard gameBoard = new GameBoard(eventBus, tiles);
    DiceView diceView = new DiceView();
    center.getChildren().addAll(gameBoard, diceView);
    addAllComponents(gameBoard, diceView);

    // Right
    VBox right = new VBox();
    root.setRight(right);
    UIPane uiPane = new UIPane(players);
    RollDiceButton rollDiceButton = new RollDiceButton(rollDiceHandler);
    right.getChildren().addAll(uiPane, rollDiceButton);
    addAllComponents(uiPane, rollDiceButton);

    // Bottom
    MessageLog messageLog = new MessageLog();
    root.setBottom(messageLog);
  }
}
