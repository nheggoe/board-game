package dev.nheggoe.boardgame.core.model;

import static org.junit.jupiter.api.Assertions.*;

import dev.nheggoe.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnManagerTest {

  private TurnManager<SnakeAndLadderPlayer> turnManager;
  private SnakeAndLadderPlayer playerOne;
  private SnakeAndLadderPlayer playerTwo;
  private SnakeAndLadderPlayer playerThree;
  private SnakeAndLadderPlayer playerFour;

  @BeforeEach
  void setup() {
    playerOne = new SnakeAndLadderPlayer("John", Player.Figure.CAR);
    playerTwo = new SnakeAndLadderPlayer("Jane", Player.Figure.BATTLE_SHIP);
    playerThree = new SnakeAndLadderPlayer("Jill", Player.Figure.DUCK);
    playerFour = new SnakeAndLadderPlayer("Jack", Player.Figure.HAT);
    var players = List.of(playerOne, playerTwo, playerThree, playerFour);
    turnManager = new TurnManager<>(players);
  }

  @Test
  void testBasic() {
    assertNotNull(turnManager.getCurrentPlayer());
    assertNotNull(turnManager.getNextPlayer());
  }

  @Test
  void testLoop() {
    for (int i = 0; i < 10; i++) {
      assertEquals(playerOne, turnManager.getNextPlayer());
      assertEquals(playerTwo, turnManager.getNextPlayer());
      assertEquals(playerThree, turnManager.getNextPlayer());
      assertEquals(playerFour, turnManager.getNextPlayer());
    }
  }

  @Test
  void testRemovePlayer() {

    turnManager.getNextPlayer();
    turnManager.getNextPlayer();
    assertEquals(playerTwo, turnManager.getCurrentPlayer());
  }
}
