package edu.ntnu.idi.bidata.boardgame.common.ui.view;

import edu.ntnu.idi.bidata.boardgame.common.ui.component.SettingButton;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.core.ui.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Represents the primary UI view for the application, providing functionality for navigating to
 * specific game-related views and accessing application settings.
 *
 * <p>The view is built using a {@link BorderPane} as the root layout and contains: - A center pane
 * with buttons for navigating to the "Snake and Ladder" and "Monopoly" game views. - A settings
 * button positioned on the right side of the layout.
 *
 * <p>Extends {@link View} to enable advanced layout management and resource handling.
 */
public class MainView extends View {

  /**
   * Constructs the MainView and initializes its layout components.
   *
   * <p>The MainView utilizes a {@link BorderPane} as the root layout. It assigns a center pane,
   * which contains buttons for navigating to "Snake and Ladder" and "Monopoly" game views.
   * Additionally, it places a settings button on the right side of the layout to handle application
   * settings.
   *
   * @param sceneSwitcher the {@code SceneSwitcher} used for managing scene transitions, must not be
   *     {@code null}
   * @param snake the {@code EventHandler<ActionEvent>} assigned to handle actions triggered by the
   *     "Snake and Ladder" button
   * @param monopoly the {@code EventHandler<ActionEvent>} assigned to handle actions triggered by
   *     the "Monopoly" button
   * @throws NullPointerException if any of the parameters are {@code null}
   */
  public MainView(
      SceneSwitcher sceneSwitcher,
      EventHandler<ActionEvent> snake,
      EventHandler<ActionEvent> monopoly) {
    var root = new BorderPane();
    setRoot(root);

    root.setCenter(createCenterPane(snake, monopoly));
    root.setRight(new SettingButton(sceneSwitcher));
  }

  private VBox createCenterPane(
      EventHandler<ActionEvent> snake, EventHandler<ActionEvent> monopoly) {
    var center = new VBox();
    center.setAlignment(Pos.CENTER);
    center.setSpacing(10);
    var monopolyButton = new Button("Monopoly");
    var snakeAndeLadderButton = new Button("Snake and Ladder");
    snakeAndeLadderButton.setOnAction(snake);
    monopolyButton.setOnAction(monopoly);
    center.getChildren().addAll(monopolyButton, snakeAndeLadderButton);
    return center;
  }
}
