package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

public class IllegalTilePositionException extends RuntimeException {

  public IllegalTilePositionException() {
    super("Invalid tile position: Tile position cannot be negative");
  }

  public IllegalTilePositionException(int position) {
    super("Invalid tile position: " + position);
  }

  public IllegalTilePositionException(int position, String tileType) {
    super("Invalid position " + position + " for tile type: " + tileType);
  }
}
