package edu.ntnu.idi.bidata.boardgame.common.event.type;

import java.util.Objects;

/**
 * @param output the output string to be displayed, must not be null
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record OutputEvent(String output) implements Event {
  public OutputEvent {
    Objects.requireNonNull(output, "Output string cannot be null!");
  }
}
