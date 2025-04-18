package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.model.property.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyTileTest {

  private PropertyTile propertyTile;

  @BeforeEach
  void setup() {
    Property factory = new Property("Factory", 3000) {};
    propertyTile = new PropertyTile(8, factory);
  }

  @Test
  void testPropertyTileAction() {}
}
