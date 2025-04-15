package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
  /*

    @Test
    void generateDefaultLayout() {
      Board board = new Board();
      assertEquals(0, board.getTile(0).getPosition());
      assertEquals("Start", board.getTile(0).getTileName());
      assertEquals("Tile 90", board.getTile(89).getTileName());
    }

    @Test
    void getTile() {
      Board board = new Board();
      Tile tile = board.getTile(0);
      assertNotNull(tile);
      assertEquals(0, tile.getPosition());
      assertEquals("Start", tile.getTileName());

      Tile tileLast = board.getTile(89);
      assertNotNull(tileLast);
      assertEquals(89, tileLast.getPosition());
      assertEquals("Tile 90", tileLast.getTileName());
    }

    @Test
    void getTileInvalidPosition() {
      Board board = new Board();
      assertThrows(IllegalArgumentException.class, () -> board.getTile(-1));
      assertThrows(IllegalArgumentException.class, () -> board.getTile(90));
    }
  */
}
