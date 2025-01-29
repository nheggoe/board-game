package edu.ntnu.idi.bidata;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TilesTest {

  @Test
  void testGetX() {
    Tiles tile = new Tiles(5, 10);
    assertEquals(5, tile.getX(), "getX() should return the correct x value");
  }

  @Test
  void testGetY() {
    Tiles tile = new Tiles(5, 10);
    assertEquals(10, tile.getY(), "getY() should return the correct y value");
  }

  @Test
  void testSetX() {
    Tiles tile = new Tiles(0, 0);
    tile.setX(7);
    assertEquals(7, tile.getX(), "setX() should update the x value correctly");
  }

  @Test
  void testSetY() {
    Tiles tile = new Tiles(0, 0);
    tile.setY(15);
    assertEquals(15, tile.getY(), "setY() should update the y value correctly");
  }

  @Test
  void testConstructor() {
    Tiles tile = new Tiles(3, 8);
    assertEquals(3, tile.getX(), "Constructor should initialize x correctly");
    assertEquals(8, tile.getY(), "Constructor should initialize y correctly");
  }
}