package edu.ntnu.idi.bidata.boardgame.backend.event;

public interface EventPublisher {

  void addListener(EventListener listener);

  void removeListener(EventListener listener);

  void update(Event event);
}
