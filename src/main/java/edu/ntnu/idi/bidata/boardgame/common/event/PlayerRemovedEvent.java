package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import java.util.Objects;

public record PlayerRemovedEvent(Player payload) implements Event {
  public PlayerRemovedEvent {
    Objects.requireNonNull(payload, "Player cannot be null!");
  }
}
