package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.TileAction;
import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.core.model.Game;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.core.ui.GameOverScreen;
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
 * @version 2025.05.19
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

    if (player.getPosition() == getBoard().size()) return;

    tileActionOf(getTile(player.getPosition())).execute(player);
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
    };
  }

  @Override
  public SnakeAndLadderBoard getBoard() {
    return (SnakeAndLadderBoard) super.getBoard();
  }

  @Override
  protected void completeRoundAction(SnakeAndLadderPlayer player) {
    endGame();
    GameOverScreen.show(player.getName());
  }

  @Override
  public Map.Entry<Integer, List<SnakeAndLadderPlayer>> getWinners() {
    var treeMap = new TreeMap<Integer, List<SnakeAndLadderPlayer>>();
    for (var player : getPlayers()) {
      treeMap.computeIfAbsent(player.getPosition(), unused -> new ArrayList<>()).add(player);
    }
    return treeMap.reversed().firstEntry();
  }

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
