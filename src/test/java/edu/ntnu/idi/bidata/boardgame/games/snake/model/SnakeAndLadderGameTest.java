package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.DiceRoll;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.LadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeTile;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SnakeAndLadderGameTest {
  private SnakeAndLadderGame game;

  @BeforeEach
  void setup() {
    var eventBus = mock(EventBus.class);
    var tiles = new ArrayList<SnakeAndLadderTile>();
    tiles.add(new NormalTile()); // 0
    tiles.add(new NormalTile()); // 1
    tiles.add(new SnakeTile(1)); // 2
    tiles.add(new LadderTile(1)); // 3
    tiles.add(new NormalTile()); // 4
    tiles.add(new NormalTile()); // 5
    tiles.add(new NormalTile()); // 6
    tiles.add(new NormalTile()); // 7
    tiles.add(new SnakeTile(1)); // 8
    tiles.add(new NormalTile()); // 9
    tiles.add(new NormalTile()); // 10
    tiles.add(new NormalTile()); // 11
    tiles.add(new NormalTile()); // 12
    tiles.add(new NormalTile()); // 13
    var board = new SnakeAndLadderBoard(tiles);
    var player1 = new SnakeAndLadderPlayer("John", Player.Figure.CAR);
    var players = List.of(player1);
    game = new SnakeAndLadderGame(eventBus, board, players);
  }

  @Test
  void test_simulateGamePlay() {
    try (MockedStatic<Dice> mockedDice = mockStatic(Dice.class)) {
      mockedDice.when(() -> Dice.roll(1)).thenReturn(new DiceRoll(new int[] {2}));
      assertThat(game.getBoard()).isNotNull();
      assertThat(game.getBoard().tiles()).hasSize(14);

      var player = game.getPlayers().getFirst();
      assertThat(player.getPosition()).isZero();

      game.nextTurn();
      assertThat(player.getPosition())
          .withFailMessage(
              "After one move should land the the tile on index 1, instead it was "
                  + player.getPosition())
          .isEqualTo(1);
      assertThat(game.getBoard().getTileAtIndex(player.getPosition()))
          .isInstanceOf(NormalTile.class);

      game.nextTurn();
      assertThat(player.getPosition())
          .withFailMessage(
              "After two moves should've landed on the tile with index 4, but was "
                  + player.getPosition())
          .isEqualTo(4);
      assertThat(game.getBoard().getTileAtIndex(player.getPosition()))
          .isInstanceOf(NormalTile.class);

      game.nextTurn();
      assertThat(player.getPosition()).isEqualTo(6);
      assertThat(game.getBoard().getTileAtIndex(player.getPosition()))
          .isInstanceOf(NormalTile.class);

      game.nextTurn();
      assertThat(player.getPosition()).isEqualTo(7);
      assertThat(game.getBoard().getTileAtIndex(player.getPosition()))
          .isInstanceOf(NormalTile.class);
    }
  }

  @Test
  void test_simulateGamePlay_with_rollOnSix() {
    try (MockedStatic<Dice> mockedDice = mockStatic(Dice.class)) {
      mockedDice
          .when(() -> Dice.roll(1))
          .thenReturn(new DiceRoll(new int[] {6}))
          .thenReturn(new DiceRoll(new int[] {2}));

      assertThat(game.getBoard()).isNotNull();
      assertThat(game.getBoard().tiles()).hasSize(14);

      var player = game.getPlayers().getFirst();
      assertThat(player.getPosition()).isZero();

      game.nextTurn();
      assertThat(player.getPosition()).isEqualTo(7);
      assertThat(game.getBoard().getTileAtIndex(player.getPosition()))
          .isInstanceOf(NormalTile.class);

      game.nextTurn();
      assertThat(game.getBoard().getTileAtIndex(player.getPosition()))
          .isInstanceOf(NormalTile.class);
    }
  }
}
