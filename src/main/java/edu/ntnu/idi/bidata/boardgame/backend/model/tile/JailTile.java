package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import java.util.HashMap;
import java.util.Map;

public final class JailTile extends CornerTile {

  private final Map<Player, Integer> players;

  public JailTile(Position position) {
    super(position);
    players = new HashMap<>();
  }

  public void jailForNmberOfRounds(Player player, int numberOfRounds) {
    if (players.containsKey(player)) {
      throw new IllegalStateException("Player is already in jail!");
    }
    players.put(player, numberOfRounds);
  }

  public void pass(Player player) {
    if (!players.containsKey(player)) {
      throw new IllegalStateException("Player is not in jail!");
    }
    players.merge(player, -1, Integer::sum);
    if (players.get(player) == 0) {
      players.remove(player);
    }
  }
}
