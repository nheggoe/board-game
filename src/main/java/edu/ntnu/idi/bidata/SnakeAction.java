package edu.ntnu.idi.bidata;

/**
 * The {@code SnakeAction} class represents an action that moves the player 10 tiles backwards.
 * It automatically assigns itself to the given tile during construction.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.12
 */
public class SnakeAction implements TileAction{
  // The tile where the ladder starts
  private final Tiles sourceTile;

  /**
   * Constructs a new SnakeAction and automatically assigns itself to the source tile.
   *
   * @param sourceTile the tile where the ladder starts
   */
  public SnakeAction(Tiles sourceTile) {
    this.sourceTile = sourceTile;
    sourceTile.setLandAction(this);
  }

  /**
   * Moves the player 10 tiles backwards
   *
   * @param player the player landing on the tile
   */
  @Override
  public void perform(Player player) {
    int newPosition = sourceTile.getPosition() - 10;

    if (newPosition >= player.getBoard().getNumberOfTiles()) {
      newPosition = player.getBoard().getNumberOfTiles() - 1;
    }
    player.placeOnTile(player.getBoard().getTile(newPosition));
  }
}
