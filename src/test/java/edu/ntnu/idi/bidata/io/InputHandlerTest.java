package edu.ntnu.idi.bidata.io;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for validating the behavior of the InputHandler class. This class contains unit tests
 * to ensure the correctness of various methods associated with handling and processing input.
 *
 * @author Nick Heggø
 * @version 2025.03.12
 */
class InputHandlerTest {
  /*

    private final InputStream originalIn = System.in;

    @AfterEach
    void restoreSystemIn() {
      System.setIn(originalIn); // restore System.in after each test
    }

    @Test
    void emptyLineNegativeTest() {
      assertTimeoutPreemptively(
          Duration.ofSeconds(1),
          () -> {
            String multiLineData =
                """
                       \s
                             \s
                                   \s
                           \s
                                     \s""";
            System.setIn(new ByteArrayInputStream(multiLineData.getBytes()));
            InputHandler inHandler = new InputHandler();
            for (int i = 0; i < 5; i++) {
              assertDoesNotThrow(inHandler::nextLine);
            }
          });
    }

    @Test
    void nextLine2() {
      assertTimeoutPreemptively(
          Duration.ofSeconds(1),
          () -> {
            String multiLineData =
                """
                                   s         \s
                test   \s
                     test     \s""";
            System.setIn(new ByteArrayInputStream(multiLineData.getBytes()));
            InputHandler inHandler = new InputHandler();
            assertEquals("s", inHandler.nextLine());
            assertEquals("test", inHandler.nextLine());
            assertEquals("test", inHandler.nextLine());
          });
    }
  */
}
