package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.core.ui.EventListeningComponent;

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

  public UnhandledEventException(Event event) {
    super("Unhandled event type: " + event.getClass().getSimpleName());
  }
}
