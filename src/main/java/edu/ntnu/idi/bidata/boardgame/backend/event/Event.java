package edu.ntnu.idi.bidata.boardgame.backend.event;

import java.util.Objects;
import java.util.UUID;

public record Event(Type type, UUID playerId, Object payload) {

  public Event {
    Objects.requireNonNull(type, "Type must not be null");
    Objects.requireNonNull(payload, "Payload must not be null");
  }

  public enum Type {
    DICE_ROLLED,
    DISPLAY_TEXT,
    PLAYER_MOVED,
    PLAYER_REMOVED,
    PURCHASED_OWNABLE,
  }
}
