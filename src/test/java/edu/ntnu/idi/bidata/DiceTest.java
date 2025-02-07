package edu.ntnu.idi.bidata;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
  
  @Test
  void testSingletonInstance(){
    Dice firstInstance = Dice.getInstance();
    Dice secondInstance = Dice.getInstance();
    assertSame(firstInstance, secondInstance);
  }

  @Test
  void testRollWithinRange() {
    Dice dice = Dice.getInstance();
    int[] roll = dice.roll();
    assertTrue(roll[0] >= 1 && roll[0] <= 6);
    assertTrue(roll[1] >= 1 && roll[1] <= 6);
  }

  @Test
  void testIsDouble() {
    Dice dice = Dice.getInstance();
    assertTrue(dice.isDouble(new int[]{3, 3}));
    assertFalse(dice.isDouble(new int[]{3, 4}));
  }

  @Test
  void testGetTotal() {
    Dice dice = Dice.getInstance();
    assertEquals(5, dice.getTotal(new int[]{2, 3}));
    assertEquals(12, dice.getTotal(new int[]{6, 6}));
  }
}
