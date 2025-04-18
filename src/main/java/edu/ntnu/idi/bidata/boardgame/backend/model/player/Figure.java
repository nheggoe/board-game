package edu.ntnu.idi.bidata.boardgame.backend.model.player;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The {@link Figure} enum represents various game tokens that players can choose from in a board
 * game. Each enum instance is associated with a display name, which is a properly formatted,
 * human-readable version of the enum constant's name.
 *
 * @author Nick Heggø
 * @version 2025.04.18
 */
public enum Figure {
  HAT,
  CAR,
  DUCK,
  CAT;
  private final String displayName;

  Figure() {
    this.displayName =
        Arrays.stream(name().split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
  }

  public static String getAvailableFigures() {
    return Arrays.stream(Figure.values())
        .map(Figure::getDisplayName)
        .collect(Collectors.joining(", "));
  }

  /**
   * Returns the display name of the figure. The display name is a human-readable, properly
   * formatted version of the enum constant's name.
   *
   * @return the display name of the figure
   */
  public String getDisplayName() {
    return displayName;
  }
}
