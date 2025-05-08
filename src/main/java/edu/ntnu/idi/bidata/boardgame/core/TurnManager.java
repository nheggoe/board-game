package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.common.event.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.common.event.PlayerRemovedEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class TurnManager implements EventListener {

  private final EventBus eventBus;
  private final List<UUID> playerIds;
  private UUID currentPlayerId;
  private int roundNumber = 0;

  public TurnManager(EventBus eventBus, List<UUID> playerIds) {
    this.eventBus = Objects.requireNonNull(eventBus, "Event bus cannot be null!");
    eventBus.addListener(PlayerRemovedEvent.class, this);
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

  @Override
  public void onEvent(Event event) {
    if (event instanceof PlayerRemovedEvent(Player player) && playerIds.contains(player.getId())) {
      playerIds.removeIf(p -> p.equals(player.getId()));
    }
  }

  @Override
  public void close() throws Exception {
    eventBus.removeListener(PlayerRemovedEvent.class, this);
  }
}
