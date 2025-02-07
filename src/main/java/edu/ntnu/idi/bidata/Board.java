package edu.ntnu.idi.bidata;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Board} class represents a game board consisting of a list of tiles.
 * Each tile has a unique position and a name. The board is initialized with a
 * predefined set of tiles.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.07
 */
public class Board {
  /**
   * The list of tiles that make up the board.
   */
  private final List<Tiles> tiles;

  /**
   * Constructs a new board with predefined tiles.
   */
  public Board(){
    tiles = new ArrayList<>();
    initializeBoard();
  }

  /**
   * Initializes the board by adding 90 tiles.
   * The first tile is named "Start", and the rest go sequentially.
   */
  private void initializeBoard(){
    for (int i = 0; i < 90; i++) {
      tiles.add(new Tiles(i, i == 0 ? "Start" : "Tile " + (i+1)));
    }
  }

  /**
   * Retrieves a tile at the provided position.
   *
   * @param position the position of the tile to retrieve
   * @return the {@code Tiles} object at the specified position
   * @throws IllegalArgumentException if the position is out of bounds
   */
  public Tiles getTile(int position){
    if(position < 0 || position >= tiles.size()){
      throw new IllegalArgumentException();
    }
    return tiles.get(position);
  }
  /**
   * Returns the total number of tiles.
   *
   * @return the number of tiles
   */
  public int getNumberOfTiles(){
    return tiles.size();
  }


}
