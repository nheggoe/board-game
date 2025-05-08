package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import org.junit.jupiter.api.Test;

class PlayerMovedEventTest {

  @Test
  void testInvalidData() {
    assertThrows(NullPointerException.class, () -> new PlayerMovedEvent(null));
  }

  @Test
  void testBasic() {
    var player = new Player("John", Player.Figure.CAR) {};
    var playerMovedEvent = new PlayerMovedEvent(player);
    assertEquals(player, playerMovedEvent.player());
  }
}
