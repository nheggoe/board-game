package edu.ntnu.idi.bidata.boardgame.backend.model.ownable;

public record Property(String name, Color color, int price) implements Ownable {

  public Property {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Property name cannot be empty!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Property must have a color!");
    }
    if (price < 0) {
      throw new IllegalArgumentException("Price of property must be a positive number!");
    }
  }

  @Override
  public int rent() {
    return (int) (price * 0.5);
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
