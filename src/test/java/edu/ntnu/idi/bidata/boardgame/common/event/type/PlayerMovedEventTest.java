package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import org.junit.jupiter.api.Test;

class PlayerMovedEventTest {

  @Test
  void testInvalidData() {
    assertThrows(NullPointerException.class, () -> new CoreEvent.PlayerMoved(null));
  }

  @Test
  void testBasic() {
    var player = new Player("John", Player.Figure.CAR) {};
    var playerMovedEvent = new CoreEvent.PlayerMoved(player);
    assertEquals(player, playerMovedEvent.player());
  }
}
