package edu.ntnu.idi.bidata.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.ntnu.idi.bidata.action.TileAction;

/**
 * The {@code Tile} class represents a tile on the board. Each tile has a unique position, name and
 * an action in some cases.
 *
 * @author Mihailo
 * @version 2025.02.07
 */
public class Tile {
  // The name of the tile.
  private String name;
  // The position of the tile on the board.
  private int position;

  @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
  private TileAction landAction;

  /** no-arguments constructor for the Jackson JSON library. */
  private Tile() {}

  /**
   * Constructs a new tile with a position, a name, and an action.
   *
   * @param position the position of the tile
   * @param name the name of the tile
   * @param landAction the action performed when a player lands on this tile.
   */
  public Tile(int position, String name, TileAction landAction) {
    setPosition(position);
    setName(name);
    setLandAction(landAction);
  }

  /**
   * Constructs a new tile with a position and a name (without an action).
   *
   * @param position the position of the tile
   * @param name the name of the tile
   */
  public Tile(int position, String name) {
    this(position, name, null);
  }

  /**
   * Performs the action when a player lands on this tile.
   *
   * @param player the player landing on the tile
   */
  public void landPlayer(Player player, Board board) {
    if (landAction != null) {
      landAction.perform(player, board);
      player.placeOnTile(this);
    }
  }

  // Getters and setters

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLandAction(TileAction action) {
    this.landAction = action;
  }

  public TileAction getLandAction() {
    return landAction;
  }
}
