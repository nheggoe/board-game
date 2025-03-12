package edu.ntnu.idi.bidata.io;

/**
 * The OutputHandler class is responsible for outputting messages to the standard output stream. It
 * currently acts as a temporary solution for handling console-based output and may transition to a
 * graphical user interface (GUI) in the future.
 *
 * <p>This class employs the singleton design pattern to ensure that only one instance of
 * OutputHandler exists throughout the application. This ensures centralized management of output
 * operations.
 *
 * @author Nick Heggø
 * @version 2025.02.07
 */
public class OutputHandler {

  public OutputHandler() {
    // default constructor
  }

  /**
   * Prints the specified string to the standard output stream, followed by a newline. Serves at a
   * temporary solution, will be transition to GUI later on.
   *
   * @param s The string to be printed to the console.
   */
  public void println(String s) {
    System.out.println(s);
  }

  /**
   * Prints the specified string to the standard output stream without a newline.
   *
   * @param s The string to be printed to the console.
   */
  public void print(String s) {
    System.out.print(s);
  }

  /**
   * Prints a prompt symbol ("> ") to the standard output stream. This is typically used to indicate
   * that the system is ready to receive input from the user.
   */
  public void printInputPrompt() {
    print("> ");
  }
}
