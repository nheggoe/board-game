package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.core.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.MonopolyBoard;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
import java.util.List;
import java.util.Objects;

/**
 * The {@code GameEngine} class is responsible for managing the core game loop and logic. It
 * processes each game round, moves players, checks for tile actions, and determines if a player has
 * won.
 *
 * <p>This class interacts with the {@link MonopolyBoard}, {@link Dice}, and {@link Player} objects
 * to facilitate game progression.
 *
 * @author Nick Heggø
 * @version 2025.04.15
 */
public class GameEngine {

  private final MonopolyGame monopolyGame;
  private final TurnManager turnManager;

  public GameEngine(MonopolyGame monopolyGame, TurnManager turnManager) {
    this.monopolyGame = Objects.requireNonNull(monopolyGame, "Game cannot be null!");
    this.turnManager = Objects.requireNonNull(turnManager, "TurnManager cannot be null!");
  }

  public List<Player> getPlayers() {
    return List.copyOf(monopolyGame.getPlayers());
  }

  public List<MonopolyTile> getTiles() {
    return List.copyOf(monopolyGame.getTiles());
  }

  public void nextTurn() {
    var currentPlayer = turnManager.getCurrentPlayerId();
    turnManager.nextTurn();
    var diceRoll = Dice.getInstance().roll(2);
    monopolyGame.movePlayer(currentPlayer, diceRoll);
  }
}
