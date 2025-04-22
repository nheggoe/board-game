package edu.ntnu.idi.bidata.boardgame;

import edu.ntnu.idi.bidata.boardgame.backend.MonopolyFacade;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardFactory;

/**
 * The {@link Launcher} class is the entry point of the program. It creates a new instance of the
 * {@link MonopolyFacade} class and runs it.
 *
 * @author Nick Heggø
 * @version 2025.04.15
 */
public class Launcher {
  /** The main method creates a new instance of the Game class and runs it. */
  public static void main(String[] args) {
    System.out.println(BoardFactory.generateBoard(BoardFactory.Layout.NORMAL).size());
  }
}
