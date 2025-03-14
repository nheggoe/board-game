package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Tile;
import org.junit.jupiter.api.Test;

class TileTest {

  @Test
  void testGetPosition() {
    Tile tile = new Tile(1, "A");
    assertEquals(1, tile.getPosition());
  }

  @Test
  void testSetPosition() {
    Tile tile = new Tile(1, "A");
    tile.setPosition(2);
    assertEquals(2, tile.getPosition());
  }

  @Test
  void testGetName() {
    Tile tile = new Tile(1, "A");
    assertEquals("A", tile.getName());
  }

  @Test
  void testSetName() {
    Tile tile = new Tile(1, "A");
    tile.setName("B");
    assertEquals("B", tile.getName());
  }
}
