package edu.ntnu.idi.bidata.boardgame.common.event.type;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import java.util.Objects;

/**
 * @param player the player that moved, must not be null
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record PlayerMovedEvent(Player player) implements Event {
  public PlayerMovedEvent {
    Objects.requireNonNull(player, "Player cannot be null!");
  }
}
