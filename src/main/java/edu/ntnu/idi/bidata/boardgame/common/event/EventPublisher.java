package edu.ntnu.idi.bidata.boardgame.common.event;

public interface EventPublisher {

  void addListener(Class<? extends Event> eventType, EventListener listener);

  void removeListener(Class<? extends Event> eventType, EventListener listener);

  void publishEvent(Event event);
}
