package edu.ntnu.idi.bidata.boardgame.frontend.view;

import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.DiceRoll;
import java.util.Arrays;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * {@code DiceControlPane} provides a simple UI component containing the roll button, allowing
 * players to roll the dice during their turn.
 *
 * <p>When clicked, the button rolls the dice, animates the dice faces, moves the current player,
 * refreshes the UI, and advances to the next player.
 *
 * @author Mihailo
 * @version 2025.04.25
 */
public class DiceControlPane extends HBox {

  /**
   * Constructs a {@code DiceControlPane} with a roll button that controls the dice animation,
   * logging, player movement, and UI refresh.
   *
   * @param game the game instance managing players and board state
   * @param diceView the {@link DiceView} for displaying dice roll animations
   * @param log the {@link MessageLog} to log roll results
   * @param uiPane the {@link UIPane} to refresh player information after moves
   */
  public DiceControlPane(Game game, DiceView diceView, MessageLog log, UIPane uiPane) {
    Button rollButton = new Button();
    ImageView icon = new ImageView(new Image("/images/rolldice.png"));
    icon.setFitWidth(48);
    icon.setFitHeight(48);
    rollButton.setGraphic(icon);

    rollButton.setOnAction(
        event -> {
          Player currentPlayer = game.getCurrentPlayer();
          DiceRoll roll = Dice.getInstance().roll(2);

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
                game.nextPlayer();
                uiPane.setCurrentPlayer(game.getCurrentPlayer());
                uiPane.refresh();
              });
        });

    this.getChildren().add(rollButton);
    this.setSpacing(10);
  }
}
