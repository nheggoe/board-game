package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

public record TaxTile(int amount) implements Tile {
  public TaxTile {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount to pay cannot be negative!");
    }
  }
}
