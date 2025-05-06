package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.common.event.EventListener;
import java.util.Objects;

public abstract class EventListeningComponent extends Component implements EventListener {

  private final EventBus eventBus;

  protected EventListeningComponent(EventBus eventBus) {
    this.eventBus = Objects.requireNonNull(eventBus, "Event bus cannot be null!");
  }

  protected EventBus getEventBus() {
    return eventBus;
  }
}
