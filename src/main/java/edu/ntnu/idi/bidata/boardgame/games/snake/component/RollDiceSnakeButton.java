package edu.ntnu.idi.bidata.boardgame.games.snake.component;

import edu.ntnu.idi.bidata.boardgame.core.ui.Component;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A small component that shows a “Roll Dice” button with an icon.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.05.19
 */
public class RollDiceSnakeButton extends Component {

  private final Button button;

  public RollDiceSnakeButton(EventHandler<ActionEvent> rollDiceAction) {
    Objects.requireNonNull(rollDiceAction);

    button = new Button("Roll Dice");
    var url = getClass().getResource("/images/rolldice.png");
    Objects.requireNonNull(url, "Missing resource: /images/rolldice.png");
    ImageView icon = new ImageView(new Image(url.toExternalForm()));
    icon.setFitWidth(48);
    icon.setFitHeight(48);
    button.setGraphic(icon);
    button.setOnAction(rollDiceAction);

    getChildren().add(button);
  }

  /** Allows external code to replace the button’s action handler. */
  public void setOnAction(EventHandler<ActionEvent> handler) {
    button.setOnAction(handler);
  }
}
