package edu.ntnu.idi.bidata.boardgame.core.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  private Player player;

  @BeforeEach
  void setup() {
    player = new Player("John", Player.Figure.CAR) {};
  }

  @Test
  void testGetSetName() {
    assertThat(player.getName()).isEqualTo("John");
    assertThatThrownBy(() -> player.setName("")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testGetSetFigure() {
    assertThat(player.getFigure()).isEqualTo(Player.Figure.CAR);
    player.setFigure(Player.Figure.HAT);
    assertThat(player.getFigure()).isEqualTo(Player.Figure.HAT);
  }
}
