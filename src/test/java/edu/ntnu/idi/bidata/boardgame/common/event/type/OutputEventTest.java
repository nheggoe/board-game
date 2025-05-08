package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OutputEventTest {

  @Test
  void testInvalidData() {
    assertThrows(NullPointerException.class, () -> new OutputEvent(null));
  }

  @Test
  void testBasic() {
    var output = "test";
    var outputEvent = new OutputEvent(output);
    assertEquals(output, outputEvent.output());
  }
}
