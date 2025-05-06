package edu.ntnu.idi.bidata.boardgame.backend.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import edu.ntnu.idi.bidata.boardgame.common.util.StringFormatter;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.CornerMonopolyTile;
import org.junit.jupiter.api.Test;

class StringFormatterTest {

  @Test
  void testAlgorithm() {
    assertThat(StringFormatter.formatEnum(Player.Figure.BATTLE_SHIP)).isEqualTo("Battle Ship");
    assertThat(StringFormatter.formatEnum(CornerMonopolyTile.Position.BOTTOM_LEFT))
        .isEqualTo("Bottom Left");
  }
}
