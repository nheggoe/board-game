package edu.ntnu.idi.bidata.boardgame.common.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringFormatter {

  private StringFormatter() {}

  public static String formatEnum(Enum<?> em) {
    return Arrays.stream(em.name().split("_"))
        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
        .collect(Collectors.joining(" "));
  }
}
