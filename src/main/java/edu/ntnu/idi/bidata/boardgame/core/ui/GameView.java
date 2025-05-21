package edu.ntnu.idi.bidata.boardgame.core.ui;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.model.Tile;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MessagePanel;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.PlayerDashboard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.RollDiceButton;
import java.util.List;
import java.util.function.Supplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Nick Heggø
 * @version 2025.05.21
 */
public abstract class GameView<T extends Tile, P extends Player> extends View {

  protected GameView(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      Supplier<List<T>> tilesSupplier,
      Supplier<List<P>> playersSupplier,
      EventHandler<ActionEvent> rollDiceAction) {

    var root = new BorderPane();
    setRoot(root);

    root.setCenter(createCenterPane(eventBus, tilesSupplier, playersSupplier));
    root.setRight(createRightPane(sceneSwitcher, eventBus, playersSupplier, rollDiceAction));
    root.setBottom(createBottomPane(eventBus));
  }

  protected abstract Pane createCenterPane(
      EventBus eventBus, Supplier<List<T>> tiles, Supplier<List<P>> players);

  // {
  //   var center = new StackPane();
  //   var image = new Image("/images/monopoly-background.png");
  //   var imageView = new ImageView(image);
  //   imageView.setPreserveRatio(true);
  //   imageView.setFitHeight(600);
  //   imageView.setFitWidth(800);
  //   center.getChildren().add(imageView);
  //   center.prefWidthProperty().bind(this.widthProperty().multiply(0.6));
  //   center.prefHeightProperty().bind(this.heightProperty().multiply(0.6));
  //
  //   var backGround = new Background(new BackgroundFill(Color.BLACK, null, null));
  //   center.setBackground(backGround);
  //
  //   var monopolyBoardView = new MonopolyBoardView(eventBus, tiles, players);
  //   var diceView = new DiceView(eventBus);
  //   addComponents(monopolyBoardView, diceView);
  //   center.getChildren().addAll(monopolyBoardView, diceView);
  //   return center;
  // }

  private VBox createRightPane(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      Supplier<List<P>> players,
      EventHandler<ActionEvent> rollDiceHandler) {
    var right = new VBox();
    right.prefWidthProperty().bind(this.widthProperty().multiply(0.2));
    right.prefHeightProperty().bind(this.heightProperty().multiply(0.7));

    var settingButton = new SettingButton(sceneSwitcher);
    var playerDashboard = new PlayerDashboard<>(eventBus, players);
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
