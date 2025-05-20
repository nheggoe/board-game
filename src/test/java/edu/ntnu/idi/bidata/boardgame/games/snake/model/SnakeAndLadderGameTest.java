package edu.ntnu.idi.bidata.boardgame.games.snake.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.core.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.NormalTile;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.tile.SnakeAndLadderTile;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SnakeAndLadderGameTest {
  private SnakeAndLadderGame game;

  @BeforeEach
  void setup() {
    var eventBus = new EventBus();
    var tiles = new ArrayList<SnakeAndLadderTile>();
    tiles.add(new NormalTile()); // 0
    tiles.add(new NormalTile()); // 1
    tiles.add(new NormalTile()); // 2
    tiles.add(new NormalTile()); // 3
    tiles.add(new NormalTile()); // 4
    tiles.add(new NormalTile()); // 5
    tiles.add(new NormalTile()); // 6
    tiles.add(new NormalTile()); // 7
    tiles.add(new NormalTile()); // 8
    tiles.add(new NormalTile()); // 9
    tiles.add(new NormalTile()); // 10
    tiles.add(new NormalTile()); // 11
    tiles.add(new NormalTile()); // 12
    tiles.add(new NormalTile()); // 13
    var board = new SnakeAndLadderBoard(tiles);
    var player1 = new SnakeAndLadderPlayer("John", Player.Figure.CAR);
    var player2 = new SnakeAndLadderPlayer("Jane", Player.Figure.HAT);
    var players = List.of(player1, player2);
    game = new SnakeAndLadderGame(eventBus, board, players);
  }

  @Test
  @Disabled
  // fixme unfinished
  void testBasic() {
    try (MockedStatic<Dice> mockedDice = mockStatic(Dice.class)) {
      mockedDice.when(() -> Dice.roll(2)).thenReturn(1);
      assertThat(game.getBoard()).isNotNull();
      assertThat(game.getBoard().tiles()).hasSize(14);
    }
  }
}
