package edu.ntnu.idi.bidata.boardgame.backend.model.ownable;

public record Railroad(int price) implements Ownable {
  @Override
  public int rent() {
    return (int) (price * 0.5);
  }
}
