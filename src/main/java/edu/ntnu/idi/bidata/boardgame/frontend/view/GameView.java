package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.event.Event;
import edu.ntnu.idi.bidata.boardgame.backend.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import edu.ntnu.idi.bidata.boardgame.frontend.component.DiceView;
import edu.ntnu.idi.bidata.boardgame.frontend.component.GamePane;
import edu.ntnu.idi.bidata.boardgame.frontend.component.MessageLog;
import edu.ntnu.idi.bidata.boardgame.frontend.component.RollDiceButton;
import edu.ntnu.idi.bidata.boardgame.frontend.component.UIPane;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameView extends BorderPane implements EventListener {

  private final StackPane center;
  private final GamePane gamePane;
  private final DiceView diceView;

  private final VBox right;
  private final UIPane uiPane;
  private final RollDiceButton rollDiceButton;
  private final MessageLog messageLog;

  public GameView(
      List<Player> players, List<Tile> tiles, EventHandler<ActionEvent> rollDiceHandler) {
    center = new StackPane();
    gamePane = new GamePane(tiles);
    diceView = new DiceView();
    setCenter(center);
    center.getChildren().addAll(gamePane, diceView);

    right = new VBox();
    uiPane = new UIPane(players);
    rollDiceButton = new RollDiceButton(rollDiceHandler);
    setRight(right);
    right.getChildren().addAll(uiPane, rollDiceButton);

    messageLog = new MessageLog();
    setBottom(messageLog);
  }

  @Override
  public void update(Event event) {
    switch (event.type()) {
      case DICE_ROLLED -> {
        if (event.payload() instanceof DiceRoll diceRoll) {
          messageLog.log(diceRoll);
        }
      }
      case DISPLAY_TEXT -> messageLog.log(event.payload());
      case PLAYER_MOVED -> {}
      case PLAYER_REMOVED -> {}
      case PURCHASED_OWNABLE -> {}
    }
  }
}
