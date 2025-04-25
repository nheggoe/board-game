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

  public Tile getTileAtPosition(int positon) {
    return game.getTile(positon);
  }

  /**
   * @return the number of tiles the game have
   */
  public int getBoardSize() {
    return game.getBoard().getNumberOfTiles();
  }

}
