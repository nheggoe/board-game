package edu.ntnu.idi.bidata.boardgame.backend.model.property;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Figure;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyTest {
  private Property property;

  @BeforeEach
  void setup() {
    property = new Property("Test property", 100) {};
  }

  @Test
  void testBasic() {
    assertThat(property.getName()).isEqualTo("Test property");
    assertThat(property.getOwner().getName()).isEqualTo("Bank");
  }

  @Test
  void testPurchaseProperty() {
    Player player = new Player("Duke", Figure.CAT);
    player.addBalance(100);
    assertThatCode(() -> property.transferOwnership(player, property.getValue()))
        .doesNotThrowAnyException();
    assertThat(player.getBalance()).isZero();
  }

  @Test
  void testPurchasePropertyWithInefficientBalance() {
    Player player = new Player("Duke", Figure.CAT);
    assertThatThrownBy(() -> property.transferOwnership(player, property.getValue()))
        .isInstanceOf(InsufficientFundsException.class);
    assertThat(player.getBalance()).isZero();
  }
}
