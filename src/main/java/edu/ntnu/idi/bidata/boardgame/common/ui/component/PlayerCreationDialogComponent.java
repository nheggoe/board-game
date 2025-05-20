package edu.ntnu.idi.bidata.boardgame.common.ui.component;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayersRequiredEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * UI component that handles player creation dialogs. Listens for PlayersRequiredEvent and displays
 * appropriate dialogs.
 */
public class PlayerCreationDialogComponent extends EventListeningComponent {

  public PlayerCreationDialogComponent(EventBus eventBus) {
    super(eventBus);
    // Listen for player creation requests
    getEventBus().addListener(PlayersRequiredEvent.class, this);
  }

  @Override
  public void onEvent(Event event) {
    if (event instanceof PlayersRequiredEvent<?> playersEvent) {
      // Use Platform.runLater to ensure UI operations happen on the JavaFX application thread
      Platform.runLater(() -> handlePlayerCreation(playersEvent));
    }
  }

  private <P extends Player> void handlePlayerCreation(PlayersRequiredEvent<P> event) {
    Class<P> playerType = event.playerType();
    Consumer<List<P>> callback = event.playerConsumer();

    // Ask how many players to create
    int playerCount = promptForPlayerCount();
    if (playerCount <= 0) {
      return;
    }

    List<P> newPlayers = new ArrayList<>();

    // Show dialog for each player
    for (int i = 0; i < playerCount; i++) {
      Optional<P> playerOpt = showPlayerCreationDialog(playerType, i + 1);
      if (playerOpt.isPresent()) {
        newPlayers.add(playerOpt.get());
      } else {
        // User cancelled, abort whole operation
        return;
      }
    }

    // Return the created players through the callback
    callback.accept(newPlayers);
  }

  private int promptForPlayerCount() {
    ChoiceDialog<Integer> dialog = new ChoiceDialog<>(2, List.of(1, 2, 3, 4, 5, 6));
    dialog.setTitle("Player Setup");
    dialog.setHeaderText("No players found in save file");
    dialog.setContentText("How many players would you like to create?");

    Optional<Integer> result = dialog.showAndWait();
    return result.orElse(0);
  }

  private <P extends Player> Optional<P> showPlayerCreationDialog(
      Class<P> playerType, int playerNumber) {
    // Create a custom dialog for player creation
    Dialog<P> dialog = new Dialog<>();
    dialog.setTitle("Create Player " + playerNumber);
    dialog.setHeaderText("Enter player details");

    // Set the button types
    ButtonType createButtonType = new ButtonType("Create");
    dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

    // Create the name and figure fields
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    TextField nameField = new TextField();
    nameField.setPromptText("Name");

    // Create dropdown for figures
    ChoiceDialog<Player.Figure> figureDialog =
        new ChoiceDialog<>(Player.Figure.CAR, List.of(Player.Figure.values()));
    figureDialog.setTitle("Select Figure");
    figureDialog.setHeaderText(
        "Choose a figure for "
            + (nameField.getText().isEmpty() ? "player " + playerNumber : nameField.getText()));

    Label figureLabel = new Label("Click to select figure →");
    javafx.scene.control.Button figureButton = new javafx.scene.control.Button("Select Figure");
    figureButton.setOnAction(e -> figureDialog.showAndWait());

    grid.add(new Label("Name:"), 0, 0);
    grid.add(nameField, 1, 0);
    grid.add(new Label("Figure:"), 0, 1);
    grid.add(figureButton, 1, 1);
    grid.add(figureLabel, 2, 1);

    dialog.getDialogPane().setContent(grid);

    // Request focus on the name field by default
    Platform.runLater(nameField::requestFocus);

    // Convert the result to a player when the create button is clicked
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == createButtonType) {
            String name = nameField.getText();
            Optional<Player.Figure> figureOpt =
                figureDialog.getResult() != null
                    ? Optional.of(figureDialog.getResult())
                    : Optional.empty();

            if (name.isEmpty()) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Invalid Input");
              alert.setHeaderText("Name cannot be empty");
              alert.showAndWait();
              return null;
            }

            if (figureOpt.isEmpty()) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Invalid Input");
              alert.setHeaderText("Please select a figure");
              alert.showAndWait();
              return null;
            }

            try {
              // Create the player using reflection
              return createPlayer(playerType, name, figureOpt.get());
            } catch (Exception ex) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText("Failed to create player");
              alert.setContentText(ex.getMessage());
              alert.showAndWait();
              return null;
            }
          }
          return null;
        });

    return dialog.showAndWait();
  }

  @SuppressWarnings("unchecked")
  private <P extends Player> P createPlayer(
      Class<P> playerType, String name, Player.Figure figure) {
    try {
      if (playerType.getName().contains("MonopolyPlayer")) {
        return (P) new MonopolyPlayer(name, figure);
      } else if (playerType.getName().contains("SnakeAndLadderPlayer")) {
        return (P) new SnakeAndLadderPlayer(name, figure);
      } else {
        throw new IllegalArgumentException("Unsupported player type: " + playerType.getName());
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to create player: " + e.getMessage(), e);
    }
  }

  @Override
  public void close() {
    getEventBus().removeListener(PlayersRequiredEvent.class, this);
  }
}
