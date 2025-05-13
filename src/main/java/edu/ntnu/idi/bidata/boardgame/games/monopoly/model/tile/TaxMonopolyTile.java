package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile;

public record TaxMonopolyTile(int percentage) implements MonopolyTile {
  public TaxMonopolyTile {
    if (percentage < 0) {
      throw new IllegalArgumentException("Amount to pay cannot be negative!");
    }
  }
}
