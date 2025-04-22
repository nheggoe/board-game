package edu.ntnu.idi.bidata.boardgame.backend.model.player;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  private Player player;

  @BeforeEach
  void setup() {
    player = new Player("John", Figure.CAR);
  }

  @Test
  void testGetSetName() {
    assertThat(player.getName()).isEqualTo("John");
    assertThatThrownBy(() -> player.setName("")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testGetSetFigure() {
    assertThat(player.getFigure()).isEqualTo(Figure.CAR);
    player.setFigure(Figure.HAT);
    assertThat(player.getFigure()).isEqualTo(Figure.HAT);
  }

  @Test
  void testMovePlayer() {
    assertThat(player.getCurrentTile().getTilePosition()).isEqualTo(-1);
    // TODO add support for find game instance by gameId
  }
}
