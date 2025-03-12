package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.core.Dice;
import org.junit.jupiter.api.Test;

class DiceTest {

  @Test
  void testSingletonInstance() {
    Dice firstInstance = Dice.getInstance();
    Dice secondInstance = Dice.getInstance();
    assertSame(firstInstance, secondInstance);
  }

  @Test
  void testRollWithinRange() {
    Dice dice = Dice.getInstance();
    int roll = dice.roll();
    assertTrue(roll >= 2 && roll <= 12);
  }

  @Test
  void testIsDouble() {
    Dice dice = Dice.getInstance();
    assertTrue(dice.areDiceEqual(new int[] {3, 3}));
    assertFalse(dice.areDiceEqual(new int[] {3, 4}));
  }

  @Test
  void testGetTotal() {
    Dice dice = Dice.getInstance();
    assertEquals(5, dice.getTotal(new int[] {2, 3}));
    assertEquals(12, dice.getTotal(new int[] {6, 6}));
  }
}
