package edu.ntnu.idi.bidata.boardgame.backend.model.property;

public abstract class Owner {

  private String name;
  private int balance = 0;

  protected Owner(String name) {
    setName(name);
  }

  /**
   * Transfers a property from the current owner to the specified buyer for an agreed monetary
   * amount.
   *
   * @param buyer the {@code Owner} who is purchasing the property.
   * @param agreedAmount the amount of money agreed upon for the transfer. This amount will be
   *     deducted from the buyer's balance and added to the current owner's balance.
   * @throws IllegalArgumentException if the buyer's balance is less than the agreed amount or if
   *     the agreed amount is negative.
   */
  public void transferProperty(Owner buyer, int agreedAmount) {
    buyer.deductBalance(agreedAmount);
    this.addBalance(agreedAmount);
  }

  public int getBalance() {
    return balance;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty!");
    }
    this.name = name;
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
}
