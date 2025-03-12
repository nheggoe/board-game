package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Board;
import edu.ntnu.idi.bidata.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  private Player player;

  @BeforeEach
  void setUp() {
    Board board = new Board();
    player = new Player("Nick", board);
  }

  @Test
  void testPlayerInitialization() {
    assertEquals("Nick", player.getName());
    assertEquals(0, player.getCurrentTile().getPosition());
    assertEquals("Start", player.getCurrentTile().getName());
  }

  @Test
  void testMoveProperly() {
    player.move(5, new Board());
    assertEquals(5, player.getCurrentTile().getPosition());
  }

  @Test
  void testMoveBeyondBoardLimit() {
    player.move(100, new Board());
    assertEquals(89, player.getCurrentTile().getPosition());
  }

  @Test
  void testMoveNegativeSteps() {
    player.move(-3, new Board());
    assertEquals(0, player.getCurrentTile().getPosition());
  }
}
