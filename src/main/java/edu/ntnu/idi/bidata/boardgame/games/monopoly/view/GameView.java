package edu.ntnu.idi.bidata.boardgame.games.monopoly.view;

import edu.ntnu.idi.bidata.boardgame.common.event.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.DiceView;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.GameBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessageLog;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.RollDiceButton;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.UIPane;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.dice.DiceRoll;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane implements EventListener {

  private final StackPane center;
  private final GameBoard gameBoard;
  private final DiceView diceView;

  private final VBox right;
  private final UIPane uiPane;
  private final RollDiceButton rollDiceButton;
  private final MessageLog messageLog;

  public GameView(
      List<Player> players, List<MonopolyTile> tiles, EventHandler<ActionEvent> rollDiceHandler) {
    center = new StackPane();
    gameBoard = new GameBoard(tiles);
    diceView = new DiceView();
    setCenter(center);
    center.getChildren().addAll(gameBoard, diceView);

    right = new VBox();
    uiPane = new UIPane(players);
    rollDiceButton = new RollDiceButton(rollDiceHandler);
    setRight(right);
    right.getChildren().addAll(uiPane, rollDiceButton);

    messageLog = new MessageLog();
    setBottom(messageLog);
  }

  @Override
  public void onEvent(Event event) {
    switch (event.type()) {
      case DICE_ROLLED -> {
        if (event.payload() instanceof DiceRoll diceRoll) {
          messageLog.log(diceRoll);
          diceView.animateDiceRoll(diceRoll);
        }
      }
      case DISPLAY_TEXT -> messageLog.log(event.payload());
      case PLAYER_MOVED -> {
        if (event.payload() instanceof Player player) {
          gameBoard.playerMoved(player, player.getPosition());
        }
      }
      case PLAYER_REMOVED -> {}
      case PURCHASED_OWNABLE -> {}
    }
  }
}
