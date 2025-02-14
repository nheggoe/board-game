package edu.ntnu.idi.bidata.core;

/**
 * The {@code Player} class represents a player in the board game.
 * Each player has a name and a current tile position on the board.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.14
 */
public class Player {
  // Name of the player
  private String name;
  // The tile where the player is currently located
  private Tile currentTile;
  // The board on which the player is moving

  /**
   * Constructs a new player with the specified name and places them
   * at the start tile (position 0) of the board.
   *
   * @param name  the name of the player
   * @param board the board on which the player will move
   */
  public Player(String name, Board board) {
    setName(name);
    // Place the player on the "start" tile
    placeOnTile(board.getTile(0));
  }

  /**
   * Places the player on a tile.
   *
   * @param tile the tile to place the player on
   */
  public void placeOnTile(Tile tile) {
    this.currentTile = tile;
  }

  /**
   * Moves the player forward by the number of steps.
   * Activates the action of the tile where the player lands.
   *
   * @param steps the number of steps to move forward
   */
  public void move(int steps, Board board) {
    int newPosition = Math.clamp((currentTile.getPosition() + steps), 0, board.getNumberOfTiles() - 1);
    Tile newTile = board.getTile(newPosition);
    placeOnTile(newTile);
    newTile.landPlayer(this, board);
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the tile where the player is currently located.
   *
   * @return the current tile of the player
   */
  public Tile getCurrentTile() {
    return currentTile;
  }


}
