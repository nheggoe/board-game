package edu.ntnu.idi.bidata;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TilesTest {

  @Test
  void testGetPosition() {
    Tiles tile = new Tiles(1, "A");
    assertEquals(1, tile.getPosition());
  }

  @Test
  void testSetPosition() {
    Tiles tile = new Tiles(1, "A");
    tile.setPosition(2);
    assertEquals(2, tile.getPosition());
  }

  @Test
  void testGetName() {
    Tiles tile = new Tiles(1, "A");
    assertEquals("A", tile.getName());
  }

  @Test
  void testSetName() {
    Tiles tile = new Tiles(1, "A");
    tile.setName("B");
    assertEquals("B", tile.getName());
  }
}