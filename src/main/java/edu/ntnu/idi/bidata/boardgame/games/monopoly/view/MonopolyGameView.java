package edu.ntnu.idi.bidata.boardgame.games.monopoly.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.ui.GameView;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.DiceView;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.component.MonopolyBoardView;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import java.util.List;
import java.util.function.Supplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author Nick Heggø
 * @version 2025.05.16
 */
public class MonopolyGameView extends GameView<MonopolyTile, MonopolyPlayer> {

  public MonopolyGameView(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      Supplier<List<MonopolyTile>> tilesSupplier,
      Supplier<List<MonopolyPlayer>> playersSupplier,
      EventHandler<ActionEvent> rollDiceHandler) {
    super(sceneSwitcher, eventBus, tilesSupplier, playersSupplier, rollDiceHandler);
  }

  @Override
  protected Pane createCenterPane(
      EventBus eventBus,
      Supplier<List<MonopolyTile>> tiles,
      Supplier<List<MonopolyPlayer>> players) {
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
}
