package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
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
import java.util.function.Consumer;

/**
 * @author Nick Heggø
 * @version 2025.05.09
 */
public class SnakeAndLadderGame extends Game<SnakeAndLadderPlayer, SnakeAndLadderTile> {

  protected SnakeAndLadderGame(
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

    tileActionOf(getTile(player.getPosition())).accept(player);

    if (diceRoll.getTotal() == 6) {
      println("Player %s rolled a 6, they are forced to move again.".formatted(player.getName()));
      rollAndMovePlayer(player);
    }
  }

  private Consumer<SnakeAndLadderPlayer> tileActionOf(SnakeAndLadderTile tile) {
    return switch (tile) {
      case SnakeTile(int tilesToSlideBack) -> snakeTileAction(tilesToSlideBack);
      case LadderTile(int tilesToSkip) -> ladderTileAction(tilesToSkip);
      case NormalTile unused -> player -> {};
    };
  }

  private Consumer<SnakeAndLadderPlayer> snakeTileAction(int tilesToSlideBack) {
    return player -> {
      movePlayer(player, tilesToSlideBack);
      println(
          "Player %s moved to tile %d (slide back %d tiles)"
              .formatted(player.getName(), player.getPosition(), tilesToSlideBack));
    };
  }

  private Consumer<SnakeAndLadderPlayer> ladderTileAction(int tilesToSkip) {
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
}
