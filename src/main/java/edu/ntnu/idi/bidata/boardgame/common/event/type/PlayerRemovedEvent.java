package edu.ntnu.idi.bidata.boardgame.common.event.type;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import java.util.Objects;

/**
 * @param player the player that was removed, must not be null
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record PlayerRemovedEvent(Player player) implements Event {
  public PlayerRemovedEvent {
    Objects.requireNonNull(player, "Player cannot be null!");
  }
}
