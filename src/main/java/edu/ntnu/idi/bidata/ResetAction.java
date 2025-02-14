package edu.ntnu.idi.bidata;

public class ResetAction implements TileAction{

  public ResetAction() {
    // default constructor
  }

  /**
   * Moves the player to the starting tile
   *
   * @param player the player landing on the tile
   */
  @Override
  public void perform(Player player, Board board) {
    player.placeOnTile(board.getTile(0));
  }
}
