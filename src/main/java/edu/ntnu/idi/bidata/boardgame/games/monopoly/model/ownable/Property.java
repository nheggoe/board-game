package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.upgrade.Upgrade;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.upgrade.UpgradeType;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 2025.05.14
 */
public final class Property implements Ownable {

  private final String name;
  private final Color color;
  private final int price;
  private final List<Upgrade> upgrades;

  public Property(String name, Color color, int price) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Property name cannot be empty.");
    }
    if (color == null) {
      throw new IllegalArgumentException("Property must have a color.");
    }
    if (price < 0) {
      throw new IllegalArgumentException("Price of property must be a positive number.");
    }
    this.name = name;
    this.color = color;
    this.price = price;
    this.upgrades = new ArrayList<>();
  }

  @Override
  public int price() {
    return price;
  }

  @Override
  public int rent() {
    int baseRent = (int) (price * 0.7);
    int bonusRent = upgrades.stream().mapToInt(Upgrade::rentMultiplierPercentage).sum();
    return baseRent + (baseRent * bonusRent / 100);
  }

  public void addUpgrade(Upgrade upgrade) {
    upgrades.add(upgrade);
  }

  public boolean hasHotel() {
    return upgrades.stream().anyMatch(u -> u.type() == UpgradeType.HOTEL);
  }

  public boolean canBuildHouse() {
    return !hasHotel() && (countHouses() < 4);
  }

  public int countHouses() {
    return (int) upgrades.stream().filter(u -> u.type() == UpgradeType.HOUSE).count();
  }

  public List<Upgrade> getUpgrades() {
    return List.copyOf(upgrades);
  }

  public String getName() {
    return name;
  }

  public Color getColor() {
    return color;
  }

  @Override
  public String toString() {
    return "Property[name=%s, price=%d, color=%s]".formatted(name, price, color);
  }

  public enum Color {
    BROWN,
    DARK_BLUE,
    GREEN,
    LIGHT_BLUE,
    ORANGE,
    PINK,
    RED,
    YELLOW
  }
}
