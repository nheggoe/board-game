package edu.ntnu.idi.bidata.boardgame.backend.model;

import java.util.Random;

/**
 * Singleton class representing a die with two six-sided dice. Uses lazy initialization to ensure
 * only one instance is created when needed. Uses Singleton to ensure non-biased dice rolls.
 *
 * @author Mihailo Hranisavljevic and Nick Heggø
 * @version 2025.04.15
 */
public class Dice {

  private static Dice instance;

  private final Random random;

  /** Private constructor to prevent instantiation. Initializes a Random instance. */
  private Dice() {
    this.random = new Random();
  }

  /**
   * Returns the single instance of Dice using lazy initialization.
   *
   * @return the singleton instance of Dice
   */
  public static synchronized Dice getInstance() {
    if (instance == null) {
      instance = new Dice();
    }
    return instance;
  }

  /**
   * Rolls two dice at the same time
   *
   * @return the result as an array of integers.
   */
  public DiceRoll roll(int numberOfDice) {
    int[] rolls = new int[numberOfDice];
    for (int i = 0; i < rolls.length; i++) {
      rolls[i] = random.nextInt(6) + 1;
    }
    return new DiceRoll(rolls);
  }
}
