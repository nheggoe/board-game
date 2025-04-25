package edu.ntnu.idi.bidata.boardgame.frontend.controller;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;

public class GameController {

  private final Game game;

  public GameController() {
    game =
        GameEngine.getInstance()
            .getGame()
            .orElseThrow(() -> new IllegalArgumentException("Game engine is not correct setup!"));
  }

  /**
   * Retrieves the tile located at a specific position on the game board.
   *
   * @param position the position on the game board, typically represented as an index
   * @return the {@link Tile} at the specified position
   * @throws IllegalArgumentException if the specified position is invalid
   */
  public Tile getTileAtPosition(int position) {
    return game.getTile(position);
  }

  /**
   * Retrieves the size of the game board by returning the number of tiles it contains.
   *
   * @return the total number of tiles on the board
   */
  public int getBoardSize() {
    return game.getBoard().getNumberOfTiles();
  }
}
