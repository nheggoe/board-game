package edu.ntnu.idi.bidata.boardgame.common.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A utility class that implements the {@link EventPublisher} interface to provide centralized event
 * management and facilitate communication between different components in an event-driven system.
 *
 * <p>The EventBus allows registering listeners for specific event types, removing listeners, and
 * publishing events to notify all subscribed listeners of a particular event type. It ensures a
 * decoupled system where producers and consumers of events do not need to have direct knowledge of
 * each other.
 *
 * <p>This implementation uses a map to store listeners keyed by the event types they subscribe to.
 * It ensures type safety and supports multiple listeners per event type.
 *
 * <p>Implements: - {@link EventPublisher} interface, which defines the core methods for managing
 * event listeners and publishing events.
 *
 * <p>Methods: - {@code addListener(Class<? extends Event> eventType, EventListener listener)}:
 * Registers a listener for a specific event type, ensuring that the listener receives notifications
 * when events of the given type are published.
 *
 * <p>- {@code removeListener(Class<? extends Event> eventType, EventListener listener)}: Removes a
 * previously registered listener for the specified event type.
 *
 * <p>- {@code publishEvent(Event event)}: Publishes an event to all listeners that have been
 * registered for the type of the given event.
 *
 * <p>Usage Considerations: - Ensure to handle any exceptions within listener implementations to
 * prevent disruptions during event publication. - All arguments passed to the methods are validated
 * for nullity.
 *
 * <p>Thread Safety: - This implementation is not thread-safe. If multiple threads access the
 * EventBus concurrently, synchronization mechanisms must be externally provided.
 *
 * <p>Design Note: - The EventBus promotes loose coupling and enables pluggable and reusable
 * components in systems that adhere to an event-driven architecture.
 */
public final class EventBus implements EventPublisher {

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
    listeners
        .getOrDefault(event.getClass(), List.of())
        .forEach(listener -> listener.onEvent(event));
  }
}
