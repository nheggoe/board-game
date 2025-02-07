package edu.ntnu.idi.bidata;

import java.util.Random;

/**
 * Singleton class representing a die with two six-sided dice.
 * Uses lazy initialization to ensure only one instance is created when needed.
 * Uses Singleton in order to ensure non-biased dice rolls.
 *
 * @author Mihailo Hranisavljevic
 * @version 2025.02.07
 */
public class Dice {
  private static Dice instance;
  private final Random random;

  /**
   * Private constructor to prevent instantiation.
   * Initializes a Random instance.
   */
  private Dice() {
    this.random = new Random();
  }

  /**
   * Returns the single instance of Dice using lazy initialization.
   *
   * @return the singleton instance of Dice
   */
  public static Dice getInstance() {
    if (instance == null) {
      instance = new Dice();
    }
    return instance;
  }

  /**
   * Rolls two dice and returns the result as an array.
   *
   * @return an array containing two integers representing the dice roll
   */
  public int[] roll() {
    int die1 = random.nextInt(6) + 1;
    int die2 = random.nextInt(6) + 1;
    return new int[]{die1, die2};
  }

  /**
   * Checks if both dice have the same value.
   *
   * @param diceRoll an array of two integers representing the dice roll
   * @return true if both dice have the same value, false otherwise
   */
  public boolean areDiceEqual(int[] diceRoll) {
    return diceRoll[0] == diceRoll[1];
  }

  /**
   * Calculates the sum of the two rolls.
   *
   * @param diceRoll an array of two integers representing the dice roll
   * @return the sum of the two dice
   */
  public int getTotal(int[] diceRoll) {
    return diceRoll[0] + diceRoll[1];
  }
}