package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.Ownable;

public record OwnableMonopolyTile(Ownable ownable) implements MonopolyTile {
  public OwnableMonopolyTile {
    if (ownable == null) {
      throw new IllegalStateException("Property cannot be null on property tile!");
    }
  }
}
