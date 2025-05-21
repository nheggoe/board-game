package edu.ntnu.idi.bidata.boardgame.core.model;

import edu.ntnu.idi.bidata.boardgame.common.util.StringFormatter;
import java.util.UUID;

/**
 * The {@code Player} class represents a player in the board game. Each player has a name and a
 * current tile position on the board.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.05.14
 */
public abstract class Player {

  // player info
  private final UUID id;
  private String name;
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
  protected Player(String name, Figure figure) {
    id = UUID.randomUUID();
    setName(name);
    setFigure(figure);
  }

  // ------------------------  getters and setters  ------------------------

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty!");
    }
    this.name = name;
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

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    if (position < 0) {
      throw new IllegalArgumentException("Positon cannot be negative!");
    }
    this.position = position;
  }

  public UUID getId() {
    return id;
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
    return "%s[figure=%s, position=%d]"
        .formatted(getName(), StringFormatter.formatEnum(getFigure()), getPosition());
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Player player)) {
      return false;
    }
    return id.equals(player.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public static String toCsvLine(Player player) {
    return "%s,%s".formatted(player.getName(), player.getFigure());
  }
}
