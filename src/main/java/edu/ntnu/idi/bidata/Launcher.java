package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.core.BoardGame;

/**
 * The {@link Launcher} class is the entry point of the program. It creates a new instance of the
 * {@link BoardGame} class and runs it.
 *
 * @author Nick Heggø
 * @version 2025.02.14
 */
public class Launcher {
  /** The main method creates a new instance of the Game class and runs it. */
  public static void main(String[] args) {
    new BoardGame().start();
  }
}
