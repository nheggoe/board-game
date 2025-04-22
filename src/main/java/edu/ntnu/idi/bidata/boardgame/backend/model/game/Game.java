package edu.ntnu.idi.bidata.boardgame.backend.model.game;

import edu.ntnu.idi.bidata.boardgame.backend.model.board.Board;
import edu.ntnu.idi.bidata.boardgame.backend.model.player.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.tile.Tile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SequencedCollection;
import java.util.UUID;

/**
 * The {@link Game} class represents a board game instance. It manages players, their states, and
 * the game board. The game maintains a unique identifier and keeps track of players and their
 * interactions with the board.
 *
 * <p>This class implements {@link Iterable}, allowing iteration over the collection of players
 * participating in the game.
 *
 * @author Nick Heggø
 * @version 2025.04.15
 */
public class Game implements Iterable<Player> {

  private final UUID gameId;
  private final List<Player> players;

  private Board board;

  private Game() {
    this.gameId = UUID.randomUUID();
    this.players = new ArrayList<>();
  }

  public Game(Board board, SequencedCollection<Player> players) {
    this();
    setBoard(board);
    addPlayers(players);
  }

  // ------------------------  APIs  ------------------------

  public boolean addPlayer(Player player) {
    player.setGameId(gameId);
    return players.add(player);
  }

  public void addPlayers(SequencedCollection<Player> players) {
    players.forEach(
        player -> {
          player.setGameId(gameId);
          player.setCurrentTile(getBoard().getStartingTile());
        });
    this.players.addAll(players);
  }

  public void movePlayer(Player player, int steps) {
    Tile currentTile =
        player.getCurrentTile() == null ? board.getStartingTile() : player.getCurrentTile();
    player.setCurrentTile(board.getTileAfterSteps(currentTile, steps));
  }

  @Override
  public Iterator<Player> iterator() {
    if (players.isEmpty()) {
      throw new IllegalStateException("There is currently no players!");
    }
    return players.iterator();
  }

  // ------------------------  getters and setters  ------------------------

  private void setBoard(Board board) {
    if (board == null) {
      throw new IllegalArgumentException("Board cannot be null!");
    }
    this.board = board;
  }

  public Board getBoard() {
    return board;
  }

  public UUID getGameId() {
    return gameId;
  }
}
