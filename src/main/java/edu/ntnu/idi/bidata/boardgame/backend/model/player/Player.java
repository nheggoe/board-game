package edu.ntnu.idi.bidata.boardgame.backend.model.player;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.property.Owner;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import java.util.UUID;

/**
 * The {@code Player} class represents a player in the board game. Each player has a name and a
 * current tile position on the board.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.18
 */
public class Player extends Owner {

  // game state
  private UUID gameId;
  private Tile currentTile;

  // player info
  private Figure figure;

  /**
   * Constructs a new player with the specified name and places them at the start tile (position 0)
   * of the board.
   *
   * @param name the name of the player
   * @param figure the figure player has chosen to play as
   */
  public Player(String name, Figure figure) {
    super(name);
    setFigure(figure);
  }

  // ------------------------  APIs  ------------------------

  /**
   * Moves the player forward by the number of steps.
   *
   * @param steps the number of steps to move forward
   */
  public void move(int steps) {
    GameEngine.getInstance().getGame().movePlayer(this, steps);
  }

  // ------------------------  getters and setters  ------------------------

  public UUID getGameId() {
    return gameId;
  }

  public void setGameId(UUID gameId) {
    if (gameId == null) {
      throw new IllegalArgumentException("Game ID cannot be null!");
    }
    this.gameId = gameId;
  }

  /**
   * Returns the tile where the player is currently located.
   *
   * @return the current tile of the player
   */
  public Tile getCurrentTile() {
    return currentTile != null ? currentTile : new Tile(-1, "INVALID") {};
  }

  public void setCurrentTile(Tile tile) {
    if (tile == null) {
      throw new IllegalArgumentException("Tile cannot be null!");
    }
    this.currentTile = tile;
  }

  /**
   * Returns the figure of the player.
   *
   * @return the figure of the player
   */
  public Figure getFigure() {
    return figure;
  }

  public void setFigure(Figure figure) {
    if (figure == null) {
      throw new IllegalArgumentException("Invalid figure, please try again.");
    }
    this.figure = figure;
  }
}
