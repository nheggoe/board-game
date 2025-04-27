package edu.ntnu.idi.bidata.boardgame.backend.core;

import static edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler.*;

import edu.ntnu.idi.bidata.boardgame.backend.event.EventListener;
import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import java.util.List;
import java.util.Objects;

/**
 * The {@code GameEngine} class is responsible for managing the core game loop and logic. It
 * processes each game round, moves players, checks for tile actions, and determines if a player has
 * won.
 *
 * <p>This class interacts with the {@link Board}, {@link Dice}, and {@link Player} objects to
 * facilitate game progression.
 *
 * @author Nick Heggø
 * @version 2025.04.15
 */
public class GameEngine {

  private final Game game;
  private final TurnManager turnManager;

  public GameEngine(Game game, TurnManager turnManager) {
    this.game = Objects.requireNonNull(game, "Game cannot be null!");
    this.turnManager = Objects.requireNonNull(turnManager, "TurnManager cannot be null!");
  }

  public List<Player> getPlayers() {
    return List.copyOf(game.getPlayers());
  }

  public List<Tile> getTiles() {
    return List.copyOf(game.getTiles());
  }

  public void nextTurn() {
    var currentPlayer = turnManager.getCurrentPlayerId();
    turnManager.nextTurn();
    var diceRoll = Dice.getInstance().roll(2);
    game.movePlayer(currentPlayer, diceRoll);
  }

  public void addListener(EventListener eventListener) {
    game.addListener(Objects.requireNonNull(eventListener, "Observer cannot be null!"));
  }

  public void removeListener(EventListener eventListener) {
    game.removeListener(Objects.requireNonNull(eventListener, "Observer cannot be null!"));
  }

  /** Checks if any player has reached the winning tile and ends the game if a winner is found. */
  private void checkWinningStatus() {
    var winners = game.getWinners().getValue();
    if (winners.isEmpty()) {
      throw new IllegalArgumentException("Failed to retrieve winners.");
    } else if (winners.size() == 1) {
      Player winner = winners.getFirst();
      println(
          "The winner is %s with net worth of $%d!"
              .formatted(winner.getName(), winner.getNetWorth()));
    } else {
      println("We have multiple winners!");
      for (int i = 0; i < winners.size(); i++) {
        println("%d. %s".formatted((i + 1), winners.get(i).getName()));
      }
      println(
          "All finished the game with net worth of $%d"
              .formatted(winners.getFirst().getNetWorth()));
    }
  }
}
