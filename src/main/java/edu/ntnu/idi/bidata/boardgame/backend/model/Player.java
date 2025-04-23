package edu.ntnu.idi.bidata.boardgame.backend.model;

import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Owner;
import edu.ntnu.idi.bidata.boardgame.backend.util.StringFormatter;

/**
 * The {@code Player} class represents a player in the board game. Each player has a name and a
 * current tile position on the board.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.18
 */
public class Player extends Owner {

  // player info
  private Figure figure;

  // game state
  private int position;

  /**
   * Constructs a new player with the specified name and places them at the start tile (position 0)
   * of the board.
   *
   * @param name the name of the player
   * @param figure the figure player has chosen to play as
   */
  public Player(String name, Figure figure) {
    super();
    setName(name);
    setFigure(figure);
  }

  // ------------------------  getters and setters  ------------------------

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

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    if (position < 0) {
      throw new IllegalArgumentException("Positon cannot be negative!");
    }
    this.position = position;
  }

  public enum Figure {
    BATTLE_SHIP,
    CAR,
    CAT,
    DUCK,
    HAT
  }

  @Override
  public String toString() {
    return "%s[figure=%s, position=%d, balance=%d]"
        .formatted(getName(), StringFormatter.formatEnum(getFigure()), getPosition(), getBalance());
  }
}
