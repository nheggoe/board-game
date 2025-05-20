package edu.ntnu.idi.bidata.boardgame.games.snake.controller;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.util.GameFactory;
import edu.ntnu.idi.bidata.boardgame.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.ui.Controller;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoardFactory;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.view.SnakeSetupView;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 * Controller for the SnakeSetupView. Manages player input and transitions to the game view.
 *
 * @version 2025.05.19
 */
public class SnakeSetupController extends Controller {

  private final SnakeSetupView setupView;

  public SnakeSetupController(SceneSwitcher sceneSwitcher) {
    super(sceneSwitcher, new SnakeSetupView());
    this.setupView = (SnakeSetupView) getView();

    configureStartButton();
  }

  private void configureStartButton() {
    setupView
        .getStartGameButton()
        .setOnAction(
            event -> {
              var playerNames = setupView.getPlayerNames();

              if (playerNames.isEmpty()) {
                new Alert(
                        Alert.AlertType.ERROR, "Please add at least one player to start the game.")
                    .showAndWait();
                return;
              }

              List<SnakeAndLadderPlayer> players = createPlayers(playerNames);
              startSnakeGame(players);
            });
  }

  private List<SnakeAndLadderPlayer> createPlayers(List<String> playerNames) {
    List<SnakeAndLadderPlayer> players = new ArrayList<>();
    for (var name : playerNames) {
      players.add(
          new SnakeAndLadderPlayer(
              name, Player.Figure.BATTLE_SHIP)); // Assuming fixed figure for now
    }
    return players;
  }

  private void startSnakeGame(List<SnakeAndLadderPlayer> players) {
    var eventBus = new EventBus();
    var board = SnakeAndLadderBoardFactory.createBoard();
    var game = GameFactory.createSnakeGame(eventBus, players);
    var engine = new GameEngine<>(game); // wrap it

    var gameController = new SnakeGameController(getSceneSwitcher(), eventBus, engine, board);

    getSceneSwitcher().setRoot(gameController.getView()); // show game UI
  }
}
