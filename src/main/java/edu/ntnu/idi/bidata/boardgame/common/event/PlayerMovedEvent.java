package edu.ntnu.idi.bidata.boardgame.common.event;

import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.Player;

public record PlayerMovedEvent(Player payload) implements Event {}
