package edu.ntnu.idi.bidata.boardgame.games.monopoly.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.DiceView;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessagePanel;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MonopolyBoardView;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.PlayerDashboard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.RollDiceButton;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Nick Heggø
 * @version 2025.05.07
 */
public class MonopolyGameView extends View {

  public MonopolyGameView(
      EventBus eventBus,
      List<MonopolyPlayer> players,
      List<MonopolyTile> tiles,
      EventHandler<ActionEvent> rollDiceHandler) {

    // root
    BorderPane root = new BorderPane();
    getChildren().add(root);
    root.prefWidthProperty().bind(widthProperty());
    root.prefHeightProperty().bind(heightProperty());

    // Center
    StackPane center = new StackPane();
    root.setCenter(center);
    MonopolyBoardView monopolyBoardView = new MonopolyBoardView(eventBus, players, tiles);
    DiceView diceView = new DiceView(eventBus);
    center.getChildren().addAll(monopolyBoardView, diceView);
    addComponents(monopolyBoardView, diceView);

    // Bottom
    MessagePanel messagePanel = new MessagePanel(eventBus);
    root.setBottom(messagePanel);
    addComponents(messagePanel);

    // Right
    VBox right = new VBox();
    root.setRight(right);
    PlayerDashboard playerDashboard = new PlayerDashboard(eventBus, players);
    RollDiceButton rollDiceButton = new RollDiceButton(rollDiceHandler);
    right.getChildren().addAll(playerDashboard, rollDiceButton);
    addComponents(playerDashboard, rollDiceButton);
  }
}
