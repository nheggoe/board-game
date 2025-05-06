package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board;

public class InvalidBoardLayoutException extends RuntimeException {
  public InvalidBoardLayoutException(String message) {
    super(message);
  }

  public InvalidBoardLayoutException() {
    super("Invalid board setup");
  }
}
