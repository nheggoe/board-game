package edu.ntnu.idi.bidata.boardgame.core.model.dice;

import java.util.Random;

/**
 * Singleton class representing a die with two six-sided dice. Uses lazy initialization to ensure
 * only one instance is created when needed. Uses Singleton to ensure non-biased dice rolls.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.05.08
 */
public class Dice {

  private static final Random random = new Random();

  private Dice() {}

  /**
   * Rolls two dice at the same time
   *
   * @return the result as an array of integers.
   */
  public static DiceRoll roll(int numberOfDice) {
    int[] rolls = new int[numberOfDice];
    for (int i = 0; i < rolls.length; i++) {
      rolls[i] = random.nextInt(6) + 1;
    }
    return new DiceRoll(rolls);
  }
}
