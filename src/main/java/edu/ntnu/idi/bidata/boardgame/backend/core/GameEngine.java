package edu.ntnu.idi.bidata.boardgame.backend.core;

import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.board.BoardGameFactory;
import edu.ntnu.idi.bidata.boardgame.backend.model.dice.Dice;
import edu.ntnu.idi.bidata.boardgame.backend.model.game.Game;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.TileAction;
import edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;
import java.util.SequencedCollection;

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

  private static GameEngine instance;

  private final OutputHandler outputHandler;
  private final Game game;
  private final Dice dice;
  private boolean running = true;
  private int roundNumber = 1;

  /**
   * Constructs a {@code GameEngine} with the required dependencies.
   *
   * @param outputHandler Handles output to the user.
   * @param game The game board containing tiles and game elements.
   * @param dice The dice object used for rolling moves.
   */
  private GameEngine(OutputHandler outputHandler, Game game, Dice dice) {
    this.outputHandler = outputHandler;
    this.game = game;
    this.dice = dice;
  }

  public static synchronized GameEngine getInstance() {
    if (instance == null) {
      instance =
          new GameEngine(
              OutputHandler.getInstance(),
              new Game(BoardGameFactory.generateBoard(BoardGameFactory.Layout.UNFAIR)),
              Dice.getInstance());
    }
    return instance;
  }

  public void setup(SequencedCollection<Player> players) {
    game.addPlayers(players);
  }

  /** Starts and manages the game loop, allowing players to take turns until the game ends. */
  public void run() {
    outputHandler.println("Game has started! Initial player positions:");
    printPlayerLocations();

    while (running) {
      outputHandler.println("Press enter to play round or 'exit' to quit:");
      String input = InputHandler.getInstance().nextLine();
      if (input.equalsIgnoreCase("exit")) {
        running = false;
      } else {
        playRound();
      }
    }
  }

  /**
   * Executes a game round by rolling the dice, moving players, checking tile actions, and
   * determining if any player has won.
   */
  private void playRound() {
    try {
      outputHandler.println("Round %d:".formatted(roundNumber++));
      game.forEach(player -> player.move(dice.roll(2).getTotal()));
      game.forEach(this::executeTileAction);
      checkWinningStatus();
    } catch (Exception e) {
      outputHandler.println("An error occurred during the round: " + e.getMessage());
    }
  }

  /**
   * Checks if a player lands on a tile that has an action and executes it.
   *
   * @param player The player whose current tile is checked for an action.
   */
  private void executeTileAction(Player player) {
    TileAction action = player.getCurrentTile().getTileAction();
    if (action != null) {
      action.performAction(player);
    }
  }

  /** Prints the current locations of all players. */
  private void printPlayerLocations() {
    game.forEach(
        player ->
            outputHandler.println(
                "Player %s is on tile %d"
                    .formatted(
                        player.getName(),
                        player.getCurrentTile() == null
                            ? 1
                            : player.getCurrentTile().getTilePosition() + 1)));
  }

  /**
   * Checks if any player has reached the winning tile and ends the game if a winner is found.
   *
   * @param players The list of players in the game.
   */
  private void checkWinningStatus() {
    game.forEach(
        player -> {
          if (player.getCurrentTile().equals(game.getBoard().tiles().getLast())) {
            outputHandler.println(player.getName() + " has won the game!");
            running = false;
          }
        });
  }

  // ------------------------  getters and setters  ------------------------

  public Game getGame() {
    return game;
  }
}
