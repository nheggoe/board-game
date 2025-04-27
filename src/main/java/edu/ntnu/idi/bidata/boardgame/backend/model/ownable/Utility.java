package edu.ntnu.idi.bidata.boardgame.backend.model.ownable;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;

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
    var diceRoll = Dice.getInstance().roll(2);
    int total = diceRoll.getTotal();

    var game = GameEngine.getInstance().getGame().orElseThrow();
    Player owner = game.stream().filter(p -> p.isOwnerOf(this)).findFirst().orElse(null);

    if (owner == null) {
      return total * 4;
    }

    long utilityOwned = owner.getOwnedAssets().stream().filter(Utility.class::isInstance).count();

    int multiplier = (utilityOwned == 2) ? 10 : 4;
    return total * multiplier;
  }
}
