package edu.ntnu.idi.bidata.boardgame.games.snake.model.tile;

/**
 * @param tilesToSkip the number of tiles that will be skipped by stepping on this tile
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record LadderTile(int tilesToSkip) implements SnakeAndLadderTile {
  public LadderTile {
    if (tilesToSkip == 0) {
      throw new IllegalArgumentException("Tiles to skip must be non-zero");
    }
    if (tilesToSkip < 0) {
      throw new IllegalArgumentException("Tiles to skip must be non-negative");
    }
    if (tilesToSkip > 100) {
      throw new IllegalArgumentException("Tiles to skip exceeds maximum of 100");
    }
  }
}
