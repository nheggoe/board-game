package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code JailTile} represents a jail tile on the game board.
 *
 * <p>Players can be sent to jail for a number of rounds, during which their movement may be
 * restricted. This class tracks which players are jailed and how many rounds they have left.
 *
 * <p>Extends {@link CornerTile}.
 *
 * @author Mihailo
 * @version 2025.04.25
 */
public final class JailTile extends CornerTile {

  private final Map<Player, Integer> players;

  /**
   * Constructs a {@code JailTile} at the given board position.
   *
   * @param position the board position of the jail tile
   */
  public JailTile(Position position) {
    super(position);
    players = new HashMap<>();
  }

  /**
   * Puts a player in jail for a specified number of rounds.
   *
   * @param player the player to send to jail
   * @param numberOfRounds the number of turns the player must remain jailed
   * @throws IllegalStateException if the player is already in jail
   */
  public void jailForNumberOfRounds(Player player, int numberOfRounds) {
    if (players.containsKey(player)) {
      throw new IllegalStateException("Player is already in jail!");
    }
    players.put(player, numberOfRounds);
  }

  /**
   * Decrements the number of jail rounds left for a player.
   *
   * <p>If the player has no remaining rounds after decrementing, they are released from jail.
   *
   * @param player the player to advance their jail time
   * @throws IllegalStateException if the player is not currently jailed
   */
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
