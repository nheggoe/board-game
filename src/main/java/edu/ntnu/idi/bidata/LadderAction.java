package edu.ntnu.idi.bidata;

/**
 * The {@code LadderAction} moves a player 10 tiles forward.
 * It automatically assigns itself to the given tile during construction.
 *
 * @author Mihailo
 * @version 2025.02.07
 */
public class LadderAction implements TileAction {
  // The tile where the ladder starts
  private final Tiles sourceTile;

  /**
   * Constructs a new LadderAction and automatically assigns itself to the source tile.
   *
   * @param sourceTile the tile where the ladder starts
   */
  public LadderAction(Tiles sourceTile) {
    this.sourceTile = sourceTile;
    sourceTile.setLandAction(this);
  }

  /**
   * Moves the player 10 tiles forward
   *
   * @param player the player landing on the tile
   */
  @Override
  public void perform(Player player) {
    int newPosition = sourceTile.getPosition() + 10;

    if (newPosition >= player.getBoard().getNumberOfTiles()) {
      newPosition = player.getBoard().getNumberOfTiles() - 1;
    }
    player.placeOnTile(player.getBoard().getTile(newPosition));
  }
}
