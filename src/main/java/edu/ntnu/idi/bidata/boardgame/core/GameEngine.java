package edu.ntnu.idi.bidata.boardgame.core;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Game;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile.MonopolyTile;
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

  public List<MonopolyTile> getTiles() {
    return List.copyOf(game.getTiles());
  }

  public void nextTurn() {
    var currentPlayer = turnManager.getCurrentPlayerId();
    turnManager.nextTurn();
    var diceRoll = Dice.getInstance().roll(2);
    game.movePlayer(currentPlayer, diceRoll);
  }
}
