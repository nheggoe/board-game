package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;

public record OwnableTile(Ownable ownable) implements Tile {
  public OwnableTile {
    if (ownable == null) {
      throw new IllegalStateException("Property cannot be null on property tile!");
    }
  }
}
