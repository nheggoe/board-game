package edu.ntnu.idi.bidata.boardgame.core.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nick Heggø
 * @version 2025.05.09
 */
public class TurnManager<P extends Player> {

  private final List<P> players;
  private int roundNumber = 0;
  private Iterator<P> iterator;
  private P currentPlayer;

  protected TurnManager(List<P> players) {
    requireNonNull(players, "Players cannot be null!");
    this.players = new ArrayList<>(players);
    this.iterator = players.iterator();
    this.currentPlayer = players.getFirst();
  }

  protected P getCurrentPlayer() {
    return currentPlayer;
  }

  protected P getNextPlayer() {
    if (!iterator.hasNext()) {
      iterator = players.iterator();
      roundNumber++;
    }
    currentPlayer = iterator.next();
    return currentPlayer;
  }

  protected void removePlayer(Player player) {
    requireNonNull(player, "Player to remove cannot be null!");
    if (player.equals(currentPlayer)) {
      iterator.remove();
    } else {
      // new iterator
      iterator = players.iterator();
      while (iterator.hasNext()) {
        if (iterator.next().equals(player)) {
          iterator.remove();
        }
      }
      // reset back to the current position
      iterator = players.iterator();
      while (iterator.hasNext() && !iterator.next().equals(currentPlayer)) {}
    }
  }

  protected int getRoundNumber() {
    return roundNumber;
  }

  protected List<P> getPlayers() {
    return List.copyOf(players);
  }
}
