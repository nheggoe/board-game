package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * DemoView displays a basic UI to test the UIPane, DiceView, and MessageLog with a sample game
 * state.
 */
public class DemoView extends Scene {

  public DemoView() {
    super(createContent(), 1000, 700);
  }

  private static BorderPane createContent() {
    Player p1 = new Player("Alice", Player.Figure.CAT);
    Player p2 = new Player("Bob", Player.Figure.HAT);
    p1.addBalance(1500);
    p2.addBalance(1200);

    Game game = new Game(BoardFactory.generateBoard(BoardFactory.Layout.EASY), List.of(p1, p2));

    UIPane uiPane = new UIPane(game);
    MessageLog log = new MessageLog();
    DiceView diceView = new DiceView(2);

    log.log("Game started. Welcome Alice and Bob!");

    // Create Roll Button, pass all needed references
    Button rollButton = getRollButton(game, diceView, log, uiPane);

    VBox sidebar = new VBox(20, rollButton, uiPane);
    sidebar.setPadding(new Insets(10));
    sidebar.setAlignment(Pos.TOP_CENTER);

    BorderPane root = new BorderPane();
    root.setRight(sidebar);
    root.setBottom(log.getRoot());
    root.setCenter(diceView);

    return root;
  }

  private static Button getRollButton(Game game, DiceView diceView, MessageLog log, UIPane uiPane) {
    Button rollButton = new Button();
    ImageView rollIcon = new ImageView(new Image("/images/rolldice.png"));
    rollIcon.setFitWidth(48);
    rollIcon.setFitHeight(48);
    rollButton.setGraphic(rollIcon);
    rollButton.setStyle("-fx-background-color: transparent;");
    rollButton.setPadding(new Insets(5));

    rollButton.setOnAction(
        e -> {
          Player currentPlayer = game.getCurrentPlayer();
          DiceRoll roll = Dice.getInstance().roll(2);

          // Disable button during animation to prevent spamming
          rollButton.setDisable(true);

          diceView.rollDiceAnimated(
              roll,
              () -> {
                log.log(
                    currentPlayer.getName()
                        + " rolled: "
                        + Arrays.toString(roll.rolls())
                        + " (Total: "
                        + roll.getTotal()
                        + ")");
                game.movePlayer(currentPlayer, roll);
                uiPane.refresh();
                game.nextPlayer();
                rollButton.setDisable(false);
              });
        });

    return rollButton;
  }
}
