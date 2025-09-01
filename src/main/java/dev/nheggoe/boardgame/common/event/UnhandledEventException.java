package dev.nheggoe.boardgame.common.event;

import dev.nheggoe.boardgame.common.event.type.Event;
import dev.nheggoe.boardgame.core.ui.EventListeningComponent;

/**
 * This exception is thrown when an event is published that has no handler configured to process it.
 *
 * <p>Thrown when an {@link EventListeningComponent} does not have logic to handle the subscribed
 * event types.
 *
 * @author Nick Heggø
 * @version 2025.05.21
 */
public class UnhandledEventException extends RuntimeException {

  /**
   * Constructs a new {@code UnhandledEventException} with the specified event.
   *
   * <p>This exception is thrown to indicate that the provided event type is unsupported or
   * unhandled by the system. The exception includes the class name of the event to aid debugging
   * and identification of unhandled scenarios.
   *
   * @param event the event instance that triggered this exception; must not be null
   */
  public UnhandledEventException(Event event) {
    super("Unhandled event type: " + event.getClass().getSimpleName());
  }
}
