package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.action.*;
import edu.ntnu.idi.bidata.core.*;
import edu.ntnu.idi.bidata.util.json.JsonService;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * The {@code BoardGameFactory} class is a factory class that creates different types of boards.
 * It also is able to read a board from a file.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.03.14
 */
public class BoardGameFactory {

  private static final JsonService boardService = new JsonService(Board.class);

  /**
   * Creates a regular board.
   *
   * @return a new board
   */
  public static Board createBoard() {
    return new Board();
  }

  /**
   * Creates a board with snakes and reset actions to represent an unfair board.
   *
   * @return a new board with snakes and reset actions
   */
  public static Board createUnfairBoard() {
    Board board = new Board();
    board.getTile(5).setLandAction(new SnakeAction());
    board.getTile(15).setLandAction(new SnakeAction());
    board.getTile(18).setLandAction(new SnakeAction());
    board.getTile(40).setLandAction(new SnakeAction());
    board.getTile(45).setLandAction(new SnakeAction());
    board.getTile(50).setLandAction(new ResetAction());
    board.getTile(60).setLandAction(new ResetAction());
    board.getTile(65).setLandAction(new ResetAction());
    board.getTile(80).setLandAction(new SnakeAction());
    return board;
  }

  /**
   * Creates a board with ladders to represent an easy board.
   *
   * @return a new board with ladders
   */
  public static Board createEasyBoard(){
    Board board = new Board();
    board.getTile(5).setLandAction(new LadderAction());
    board.getTile(15).setLandAction(new LadderAction());
    board.getTile(18).setLandAction(new LadderAction());
    board.getTile(40).setLandAction(new LadderAction());
    board.getTile(45).setLandAction(new LadderAction());
    board.getTile(50).setLandAction(new LadderAction());
    board.getTile(60).setLandAction(new LadderAction());
    board.getTile(65).setLandAction(new LadderAction());
    board.getTile(80).setLandAction(new LadderAction());
    return board;
  }



  /**
   * Loads a board configuration from a JSON file.
   *
   * @return a new instance of {@code Board} based on the JSON contents
   * @throws IOException if an error occurs while reading the file
   */
  public static Board loadBoardFromJson() throws IOException {
   Stream<Board> boards = boardService.loadJsonAsStream();
    return boards.findFirst().orElse(null);
  }

  /**
   * Saves the current board configuration to a JSON file.
   *
   * @param board the board instance to be saved
   * @throws IOException if an error occurs during writing
   */
  public static void saveBoardToJson(Board board) throws IOException {
       boardService.writeCollection(Stream.of(board));
  }
}