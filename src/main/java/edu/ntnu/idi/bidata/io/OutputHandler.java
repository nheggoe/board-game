package edu.ntnu.idi.bidata.io;

/**
 * The OutputHandler class is responsible for outputting messages to the standard
 * output stream. It currently acts as a temporary solution for handling console-based
 * output and may transition to a graphical user interface (GUI) in the future.
 *
 * This class employs the singleton design pattern to ensure that only one instance
 * of OutputHandler exists throughout the application. This ensures centralized
 * management of output operations.
 *
 * @author Nick Heggø
 * @version 2025.01.29
 */
public class OutputHandler {
  private static final OutputHandler OUTPUT_HANDLER = new OutputHandler();

  private OutputHandler() {
  }

  /**
   * Provides a singleton instance of the OutputHandler class.
   *
   * @return The singleton instance of OutputHandler.
   */
  public static OutputHandler getInstance() {
    return OUTPUT_HANDLER;
  }

  /**
   * Prints the specified string to the standard output stream, followed by a newline.
   * Serves at a temporary solution, will be transition to GUI later on.
   *
   * @param s The string to be printed to the console.
   */
  public void println(String s) {
    System.out.println(s);
  }

}
