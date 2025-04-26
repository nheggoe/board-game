package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameObserver;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import java.util.UUID;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameView extends Scene implements GameObserver {

  public GameView(double width, double height) {
    super(new Pane(), width, height);
    var root = new BorderPane();
    var center = new StackPane();
    setRoot(root);

    root.setCenter(center);
    root.setRight(new UIPane());
    root.setBottom(MessageLog.getInstance());

    center.getChildren().add(new GamePane());
    center.getChildren().add(new DiceView(2));
  }

  @Override
  public void onPlayerMoved(UUID playerId, int oldPosition, int newPosition) {}

  @Override
  public void onPropertyPurchased(int playerId, int propertyId) {}

  @Override
  public void onDiceRolled(DiceRoll diceRoll) {}

  @Override
  public void onPlayerBalanceChanged(UUID playerId, int oldBalance, int newBalance) {}
}
