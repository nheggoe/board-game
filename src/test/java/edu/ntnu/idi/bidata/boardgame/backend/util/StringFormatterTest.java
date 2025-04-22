package edu.ntnu.idi.bidata.boardgame.backend.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.boardgame.backend.model.player.Figure;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.CornerTile;
import org.junit.jupiter.api.Test;

class StringFormatterTest {

  @Test
  void testAlgorithm() {
    assertThat(StringFormatter.formatEnum(Figure.BATTLE_SHIP)).isEqualTo("Battle Ship");
    assertThat(StringFormatter.formatEnum(CornerTile.Position.BOTTOM_LEFT))
        .isEqualTo("Bottom Left");
  }
}
