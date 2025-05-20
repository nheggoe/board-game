package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseEventTest {

  private MonopolyPlayer player;
  private Property property;

  @BeforeEach
  void setup() {
    player = new MonopolyPlayer("John", Player.Figure.CAR) {};
    property = new Property("Test property", Property.Color.DARK_BLUE, 100);
  }

  @Test
  void testInvalidData() {
    assertDoesNotThrow(() -> new MonopolyEvent.Purchased(player, property));
    assertThrows(NullPointerException.class, () -> new MonopolyEvent.Purchased(null, property));
    assertThrows(NullPointerException.class, () -> new MonopolyEvent.Purchased(player, null));
  }

  @Test
  void testBasic() {
    var purchaseEvent = new MonopolyEvent.Purchased(player, property);
    assertEquals(player, purchaseEvent.monopolyPlayer());
    assertEquals(property, purchaseEvent.ownable());
  }
}
