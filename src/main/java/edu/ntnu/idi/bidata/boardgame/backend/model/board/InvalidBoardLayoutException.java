package edu.ntnu.idi.bidata.boardgame.backend.model.board;

public class InvalidBoardLayoutException extends RuntimeException {
  public InvalidBoardLayoutException(String message) {
    super(message);
  }

  public InvalidBoardLayoutException() {
    super("Invalid board setup");
  }
}
