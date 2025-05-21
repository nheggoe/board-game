package edu.ntnu.idi.bidata.boardgame.games.snake.view;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.ui.GameView;
import edu.ntnu.idi.bidata.boardgame.core.ui.SceneSwitcher;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.PlayerRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.component.SnakeAndLadderBoardRender;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import java.util.List;
import java.util.function.Supplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * JavaFX view for the Snake & Ladder game. Displays the board and player icons, and manages
 * interaction with the underlying game engine.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.21
 */
public class SnakeGameView extends GameView<SnakeAndLadderTile, SnakeAndLadderPlayer> {

  /**
   * Constructs the SnakeGameView with necessary suppliers and event handlers.
   *
   * @param sceneSwitcher handles switching between different JavaFX scenes
   * @param eventBus the global event bus for dispatching and receiving game events
   * @param tilesSupplier supplies the list of board tiles
   * @param playersSupplier supplies the list of game players
   * @param rollDiceAction the event handler for rolling the dice
   */
  public SnakeGameView(
      SceneSwitcher sceneSwitcher,
      EventBus eventBus,
      Supplier<List<SnakeAndLadderTile>> tilesSupplier,
      Supplier<List<SnakeAndLadderPlayer>> playersSupplier,
      EventHandler<ActionEvent> rollDiceAction) {
    super(sceneSwitcher, eventBus, tilesSupplier, playersSupplier, rollDiceAction);
  }

  /**
   * Creates and returns the center pane of the game view, containing the board and players.
   *
   * @param eventBus the event bus
   * @param tiles supplier of game tiles
   * @param playersSupplier supplier of game players
   * @return the constructed JavaFX Pane
   */
  @Override
  protected Pane createCenterPane(
      EventBus eventBus,
      Supplier<List<SnakeAndLadderTile>> tiles,
      Supplier<List<SnakeAndLadderPlayer>> playersSupplier) {
    var centre = new VBox();
    centre.setAlignment(Pos.CENTER);

    var boardRender = new SnakeAndLadderBoardRender(eventBus, tiles);
    var playerRender =
        new PlayerRender(
            eventBus, boardRender.getTileGrid(), boardRender.getGridSize(), playersSupplier);

    addComponents(boardRender, playerRender);
    centre.getChildren().addAll(boardRender, playerRender);

    return centre;
  }
}
