package edu.ntnu.idi.bidata.boardgame.core.ui;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public abstract class EventListeningComponent extends Component implements EventListener {

  private final EventBus eventBus;
  private final List<Class<? extends Event>> subscribedEvents;

  @SafeVarargs
  protected EventListeningComponent(EventBus eventBus, Class<? extends Event>... eventType) {
    this.eventBus = requireNonNull(eventBus, "Event bus cannot be null!");
    this.subscribedEvents = List.copyOf(Arrays.asList(eventType));
    subscribedEvents.forEach(event -> eventBus.addListener(event, this));
  }

  @Override
  public void close() {
    subscribedEvents.forEach(event -> eventBus.removeListener(event, this));
  }

  protected EventBus getEventBus() {
    return eventBus;
  }
}
