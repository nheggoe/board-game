package edu.ntnu.idi.bidata;

/**
 * The {@code Tiles} class represents a tile on the board.
 * Each tile has a unique position and a name.
 *
 * @author Mihailo
 * @version 2025.02.07
 */
public class Tiles {
  // The name of the tile.
  private String name;
  // The position of the tile on the board.
  private int position;

  /**
   * Constructs a new tile with a position and a name.
   *
   * @param position the position of the tile
   * @param name the name of the tile
   */
  public Tiles(int position, String name) {
   setName(name);
   setPosition(position);
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
}
