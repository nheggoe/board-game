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
 * @author Nick Heggø, Mihailo Hranisavljevic
 * @version 2025.05.16
 */
public class SnakeAndLadderGame extends Game<SnakeAndLadderTile, SnakeAndLadderPlayer> {

  public SnakeAndLadderGame(
      EventBus eventBus, Board<SnakeAndLadderTile> board, List<SnakeAndLadderPlayer> players) {
    super(eventBus, board, players);
  }

  @Override
  public void nextTurn() {
    rollAndMovePlayer(getNextPlayer());
  }

  private void rollAndMovePlayer(SnakeAndLadderPlayer player) {
    var diceRoll = Dice.roll(1);
    movePlayer(player, diceRoll.getTotal());
    println("Player %s moved to tile %d".formatted(player.getName(), player.getPosition()));

    tileActionOf(getTile(player.getPosition())).execute(player);

    if (diceRoll.getTotal() == 6) {
      println("Player %s rolled a 6, they are forced to move again.".formatted(player.getName()));
      rollAndMovePlayer(player);
    }
  }

  private TileAction<SnakeAndLadderPlayer> tileActionOf(SnakeAndLadderTile tile) {
    return switch (tile) {
      case SnakeTile(int tilesToSlideBack) -> snakeTileAction(tilesToSlideBack);
      case LadderTile(int tilesToSkip) -> ladderTileAction(tilesToSkip);
      case NormalTile ignored -> player -> {};
    };
  }

  private TileAction<SnakeAndLadderPlayer> snakeTileAction(int tilesToSlideBack) {
    return player -> {
      movePlayer(player, tilesToSlideBack);
      println(
          "Player %s moved to tile %d (slide back %d tiles)"
              .formatted(player.getName(), player.getPosition(), tilesToSlideBack));
    };
  }

  private TileAction<SnakeAndLadderPlayer> ladderTileAction(int tilesToSkip) {
    return player -> {
      movePlayer(player, tilesToSkip);
      println(
          "Player %s moved to tile %d (skipped %d tiles)"
              .formatted(player.getName(), player.getPosition(), tilesToSkip));
      if (player.getPosition() == getBoard().size() - 1) {
        endGame();
      }
    };
  }

  @Override
  public SnakeAndLadderBoard getBoard() {
    return (SnakeAndLadderBoard) super.getBoard();
  }

  @Override
  protected void completeRoundAction(SnakeAndLadderPlayer player) {
    endGame();
  }

  @Override
  public Map.Entry<Integer, List<SnakeAndLadderPlayer>> getWinners() {
    var treeMap = new TreeMap<Integer, List<SnakeAndLadderPlayer>>();
    for (var player : getPlayers()) {
      treeMap.computeIfAbsent(player.getPosition(), unused -> new ArrayList<>()).add(player);
    }
    return treeMap.reversed().firstEntry();
  }

  /** Overrides the default {@code Game.movePlayer} implementation. */
  @Override
  public void movePlayer(SnakeAndLadderPlayer player, int delta) {
    int oldPosition = player.getPosition();
    int target = oldPosition + delta;

    int min = 0;
    int max = getBoard().size() - 1;
    if (target < min) target = min;
    if (target > max) target = max;

    player.setPosition(target);
    notifyPlayerMoved(player);

    if (target == max) {
      completeRoundAction(player);
    }
  }
}
