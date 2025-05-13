package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.upgrade;

public final class Upgrade {

  private final UpgradeType type;
  private final int rentMultiplierPercentage;

  public Upgrade(UpgradeType type, int rentMultiplierPercentage) {
    if (rentMultiplierPercentage < 0) {
      throw new IllegalArgumentException("Rent multiplier must be positive!");
    }
    this.type = type;
    this.rentMultiplierPercentage = rentMultiplierPercentage;
  }

  public UpgradeType getType() {
    return type;
  }

  public int getRentMultiplierPercentage() {
    return rentMultiplierPercentage;
  }
}
