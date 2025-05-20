package edu.ntnu.idi.bidata.boardgame.games.snake.model.tile;

/**
 * Represents a snake tile in the game of Snakes and Ladders. When a player lands on this tile, they
 * will slide back a specified number of tiles.
 *
 * @param tilesToSlideBack the number of tiles the player will slide back upon landing on this tile
 * @author Nick Heggø
 * @version 2025.05.09
 */
public record SnakeTile(int tilesToSlideBack) implements SnakeAndLadderTile {
  public SnakeTile {
    if (tilesToSlideBack == 0) {
      throw new IllegalArgumentException("Tiles to slide back must be non-zero");
    }
    if (tilesToSlideBack < 0) {
      throw new IllegalArgumentException("Tiles to slide back must be non-negative");
    }
  }
}
