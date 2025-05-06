package edu.ntnu.idi.bidata.boardgame.common.event;

import java.util.Objects;

public record OutputEvent(String output) implements Event {
  public OutputEvent {
    Objects.requireNonNull(output, "Output string cannot be null!");
  }
}
