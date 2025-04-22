package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

public abstract sealed class CornerTile implements Tile
    permits FreeParkingTile, GoToJailTile, JailTile, StartTile {

  private final Position position;

  protected CornerTile(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("Positon of corner tile cannot be null!");
    }
    this.position = position;
  }

  public Position getPosition() {
    return position;
  }

  public enum Position {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT
  }
}
