package edu.ntnu.idi.bidata.io;

import java.util.Scanner;

/**
 * The InputHandler class is responsible for managing user input from the console.
 * It provides functionality to read and validate string input from the user,
 * using a singleton design pattern to ensure a single instance of the class.
 *
 * @author Nick Heggø
 * @version 2025.02.07
 */
public class InputHandler {

  private final Scanner scanner;

  /**
   * Constructs an instance of the InputHandler. This constructor initializes
   * the Scanner object to read user input from the standard input stream.
   * It is private to enforce the singleton design pattern, allowing only one
   * instance of the InputHandler to be created.
   */
  public InputHandler() {
    this(new Scanner(System.in));
  }

  public InputHandler(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Collects a valid, non-empty string input from the user. This method repeatedly
   * prompts the user to provide input until a valid string is entered. Input validation
   * is based on specific criteria defined in the nextLine method.
   *
   * @return A valid, non-empty, trimmed string provided by the user.
   */
  public String collectValidString() {
    String validInput = null;
    while (validInput == null) {
      try {
        validInput = nextLine();
      } catch (IllegalArgumentException illegalArgumentException) {
        System.out.println(illegalArgumentException.getMessage());
      }
    }
    return validInput;
  }

  /**
   * Reads a line of input from the scanner, trims any leading or trailing whitespace,
   * and validates the input to ensure it is not null or blank.
   *
   * @return A non-empty, trimmed string of user input.
   * @throws IllegalArgumentException If the input is null or blank.
   */
  public String nextLine() {
    assertEmptyLine();
    return scanner.nextLine().strip();
  }

  private void assertEmptyLine() {
    if (!scanner.hasNextLine()) {
      throw new IllegalArgumentException("The is problem");
    }
  }

  /**
   * Validates that the given string is not null or blank.
   *
   * @param s The input string to validate.
   * @throws IllegalArgumentException If the input is null or blank.
   */
  private void assertInput(String s) {
    if (s == null || s.isBlank()) {
      throw new IllegalArgumentException("Input cannot be empty!");
    }
  }
}
