package edu.ntnu.idi.bidata.boardgame.backend.model.property;

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

  protected Owner owner = new Owner("Bank") {};

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

  /**
   * Transfers the ownership of a property to a new owner after establishing the agreed monetary
   * transaction. Validates the parameters and ensures the ownership is updated accordingly.
   *
   * @param newOwner the {@code Owner} who will assume ownership of the property.
   * @param agreedAmount the monetary value agreed upon for the transfer. This value will be
   *     deducted from the buyer's balance and credited to the current owner's balance.
   * @throws IllegalArgumentException if {@code newOwner} is null or if the transaction fails due to
   *     other validation constraints.
   */
  public void transferOwnership(Owner newOwner, int agreedAmount) {
    if (newOwner == null) {
      throw new IllegalArgumentException("New owner of the property cannot be null!");
    }
    getOwner().transferProperty(newOwner, agreedAmount);
    setOwner(newOwner);
  }

  // ------------------------  Getters and Setters  ------------------------

  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }
}
