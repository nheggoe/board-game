package edu.ntnu.idi.bidata.boardgame.backend.model.property;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Figure;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyTest {

  private Property property;
  private Player player;

  @BeforeEach
  void setup() {
    property = new Property("Test property", 100) {}; // value property 100
    player = new Player("Duke", Figure.CAT); // player start with balance 0
  }

  @Test
  void testInvalidData() {
    assertThatThrownBy(() -> new Property("test", -1) {})
        .withFailMessage("Property with negative value should not be created")
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> new Property("", 10) {})
        .withFailMessage("Property with empty name should not be created")
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> new Property(null, 0) {})
        .withFailMessage("Property name cannot be null")
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testBasic() {
    assertThat(property.getName()).isEqualTo("Test property");
    assertThat(property.getOwner().getName()).isEqualTo("Bank");
  }

  @Test
  void testPurchaseProperty() {
    player.addBalance(100);

    assertThatCode(() -> property.transferOwnership(player, property.getValue()))
        .doesNotThrowAnyException();

    assertThat(player.getBalance()).isZero();
  }

  @Test
  void testPurchasePropertyWithInefficientBalance() {
    assertThatThrownBy(() -> property.transferOwnership(player, property.getValue()))
        .isInstanceOf(InsufficientFundsException.class);

    assertThat(player.getBalance()).isZero();
  }
}
