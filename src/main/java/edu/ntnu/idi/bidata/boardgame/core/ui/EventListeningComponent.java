package edu.ntnu.idi.bidata.boardgame.core.ui;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public abstract class EventListeningComponent extends Component implements EventListener {

  private final EventBus eventBus;

  @SafeVarargs
  protected EventListeningComponent(EventBus eventBus, Class<? extends Event>... eventType) {
    this.eventBus = requireNonNull(eventBus, "Event bus cannot be null!");
    for (var type : eventType) {
      eventBus.addListener(type, this);
    }
  }

  protected EventBus getEventBus() {
    return eventBus;
  }
}
