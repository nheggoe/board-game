package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable;

public sealed interface Ownable permits Property, Railroad, Utility {
  int price();

  int rent();
}
