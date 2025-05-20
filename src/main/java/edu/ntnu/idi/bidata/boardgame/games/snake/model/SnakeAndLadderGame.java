package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.TileAction;
import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.core.model.Game;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the core logic of a Snake and Ladder game. Handles player turns, movement, tile
 * effects, and game completion.
 *
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.20
 */
public class SnakeAndLadderGame extends Game<SnakeAndLadderTile, SnakeAndLadderPlayer> {

  /**
   * Constructs a new Snake and Ladder game.
   *
   * @param eventBus event bus for broadcasting game events
   * @param board the board layout with snake and ladder tiles
   * @param players list of players in the game
   */
  public SnakeAndLadderGame(
      EventBus eventBus, Board<SnakeAndLadderTile> board, List<SnakeAndLadderPlayer> players) {
    super(eventBus, board, players);
  }

  /** Executes the next player's turn by rolling the dice and applying tile effects. */
  @Override
  public void nextTurn() {
    rollAndMovePlayer(getNextPlayer());
  }

  /**
   * Rolls a die for the given player, moves them, and applies tile logic.
   *
   * @param player the player taking the turn
   */
  private void rollAndMovePlayer(SnakeAndLadderPlayer player) {
    var diceRoll = Dice.roll(1);
    movePlayer(player, diceRoll.getTotal());
    println("Player %s moved to tile %d".formatted(player.getName(), player.getPosition()));

    if (player.getPosition() == getBoard().size()) return;

    while (true) {
      SnakeAndLadderTile tile = getTile(player.getPosition() - 1);
      TileAction<SnakeAndLadderPlayer> action = tileActionOf(tile);

      int before = player.getPosition();
      action.execute(player);

      if (player.getPosition() == before || tile instanceof NormalTile) break;
    }
  }

  /**
   * Returns the tile action corresponding to the tile type.
   *
   * @param tile the tile the player landed on
   * @return the appropriate tile action
   */
  private TileAction<SnakeAndLadderPlayer> tileActionOf(SnakeAndLadderTile tile) {
    return switch (tile) {
      case SnakeTile(int tilesToSlideBack) -> snakeTileAction(tilesToSlideBack);
      case LadderTile(int tilesToSkip) -> ladderTileAction(tilesToSkip);
      case NormalTile ignored -> player -> {};
    };
  }

  /**
   * Returns a snake tile action that slides the player back.
   *
   * @param tilesToSlideBack number of tiles to move back
   * @return tile action applying the snake effect
   */
  private TileAction<SnakeAndLadderPlayer> snakeTileAction(int tilesToSlideBack) {
    return player -> {
      int from = player.getPosition();
      int to = Math.max(from - tilesToSlideBack, 1);
      player.setPosition(to);
      notifyPlayerMoved(player);
      println(
          "Player %s landed on Snake at tile %d and slid back to tile %d"
              .formatted(player.getName(), from, to));
    };
  }

  /**
   * Returns a ladder tile action that moves the player forward.
   *
   * @param tilesToSkip number of tiles to move forward
   * @return tile action applying the ladder effect
   */
  private TileAction<SnakeAndLadderPlayer> ladderTileAction(int tilesToSkip) {
    return player -> {
      int from = player.getPosition();
      int to = Math.min(from + tilesToSkip, getBoard().size());
      player.setPosition(to);
      notifyPlayerMoved(player);
      println(
          "Player %s landed on Ladder at tile %d and climbed to tile %d"
              .formatted(player.getName(), from, to));
    };
  }

  /**
   * Casts the base board to a SnakeAndLadderBoard.
   *
   * @return the cast board
   */
  @Override
  public SnakeAndLadderBoard getBoard() {
    return (SnakeAndLadderBoard) super.getBoard();
  }

  /**
   * Completes the game when a player reaches the final tile.
   *
   * @param player the player who triggered the game end
   */
  @Override
  protected void completeRoundAction(SnakeAndLadderPlayer player) {
    endGame();
  }

  /**
   * Determines the winner based on tile position.
   *
   * @return entry of tile position of the winning player
   */
  @Override
  public Map.Entry<Integer, List<SnakeAndLadderPlayer>> getWinners() {
    var treeMap = new TreeMap<Integer, List<SnakeAndLadderPlayer>>();
    for (var player : getPlayers()) {
      treeMap.computeIfAbsent(player.getPosition(), unused -> new ArrayList<>()).add(player);
    }
    return treeMap.reversed().firstEntry();
  }

  /**
   * Moves a player by a given number of tiles, respecting board boundaries.
   *
   * @param player the player to move
   * @param delta the number of tiles to move forward
   */
  @Override
  public void movePlayer(SnakeAndLadderPlayer player, int delta) {
    int oldPosition = player.getPosition();
    int target = oldPosition + delta;

    int max = getBoard().size();

    if (target >= max) {
      player.setPosition(max);
      notifyPlayerMoved(player);
      completeRoundAction(player);
      return;
    }

    if (target < 1) target = 1;

    player.setPosition(target);
    notifyPlayerMoved(player);
  }
}
