package edu.ntnu.idi.bidata.boardgame.common.util;

import edu.ntnu.idi.bidata.boardgame.common.event.EventBus;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoardFactory;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable.MonopolyPlayer;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderBoardFactory;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderGame;
import edu.ntnu.idi.bidata.boardgame.games.snake.model.SnakeAndLadderPlayer;
import java.util.List;

public class GameFactory {

  private GameFactory() {}

  public static MonopolyGame createMonopolyGame(EventBus eventBus, List<MonopolyPlayer> players) {
    var board = MonopolyBoardFactory.generateBoard(MonopolyBoard.Layout.NORMAL);
    return new MonopolyGame(eventBus, board, players);
  }

  public static SnakeAndLadderGame createSnakeGame(
      EventBus eventBus, List<SnakeAndLadderPlayer> players) {
    var board = SnakeAndLadderBoardFactory.createBoard();
    return new SnakeAndLadderGame(eventBus, board, players);
  }
}
