package edu.ntnu.idi.bidata.boardgame.backend.model.player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Collectors;

class FigureTest {


  private String enumToNameAlgorithm(String enumName) {
    return Arrays.stream(enumName.split("_"))
        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
        .collect(Collectors.joining(" "));
  }
}
