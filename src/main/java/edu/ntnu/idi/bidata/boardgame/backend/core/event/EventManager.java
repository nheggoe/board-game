package edu.ntnu.idi.bidata.boardgame.backend.core.event;

import java.util.ArrayList;
import java.util.List;

/**
 * The EventManager class is responsible for managing and notifying a list of event listeners. It
 * allows for the addition of listeners and ensures that all registered listeners are updated when
 * the update method is invoked.
 */
public class EventManager {
  private final List<EventListener> listeners;

  public EventManager() {
    listeners = new ArrayList<>();
  }

  public void addListener(EventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(EventListener listener) {
    listeners.remove(listener);
  }

  public void update() {
    listeners.forEach(EventListener::update);
  }
}
