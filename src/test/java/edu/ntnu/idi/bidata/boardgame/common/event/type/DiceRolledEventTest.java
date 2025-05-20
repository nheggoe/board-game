package edu.ntnu.idi.bidata.boardgame.common.event.type;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.core.model.dice.DiceRoll;
import org.junit.jupiter.api.Test;

class DiceRolledEventTest {

  @Test
  void testInvalidData() {
    assertThrows(NullPointerException.class, () -> new CoreEvent.DiceRolled(null));
  }

  @Test
  void testBasic() {
    var diceRoll = new DiceRoll(1, 2, 3);
    var diceRolledEvent = new CoreEvent.DiceRolled(diceRoll);
    assertEquals(diceRoll, diceRolledEvent.diceRoll());
  }
}
