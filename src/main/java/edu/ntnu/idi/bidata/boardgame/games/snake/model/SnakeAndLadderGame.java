package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.model.Board;
import edu.ntnu.idi.bidata.boardgame.core.model.Game;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
