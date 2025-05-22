package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable;

/**
 * Represents a Utility that charges rent based on a new dice roll. If the owner owns both
 * utilities, rent is 10× the dice roll total; otherwise, it is 4×.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.04.26
 */
public record Utility(String name, int price) implements Ownable {

  public Utility {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Utility name cannot be null or blank!");
    }
  }

  @Override
  public int rent() {
    return 10;
  }
}
