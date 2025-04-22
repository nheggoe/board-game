package edu.ntnu.idi.bidata.boardgame.backend.model.player;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class FigureTest {

  @Test
  void testAlgorithm() {
    String enumName1 = "BATTLE_SHIP";
    String enumName2 = "GENTLEMAN_HAT";
    assertThat(enumToNameAlgorithm(enumName1)).isEqualTo("Battle Ship");
    assertThat(enumToNameAlgorithm(enumName2)).isEqualTo("Gentleman Hat");
  }

  private String enumToNameAlgorithm(String enumName) {
    return Arrays.stream(enumName.split("_"))
        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
        .collect(Collectors.joining(" "));
  }
}
