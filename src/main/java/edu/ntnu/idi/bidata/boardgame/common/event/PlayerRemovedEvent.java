package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import java.util.Objects;

public record PlayerRemovedEvent(Player player) implements Event {
  public PlayerRemovedEvent {
    Objects.requireNonNull(player, "Player cannot be null!");
  }
}
