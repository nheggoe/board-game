package edu.ntnu.idi.bidata.boardgame.backend.model.property;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
}
