package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile;

public abstract sealed class CornerMonopolyTile implements MonopolyTile
    permits FreeParkingMonopolyTile, GoToJailMonopolyTile, JailMonopolyTile, StartMonopolyTile {

  private final Position position;

  protected CornerMonopolyTile(Position position) {
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

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + "position=" + position + ']';
  }
}
