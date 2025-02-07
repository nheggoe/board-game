package edu.ntnu.idi.bidata.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for validating the behavior of the InputHandler class.
 * This class contains unit tests to ensure the correctness of various methods
 * associated with handling and processing input.
 *
 * @author Nick Heggø
 * @version 2025.01.30
 */
class InputHandlerTest {

  InputHandler inputHandler;

  @AfterEach
  void restoreSystemIn() {
    System.setIn(System.in); // restore System.in after each test
  }


  /**
   * Tests the nextLine method of the InputHandler class.
   * Verifies that the method throws an IllegalArgumentException when
   * attempting to read an empty input line.
   * This test sets the standard input stream to a ByteArrayInputStream with
   * empty input. It ensures that the nextLine method correctly validates and
   * rejects empty input by throwing the expected exception.
   */
  @Test
  @Disabled("Allowed for enter only (empty line)")
  void emptyLineNegativeTest() {
    System.setIn(new ByteArrayInputStream("".getBytes()));
    InputHandler inputHandler = new InputHandler();
    assertThrows(IllegalArgumentException.class, inputHandler::nextLine);
    restoreSystemIn();
    System.setIn(new ByteArrayInputStream("    ".getBytes()));
    inputHandler = new InputHandler();
    assertThrows(IllegalArgumentException.class, inputHandler::nextLine);
  }

  @Test
  void nextLine2() {
    System.setIn(new ByteArrayInputStream("  test ".getBytes()));
    InputHandler inputHandler = new InputHandler();
    assertEquals("test", inputHandler.nextLine());
  }
}