package dev.nheggoe.boardgame.games.snake.model.tile;

import dev.nheggoe.boardgame.core.model.Tile;

/**
 * Represents a tile in the game of Snakes and Ladders.
 *
 * @author Nick Heggø
 * @version 2025.05.08
 */
public sealed interface SnakeAndLadderTile extends Tile permits LadderTile, NormalTile, SnakeTile {}
