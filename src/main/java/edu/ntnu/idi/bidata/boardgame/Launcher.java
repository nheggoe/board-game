package edu.ntnu.idi.bidata.boardgame;

import edu.ntnu.idi.bidata.boardgame.backend.MonopolyFacade;

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
    // MonopolyApp.main(args);
    new MonopolyFacade().start();
  }
}
