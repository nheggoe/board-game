package edu.ntnu.idi.bidata.boardgame.backend.model.ownable;

public sealed interface Ownable permits Property, Railroad, Utility {
  int price();

  int rent();
}
