package edu.ntnu.idi.bidata.boardgame.backend.model.board;

public class IllegalTilePositionException extends RuntimeException {
  public IllegalTilePositionException() {
    super("Illegal tile position");
  }
}
