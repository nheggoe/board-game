package edu.ntnu.idi.bidata.boardgame.core.ui;

import static java.util.Objects.requireNonNull;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;

/**
 * @author Nick Heggø
 * @version 2025.05.08
 */
public abstract class EventListeningComponent extends Component implements EventListener {

  private final EventBus eventBus;

  protected EventListeningComponent(EventBus eventBus) {
    this.eventBus = requireNonNull(eventBus, "Event bus cannot be null!");
  }

  protected EventBus getEventBus() {
    return eventBus;
  }
}
