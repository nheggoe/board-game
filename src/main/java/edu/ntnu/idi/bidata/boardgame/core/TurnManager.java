package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.backend.event.Event;
import edu.ntnu.idi.bidata.boardgame.backend.event.EventListener;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class TurnManager implements EventListener {

  private final List<UUID> playerIds;
  private UUID currentPlayerId;
  private int roundNumber = 0;

  public TurnManager(List<UUID> playerIds) {
    Objects.requireNonNull(playerIds, "Players list cannot be null");
    if (playerIds.isEmpty()) {
      throw new IllegalArgumentException("Players list cannot be empty");
    }
    this.playerIds = playerIds;
    currentPlayerId = playerIds.getFirst();
  }

  public void removePlayer(UUID playerId) {
    Objects.requireNonNull(playerIds, "Player to remove cannot be null!");
    if (currentPlayerId.equals(playerId)) {
      int currentPlayerIndex = playerIds.indexOf(playerId);
      if (currentPlayerIndex == -1) {
        throw new NoSuchElementException("Player not found!");
      }
      if (currentPlayerIndex == 0) {
        nextTurn();
      } else {
        currentPlayerId = playerIds.get(currentPlayerIndex - 1);
      }
    }
    playerIds.removeIf(p -> p.equals(playerId));
  }

  @Override
  public void onEvent(Event event) {
    if (event.type() == Event.Type.PLAYER_REMOVED && event.payload() instanceof UUID playerId) {
      removePlayer(playerId);
    }
  }

  public void nextTurn() {
    if (playerIds.isEmpty()) {
      throw new NoSuchElementException("No players in the game!");
    }
    int currentPlayerIndex = playerIds.indexOf(currentPlayerId);
    int nextPlayerIndex = (currentPlayerIndex + 1) % playerIds.size();
    if (nextPlayerIndex < currentPlayerIndex) {
      roundNumber++;
    }
    currentPlayerId = playerIds.get((currentPlayerIndex + 1) % playerIds.size());
  }

  public UUID getCurrentPlayerId() {
    return currentPlayerId;
  }

  public int getRoundNumber() {
    return roundNumber;
  }
}
