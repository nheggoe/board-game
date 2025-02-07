package edu.ntnu.idi.bidata;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  @Test
  void initializeBoard() {
    Board board = new Board();
    assertEquals(0, board.getTile(0).getPosition());
    assertEquals("Start", board.getTile(0).getName());
    assertEquals("Tile 90", board.getTile(89).getName());
  }

  @Test
  void getTile() {
    Board board = new Board();
    Tiles tile = board.getTile(0);
    assertNotNull(tile);
    assertEquals(0, tile.getPosition());
    assertEquals("Start", tile.getName());

    Tiles tileLast = board.getTile(89);
    assertNotNull(tileLast);
    assertEquals(89, tileLast.getPosition());
    assertEquals("Tile 90", tileLast.getName());
  }

  @Test
  void getTileInvalidPosition() {
    Board board = new Board();
    assertThrows(IllegalArgumentException.class, () -> board.getTile(-1));
    assertThrows(IllegalArgumentException.class, () -> board.getTile(90));
  }
}
