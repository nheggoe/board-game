package edu.ntnu.idi.bidata.boardgame.common.event;

import java.util.Objects;

public record OutputEvent(String payload) implements Event {
  public OutputEvent {
    Objects.requireNonNull(payload, "Output string cannot be null!");
  }
}
