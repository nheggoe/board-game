package edu.ntnu.idi.bidata.boardgame.backend.util;

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
 * @version 2025.04.15
 */
public class OutputHandler {

  private OutputHandler() {}

  /**
   * Prints the specified string to the standard output stream, followed by a newline. Serves at a
   * temporary solution, will be transition to GUI later on.
   *
   * @param s The string to be printed to the console.
   */
  public static void println(String s) {
    System.out.println(s);
  }

  /**
   * Prints the specified string to the standard output stream without a newline.
   *
   * @param s The string to be printed to the console.
   */
  public static void print(String s) {
    System.out.print(s);
  }

  /**
   * Prints a prompt symbol ("> ") to the standard output stream. This is typically used to indicate
   * that the system is ready to receive input from the user.
   */
  public static void printInputPrompt() {
    print("> ");
  }

  /**
   * Prints a message and a prompt symbol ("> ") to the standard output stream. This is typically
   * used to indicate that the system is ready to receive input from the user.
   */
  public static void printInputPrompt(String message) {
    println(message);
    print("> ");
  }

  public static void println(Object o) {
    println(o.toString());
  }
}
