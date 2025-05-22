package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.upgrade;

/**
 * Represents an upgrade for a property in the Monopoly game, which can be of a specific type and
 * affects the rent multiplier of the property it is applied to.
 *
 * @param type the type of the upgrade, such as a house or hotel. Must not be null.
 * @param rentMultiplierPercentage the additional percentage multiplier that this upgrade
 *     contributes to the rent of the property. Must be non-negative.
 * @author Mihailo
 * @version 2025.04.26
 */
public record Upgrade(UpgradeType type, int rentMultiplierPercentage) {

  public Upgrade {
    if (rentMultiplierPercentage < 0) {
      throw new IllegalArgumentException("Rent multiplier must be positive!");
    }
  }
}
