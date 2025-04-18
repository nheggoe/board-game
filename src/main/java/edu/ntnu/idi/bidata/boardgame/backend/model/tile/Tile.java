package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import org.jspecify.annotations.NullMarked;

/**
 * The {@code Tile} class represents a tile on the board. Each tile has a unique position, name and
 * an action in some cases.
 *
 * @author Mihailo and Nick Heggø
 * @version 2025.04.18
 */
@NullMarked
public abstract class Tile {

  private final int tilePosition;

  private String tileName;
  private TileAction tileAction = unused -> {};

  /**
   * Constructs a new tile with a position and a name.
   *
   * @param tilePosition the position of the tile from index 0
   * @param tileName the name of the tile
   */
  public Tile(int tilePosition, String tileName) {
    this.tilePosition = tilePosition;
    setTileName(tileName);
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
