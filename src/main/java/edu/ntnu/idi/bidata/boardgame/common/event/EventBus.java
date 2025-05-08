package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @version 2025.05.06
 */
public final class EventBus implements EventPublisher {

  private static final Logger LOGGER = Logger.getLogger(EventBus.class.getName());

  private final Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();

  @Override
  public void addListener(Class<? extends Event> eventType, EventListener listener) {
    Objects.requireNonNull(eventType, "Event type cannot be null!");
    Objects.requireNonNull(listener, "Listener cannot be null!");
    listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
  }

  @Override
  public void removeListener(Class<? extends Event> eventType, EventListener listener) {
    Objects.requireNonNull(eventType, "Event type cannot be null!");
    Objects.requireNonNull(listener, "Listener cannot be null!");
    listeners.getOrDefault(eventType, List.of()).remove(listener);
  }

  @Override
  public void publishEvent(Event event) {
    Objects.requireNonNull(event, "event cannot be null!");
    LOGGER.info(() -> LocalDateTime.now() + " sent: " + event);
    listeners
        .getOrDefault(event.getClass(), List.of())
        .forEach(listener -> listener.onEvent(event));
  }
}
