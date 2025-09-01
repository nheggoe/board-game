package dev.nheggoe.boardgame.common.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import dev.nheggoe.boardgame.core.model.Player;
import dev.nheggoe.boardgame.games.monopoly.model.tile.CornerMonopolyTile;
import org.junit.jupiter.api.Test;

class StringFormatterTest {

  @Test
  void testAlgorithm() {
    assertThat(StringFormatter.formatEnum(Player.Figure.BATTLE_SHIP)).isEqualTo("Battle Ship");
    assertThat(StringFormatter.formatEnum(CornerMonopolyTile.Position.BOTTOM_LEFT))
        .isEqualTo("Bottom Left");
  }
}
