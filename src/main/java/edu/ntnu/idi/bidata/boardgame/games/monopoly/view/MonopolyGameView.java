package edu.ntnu.idi.bidata.boardgame.games.monopoly.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      List<MonopolyPlayer> players,
      List<MonopolyTile> tiles,
      EventHandler<ActionEvent> rollDiceHandler) {

    var root = new BorderPane();
    setRoot(root);

    root.setCenter(createCenterPane(eventBus, players, tiles));
    root.setRight(createRightPane(sceneSwitcher, eventBus, players, rollDiceHandler));
    root.setBottom(createBottomPane(eventBus));
  }

  private StackPane createCenterPane(
      EventBus eventBus, List<MonopolyPlayer> players, List<MonopolyTile> tiles) {
    var center = new StackPane();
    var image = new Image("/images/monopoly-background.png");
    var imageView = new ImageView(image);
    imageView.setPreserveRatio(true);
    imageView.setFitHeight(600);
    imageView.setFitWidth(800);
    center.getChildren().add(imageView);
    center.prefWidthProperty().bind(this.widthProperty().multiply(0.6));
    center.prefHeightProperty().bind(this.heightProperty().multiply(0.6));

    var backGround = new Background(new BackgroundFill(Color.BLACK, null, null));
    center.setBackground(backGround);

    var monopolyBoardView = new MonopolyBoardView(eventBus, players, tiles);
    var diceView = new DiceView(eventBus);
    addComponents(monopolyBoardView, diceView);
    center.getChildren().addAll(monopolyBoardView, diceView);
    return center;
  }

  private VBox createRightPane(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      List<MonopolyPlayer> players,
      EventHandler<ActionEvent> rollDiceHandler) {
    var right = new VBox();
    right.prefWidthProperty().bind(this.widthProperty().multiply(0.2));
    right.prefHeightProperty().bind(this.heightProperty().multiply(0.7));

    var settingButton = new SettingButton(sceneSwitcher);
    var playerDashboard = new PlayerDashboard(eventBus, players);
    var rollDiceButton = new RollDiceButton(rollDiceHandler);
    addComponents(settingButton, playerDashboard, rollDiceButton);
    right.getChildren().addAll(settingButton, playerDashboard, rollDiceButton);
    return right;
  }

  private MessagePanel createBottomPane(EventBus eventBus) {
    var messagePanel = new MessagePanel(eventBus);
    messagePanel.prefHeightProperty().bind(this.heightProperty().multiply(0.28));
    addComponents(messagePanel);
    return messagePanel;
  }
}
