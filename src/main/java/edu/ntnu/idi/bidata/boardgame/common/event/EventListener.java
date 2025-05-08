package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;

/**
 * An interface to represent an event listener that can respond to updates.
 *
 * <p>Classes implementing this interface must provide an implementation for the update method,
 * which is intended to define the actions that should be performed when the listener is notified of
 * an event.
 *
 * @see EventPublisher
 * @author Nick Heggø
 * @version 2025.04.02
 */
public interface EventListener extends AutoCloseable {
  void onEvent(Event event);
}
