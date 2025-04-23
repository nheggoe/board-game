package edu.ntnu.idi.bidata.boardgame.backend.model.ownable;

public record Utility(String name, int price) implements Ownable {

  public Utility {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name of utility cannot be null!");
    }
  }

  @Override
  public int rent() {
    return (int) (price * 0.5);
  }
}
