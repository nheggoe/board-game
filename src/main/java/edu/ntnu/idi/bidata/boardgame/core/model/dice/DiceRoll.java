package edu.ntnu.idi.bidata.boardgame.core.model.dice;

import java.util.Arrays;

/**
 * Represents the result of rolling one or more dice. Each instance holds the face values of the
 * dice rolled. The class is implemented as a record to provide a concise and immutable way to
 * encapsulate the roll results.
 *
 * @author Nick Heggø
 * @version 2025.05.08
 */
public record DiceRoll(int... rolls) {

  public DiceRoll {
    if (rolls.length < 1) {
      throw new IllegalArgumentException("Dice roll must contain at least one dice");
    }
    for (int roll : rolls) {
      if (roll < 1) {
        throw new IllegalArgumentException("Face value of dice cannot be less than 1");
      }
      if (roll > 6) {
        throw new IllegalArgumentException("Face value of dice cannot exceed 6");
      }
    }
    rolls = Arrays.copyOf(rolls, rolls.length);
  }

  /**
   * Checks if all dice in the roll have the same face value.
   *
   * @return true if all dice have the same face value, false otherwise
   */
  public boolean areDiceEqual() {
    if (rolls.length < 2) {
      throw new UnsupportedOperationException("Dice roll must contain at least two dice");
    }
    return Arrays.stream(rolls).allMatch(face -> face == rolls[0]);
  }

  /**
   * Calculates the total sum of the face values of all dice in the roll.
   *
   * @return the total sum of all face values in the dice roll
   */
  public int getTotal() {
    return Arrays.stream(rolls).sum();
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();
    if (areDiceEqual()) {
      sb.append(rolls.length).append(" equal dice of ").append(rolls[0]).append('!');
    } else {
      for (var roll : rolls) {
        sb.append(roll).append(", ");
      }
      sb.delete(sb.length() - 2, sb.length());
    }
    sb.append(" (").append(getTotal()).append(")");
    return sb.toString();
  }
}
