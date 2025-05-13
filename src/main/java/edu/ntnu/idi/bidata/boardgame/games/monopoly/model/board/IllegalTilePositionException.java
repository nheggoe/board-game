package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board;

public class IllegalTilePositionException extends RuntimeException {
  public IllegalTilePositionException() {
    super("Illegal tile position");
  }
}
