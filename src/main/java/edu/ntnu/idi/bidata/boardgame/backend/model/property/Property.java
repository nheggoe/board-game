package edu.ntnu.idi.bidata.boardgame.backend.model.property;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;

import java.util.Optional;

/**
 * The {@link Property} class represents an abstraction of a property in a board game. Each property
 * has a name, a monetary value, and an owner. It provides functionality to manage ownership
 * transfers of the property.
 *
 * @author Nick Heggø
 * @version 2025.04.18
 */
public abstract class Property {

  private final String name;
  private final int value;

  protected Player owner;

  protected Property(String name, int value) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name of the property cannot be null or empty!");
    }
    this.name = name;
    if (value < 0) {
      throw new IllegalArgumentException("Value of the property cannot be negative!");
    }
    this.value = value;
  }

  // ------------------------  APIs  ------------------------

  public void transferOwnership(Player newOwner, int agreedAmount) {
    if (newOwner == null) {
      throw new IllegalArgumentException("New owner of the property cannot be null!");
    }
    newOwner.deductBalance(agreedAmount);
    this.owner = newOwner;
  }

  // ------------------------  Getters and Setters  ------------------------

  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }

  public Optional<Player> getOwner() {
    return Optional.ofNullable(owner);
  }
}
