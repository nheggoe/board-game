package edu.ntnu.idi.bidata.boardgame.common.event;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.common.event.type.Event;
import edu.ntnu.idi.bidata.boardgame.common.event.type.PlayerRemovedEvent;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakePlayer;
import org.junit.jupiter.api.Test;

class EventBusTest {

  private final EventListener listener =
      new EventListener() {
        @Override
        public void onEvent(Event event) {
          throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void close() throws Exception {}
      };

  @Test
  void testBasic() {
    var eventBus = new EventBus();
    assertNotNull(eventBus);
  }

  @Test
  void testNullListener() {
    var eventBus = new EventBus();
    assertDoesNotThrow(() -> eventBus.addListener(PlayerRemovedEvent.class, listener));
    assertThrows(
        NullPointerException.class, () -> eventBus.addListener(PlayerRemovedEvent.class, null));
    assertThrows(NullPointerException.class, () -> eventBus.addListener(null, listener));
  }

  @Test
  void testRemoveListener() {
    var eventBus = new EventBus();
    eventBus.addListener(PlayerRemovedEvent.class, listener);
    assertDoesNotThrow(() -> eventBus.removeListener(PlayerRemovedEvent.class, listener));
    assertThrows(
        NullPointerException.class, () -> eventBus.removeListener(PlayerRemovedEvent.class, null));
    assertThrows(NullPointerException.class, () -> eventBus.removeListener(null, listener));
  }

  @Test
  void testPublishEvent() {
    var eventBus = new EventBus();
    eventBus.addListener(PlayerRemovedEvent.class, listener);
    var player = new SnakePlayer("John", Player.Figure.CAR);
    var event = new PlayerRemovedEvent(player);
    assertThrows(UnsupportedOperationException.class, () -> eventBus.publishEvent(event));
    assertThrows(NullPointerException.class, () -> eventBus.publishEvent(null));
  }
}
