package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

public record TaxTile(int percentage) implements Tile {
  public TaxTile {
    if (percentage < 0) {
      throw new IllegalArgumentException("Amount to pay cannot be negative!");
    }
  }
}
