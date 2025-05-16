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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author Nick Heggø
 * @version 2025.05.16
 */
public class MonopolyGameView extends View {

  public MonopolyGameView(
      EventBus eventBus,
      List<MonopolyPlayer> players,
      List<MonopolyTile> tiles,
      EventHandler<ActionEvent> rollDiceHandler) {

    // root
    var root = new BorderPane();
    getChildren().add(root);
    root.prefWidthProperty().bind(this.widthProperty());
    root.prefHeightProperty().bind(this.heightProperty());

    // Center
    var center = new StackPane();
    var backGround = new Background(new BackgroundFill(Color.BLACK, null, null));
    center.setBackground(backGround);
    root.setCenter(center);
    center.prefWidthProperty().bind(this.widthProperty().multiply(0.6));
    center.prefHeightProperty().bind(this.heightProperty().multiply(0.7));

    var monopolyBoardView = new MonopolyBoardView(eventBus, players, tiles);
    var diceView = new DiceView(eventBus);
    addComponents(monopolyBoardView, diceView);

    center.getChildren().addAll(monopolyBoardView, diceView);

    // Bottom
    var messagePanel = new MessagePanel(eventBus);
    messagePanel.prefHeightProperty().bind(this.heightProperty().multiply(0.28));
    addComponents(messagePanel);
    root.setBottom(messagePanel);

    // Right
    var right = new VBox();
    right.prefWidthProperty().bind(this.widthProperty().multiply(0.2));
    right.prefHeightProperty().bind(this.heightProperty().multiply(0.7));

    root.setRight(right);

    var playerDashboard = new PlayerDashboard(eventBus, players);
    var rollDiceButton = new RollDiceButton(rollDiceHandler);
    addComponents(playerDashboard, rollDiceButton);

    right.getChildren().addAll(playerDashboard, rollDiceButton);
  }
}
