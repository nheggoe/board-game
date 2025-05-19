package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import edu.ntnu.idi.bidata.boardgame.core.ui.Component;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * {@code DiceControlPane} provides a simple UI component containing the roll button, allowing
 * players to roll the dice during their turn.
 *
 * <p>When clicked, the button rolls the dice, animates the dice faces, moves the current player,
 * refreshes the UI, and advances to the next player.
 *
 * @author Mihailo
 * @version 2025.05.06
 */
public class RollDiceSnakeButton extends Component {

  /**
   * Constructs a {@code DiceControlPane} with a roll button that controls the dice animation,
   * logging, player movement, and UI refresh.
   */
  public RollDiceSnakeButton(EventHandler<ActionEvent> rollDiceAction) {
    Objects.requireNonNull(rollDiceAction);
    Button button = new Button("Roll Dice");
    ImageView icon = new ImageView(new Image("/images/rolldice.png"));
    icon.setFitWidth(48);
    icon.setFitHeight(48);
    button.setGraphic(icon);
    button.setOnAction(rollDiceAction);
    getChildren().add(button);
  }
}
