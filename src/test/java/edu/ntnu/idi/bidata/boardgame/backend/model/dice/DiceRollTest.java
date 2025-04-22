package edu.ntnu.idi.bidata.boardgame.backend.model.dice;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DiceRollTest {

  @Test
  void testDefensiveCopy() {
    int[] numbers = {1, 2, 3};
    DiceRoll dr = new DiceRoll(numbers);
    assertThat(numbers).isEqualTo(dr.rolls());

    numbers[2] = 4;
    assertThat(numbers).isNotEqualTo(dr.rolls());
  }

  @Test
  void testIdenticalRolls() {
    assertThat(new DiceRoll(1, 1, 1).areDiceEqual()).isTrue();
    assertThat(new DiceRoll(2, 2, 2, 2, 2, 2, 2, 2).areDiceEqual()).isTrue();
    assertThat(new DiceRoll(1, 3, 4, 5).areDiceEqual()).isFalse();
  }

  @Test
  void testSum() {
    assertThat(new DiceRoll(2, 4, 6).getTotal()).isEqualTo(12);
  }

  @Test
  void testInvalidInput() {
    assertThatThrownBy(() -> new DiceRoll(0, 1, 2))
        .withFailMessage("Face value of dice cannot be less than 1")
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DiceRoll(1, 3, 7))
        .withFailMessage("Face value of dice cannot exceed 6")
        .isInstanceOf(IllegalArgumentException.class);
  }
}
