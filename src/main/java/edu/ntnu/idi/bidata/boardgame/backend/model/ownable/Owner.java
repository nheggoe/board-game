package edu.ntnu.idi.bidata.boardgame.backend.model.ownable;

import java.util.ArrayList;
import java.util.List;

public abstract class Owner {

  private final List<Ownable> ownedAssets;

  private String name;
  private int balance = 0;

  protected Owner() {
    this.ownedAssets = new ArrayList<>();
  }

  // ------------------------  public interface  ------------------------

  /**
   * Transfers a specified property from the owner's assets and updates the owner's balance with the
   * agreed monetary amount.
   *
   * @param ownable the property or asset to be removed from the owner's possession.
   * @param agreedAmount the monetary value to be added to the owner's balance as part of the
   *     transfer.
   * @throws IllegalArgumentException if the agreed amount is negative.
   */
  public void transferProperty(Ownable ownable, int agreedAmount) {
    this.ownedAssets.remove(ownable);
    this.addBalance(agreedAmount);
  }

  public void purchase(Ownable ownable) {
    validateSufficientFunds(ownable.price());
    deductBalance(ownable.price());
    ownedAssets.add(ownable);
  }

  public void pay(int amount) {
    validateSufficientFunds(amount);
    deductBalance(amount);
  }

  public void addBalance(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount to add cannot be negative!");
    }
    this.balance += amount;
  }

  public void deductBalance(int amount) {
    if (amount > balance) {
      throw new InsufficientFundsException(amount, balance);
    }
    balance -= amount;
  }

  public boolean isOwnerOf(Ownable ownable) {
    return ownedAssets.contains(ownable);
  }

  public boolean hasSufficientFunds(int amount) {
    try {
      validateSufficientFunds(amount);
      return true;
    } catch (InsufficientFundsException e) {
      return false;
    }
  }

  public int getNetWorth() {
    return balance + ownedAssets.stream().map(Ownable::price).reduce(Integer::sum).orElse(0);
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

  public int getBalance() {
    return balance;
  }

  // ------------------------  private  ------------------------

  private void validateSufficientFunds(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount to purchase can not be negative!");
    }
    if (balance < amount) {
      throw new InsufficientFundsException(amount, balance);
    }
  }
}
