package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import org.junit.jupiter.api.Test;

class PlayerRemovedEventTest {

  @Test
  void testInvalidData() {
    assertThrows(NullPointerException.class, () -> new CoreEvent.PlayerRemoved(null));
  }

  @Test
  void testBasic() {
    var player = new Player("John", Player.Figure.CAR) {};
    var playerRemovedEvent = new CoreEvent.PlayerRemoved(player);
    assertEquals(player, playerRemovedEvent.player());
  }
}
