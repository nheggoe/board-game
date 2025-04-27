package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.TreeMap;

public class TurnManager implements Iterable<Player> {

  private final List<Player> players;
  private int currentPlayerIndex = -1;

  public TurnManager(List<Player> players) {
    Objects.requireNonNull(players, "Players list cannot be null");
    if (players.isEmpty()) {
      throw new IllegalArgumentException("Players list cannot be empty");
    }
    this.players = players;
  }

  /**
   * Retrieves the current player whose turn it is in the game.
   *
   * @return the {@code Player} object representing the current player
   * @throws NoSuchElementException if there are no players in the game
   */
  public Player getCurrentPlayer() {
    if (players.isEmpty()) {
      throw new NoSuchElementException("No players in the game!");
    }
    return players.get(currentPlayerIndex);
  }

  public Player next() {
    if (players.isEmpty()) {
      throw new NoSuchElementException("No players in the game!");
    }
    var nextPlayer = players.get((currentPlayerIndex + 1) % players.size());
    currentPlayerIndex = players.indexOf(nextPlayer);
    return nextPlayer;
  }

  /**
   * Removes the specified player from the list of players. Adjusts the current player index if the
   * removed player is before or at the current index. If the list becomes empty as a result, the
   * current index resets appropriately.
   *
   * @param player the player to be removed; must not be null
   * @throws NullPointerException if the given player is null
   */
  public void removePlayer(Player player) {
    Objects.requireNonNull(player, "Player to remove cannot be null!");
    players.removeIf(p -> p.equals(player));
    int indexToRemove = players.indexOf(player);
    if (indexToRemove != -1) {
      players.remove(indexToRemove);

      // Adjust current index if needed
      if (indexToRemove <= currentPlayerIndex) {
        currentPlayerIndex--;
        if (currentPlayerIndex < 0 && !players.isEmpty()) {
          currentPlayerIndex = players.size() - 1;
        }
      }
    }
  }

  /**
   * Determines whether the game is over based on the number of remaining players. The game is
   * considered over if there is one or no player left in the game.
   *
   * @return true if the number of players is less than or equal to one, false otherwise
   */
  public boolean isGameOver() {
    return players.size() <= 1;
  }

  public Map.Entry<Integer, List<Player>> getWinners() {
    var treeMap = new TreeMap<Integer, List<Player>>();
    players.forEach(
        player ->
            treeMap.computeIfAbsent(player.getNetWorth(), unused -> new ArrayList<>()).add(player));
    return treeMap.reversed().firstEntry();
  }

  @Override
  public Iterator<Player> iterator() {
    return players.iterator();
  }
}
