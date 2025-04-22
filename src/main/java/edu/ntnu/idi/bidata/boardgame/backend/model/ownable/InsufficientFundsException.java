package edu.ntnu.idi.bidata.boardgame.backend.model.ownable;

public class InsufficientFundsException extends RuntimeException {

  public InsufficientFundsException(String message) {
    super(message);
  }

  public InsufficientFundsException(int amountToSpend, int balance) {
    super("Insufficient funds for " + amountToSpend + ", Available: " + balance);
  }
}
