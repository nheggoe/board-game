package edu.ntnu.idi.bidata.boardgame.backend.model.dice;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class DiceTest {
  private final Dice dice = Dice.getInstance();

  @Test
  void testRollDice() {
    for (int i = 0; i < 100; i++) {
      DiceRoll diceRoll = dice.roll(2);
      assertThat(diceRoll.rolls()).hasSize(2);
      assertThat(diceRoll.getTotal()).isGreaterThanOrEqualTo(2);
      assertThat(diceRoll.getTotal()).isLessThanOrEqualTo(12);
    }
  }
}
