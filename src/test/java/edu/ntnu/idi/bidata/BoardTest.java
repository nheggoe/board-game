package edu.ntnu.idi.bidata;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  @Test
  void testInitializeBoard() {
    Board board = new Board();
    assertEquals(4, board.getTile(0).getPosition());
    assertEquals("Start", board.getTile(0).getName());
    assertEquals(1, board.getTile(1).getPosition());
    assertEquals("A", board.getTile(1).getName());
    assertEquals(2, board.getTile(2).getPosition());
    assertEquals("B", board.getTile(2).getName());
    assertEquals(3, board.getTile(3).getPosition());
    assertEquals("C", board.getTile(3).getName());
  }

  @Test
  void testGetTile() {
    Board board = new Board();
    Tiles tile = board.getTile(1);
    assertNotNull(tile);
    assertEquals(1, tile.getPosition());
    assertEquals("A", tile.getName());
  }

  @Test
  void testGetTileInvalidPosition() {
    Board board = new Board();
    assertThrows(IllegalArgumentException.class, () -> board.getTile(-1));
    assertThrows(IllegalArgumentException.class, () -> board.getTile(4));
  }
}