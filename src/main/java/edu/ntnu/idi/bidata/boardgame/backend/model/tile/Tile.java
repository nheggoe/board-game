package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;

/**
 * The {@code Tile} class represents a tile on the board. Each tile has a unique position, name and
 * an action in some cases.
 *
 * @author Mihailo and Nick Heggø
 * @version 2025.04.15
 */
public abstract class Tile {

  private final int tilePosition;

  private String tileName;
  private TileAction tileAction;

  /**
   * Constructs a new tile with a position, a name, and an action.
   *
   * @param tileName the name of the tile
   * @param tileAction the action performed when a player lands on this tile.
   */
  public Tile(int tilePosition, String tileName, TileAction tileAction) {
    this.tilePosition = tilePosition;
    setTileName(tileName);
    setTileAction(tileAction);
  }

  // ------------------------  APIs  ------------------------

  public void performAction(Player player) {
    tileAction.performAction(player);
  }

  // ------------------------  getters and setters  ------------------------

  public int getTilePosition() {
    return tilePosition;
  }

  public String getTileName() {
    return tileName;
  }

  public void setTileName(String tileName) {
    this.tileName = tileName;
  }

  public void setTileAction(TileAction action) {
    this.tileAction = action;
  }

  public TileAction getTileAction() {
    return tileAction;
  }
}
