package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static java.util.Objects.requireNonNull;

/**
 * @param output the output string to be displayed, must not be null
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record OutputEvent(String output) implements Event {
  public OutputEvent {
    requireNonNull(output, "Output string cannot be null!");
  }
}
