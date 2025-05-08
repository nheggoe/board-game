package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import java.util.Objects;

public record PlayerMovedEvent(Player player) implements Event {
  public PlayerMovedEvent {
    Objects.requireNonNull(player, "Player cannot be null!");
  }
}
