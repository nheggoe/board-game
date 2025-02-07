package edu.ntnu.idi.bidata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    player.move(5);
    assertEquals(5, player.getCurrentTile().getPosition());
  }

  @Test
  void testMoveBeyondBoardLimit() {
    player.move(100);
    assertEquals(89, player.getCurrentTile().getPosition());
  }

  @Test
  void testMoveNegativeSteps() {
    player.move(-3);
    assertEquals(0, player.getCurrentTile().getPosition());
  }

}
