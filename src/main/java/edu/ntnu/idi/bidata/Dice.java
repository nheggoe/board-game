package edu.ntnu.idi.bidata;

import java.util.Random;

public class Dice {
  private final Random random;

  public Dice() {
    this.random = new Random();
  }

  public int[] roll() {
    int die1 = random.nextInt(6) + 1;
    int die2 = random.nextInt(6) + 1;
    return new int[]{die1, die2};
  }

  public boolean isDouble(int[] diceRoll) {
    return diceRoll[0] == diceRoll[1];
  }

  public int getTotal(int[] diceRoll) {
    return diceRoll[0] + diceRoll[1];
  }

}
